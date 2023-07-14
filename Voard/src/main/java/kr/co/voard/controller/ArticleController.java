package kr.co.voard.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("getView")
	public ArticleVO view(Model model, int no) {
		// 게시물 들고오기
		ArticleVO article = service.selectArticle(no);
		model.addAttribute("article",article);
		// 댓글 가져오기
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
	public int write(@RequestBody ArticleVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
				
		int result = service.insertArticle(vo);
		
		return result;
	}
	

	@DeleteMapping("list/{no}")
	public Integer deleteArticle(@PathVariable int no) {
		log.info("no" + no);
		return service.deleteArticle(no);
	}


}