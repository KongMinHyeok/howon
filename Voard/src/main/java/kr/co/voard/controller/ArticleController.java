package kr.co.voard.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.voard.repository.ArticleEntity;
import kr.co.voard.service.ArticleService;
import kr.co.voard.vo.ArticleVO;
import kr.co.voard.vo.FileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ArticleController {
	
	@Autowired
	private ArticleService service;
	
	@Autowired
	private SqlSession sqlSession;
	
	@GetMapping("list")
	public Map<String, Object> list(@RequestParam(value="pg", defaultValue = "1") String pg, @RequestParam(value="keyword", defaultValue = "") String keyword) {
		
		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage);
		
		int total = service.selectCountTotal(keyword);
		int lastPageNum = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		List<ArticleVO> articles =  service.selectArticles(start, keyword); 
		
		//model.addAttribute("articles", articles); // 서버 사이드 렌더링을 위한 모델 참조
		//model.addAttribute("currentPage", currentPage);
		//model.addAttribute("lastPageNum", lastPageNum);
		//model.addAttribute("PageStartNum", PageStartNum);
		//model.addAttribute("groups", groups);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("articles", articles);
		resultMap.put("currentPage", currentPage);
		resultMap.put("lastPageNum", lastPageNum);
		resultMap.put("pageStartNum", pageStartNum);
		resultMap.put("groups", groups);
		resultMap.put("keyword", keyword);
		
		return resultMap;
	}
	
	@PutMapping("modify")
	public Integer modify(@RequestBody ArticleVO vo) {
		
		log.info("ArticleVO : " + vo);
		
		return service.updateArticle(vo);
	}
	
	@ResponseBody
	@CrossOrigin(origins = "*")
	@GetMapping("/view")
	public ArticleVO view(@RequestParam("no") String no, Model model) {
		// 게시물 들고오기
		List<ArticleVO> list = sqlSession.selectList("kr.co.voard.dao.ArticleDAO.selectArticle", no);
		ArticleVO article = list.get(0);
		List<FileVO> files = article.getFileList();
		
		model.addAttribute("article",article);
		
		if(article.getFile() > 0) {
			model.addAttribute("files", files);
		}
		log.info("article : " + article);
		log.info("files : " + files);
		
		return article;
	}

	
	@GetMapping("download")
	public ResponseEntity<Resource> download(int fno) throws IOException {
		// 파일 조회
		FileVO vo = service.selectFile(fno);
		
		// 파일 다운로드 카운터 증가
		service.updateFileDownload(fno);
		
		// 파일 다운로드
		ResponseEntity<Resource> respEntity = service.fileDownload(vo);
		

		return respEntity;
	}
	
	
	@PostMapping("write")
	public String qnaWrite(@ModelAttribute("ArticleVO") ArticleVO article, MultipartHttpServletRequest req){
		// CSQuestionsVO 객체에 속성 값 채우기(rdate는 쿼리문에서 처리)
		article.setRegip(req.getRemoteAddr());
		System.out.println(article.getContent());
		
		// 사용자가 업로드한 파일들 가져오고 article 객체의 file 속성값 정하기	
		if(article.getFname() != null && !article.getFname().isEmpty()) { // 첨부 파일이 한 개 이상인 경우
			List<MultipartFile> files = req.getFiles("fname");
			article.setFile(files.size());
			
			// 사용자가 작성한 QnA DB에 insert
			service.insertArticle(article);
			
			for(MultipartFile file : files) {
				// DB에 파일 업로드
				log.info("file : " + file);
				service.fileUpload(file, article);
				
			}
			log.info("file :" + article.getFile());
		}else { // 첨부 파일이 없는 경우
			
			// 사용자가 작성한 QnA DB에 insert
			service.insertArticle(article);
			article.setFile(0);

		}
		log.info("article : " + article);
		return "write";
	}
	
	@ResponseBody
	@CrossOrigin(origins = "*")
	@DeleteMapping("/view")
	public Integer deleteArticle(@RequestParam("no") int no) {
		log.info("no" + no);
		return service.deleteArticle(no);
	}


}