package kr.co.voard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.voard.repository.ArticleEntity;
import kr.co.voard.vo.ArticleVO;
import kr.co.voard.vo.FileVO;

@Mapper
@Repository
public interface ArticleDAO {

	public int insertArticle(ArticleVO vo);
	public int insertFile(FileVO vo);
	public int selectCountTotal(@Param(value="keyword")String keyword );
	public ArticleVO selectArticle(int no);
	public List<ArticleVO> selectArticles(int start, @Param(value="keyword")String keyword);
	public FileVO selectFile(int fno);
	public int updateFileDownload(int fno);
	public Integer updateArticle(ArticleVO vo);
	public Integer deleteArticle(int no);
	
}