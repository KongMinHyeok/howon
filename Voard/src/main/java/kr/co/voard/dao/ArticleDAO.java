package kr.co.voard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.voard.domain.ArticleVO;
import kr.co.voard.domain.FileVO;

@Mapper
@Repository
public interface ArticleDAO {

	public int insertArticle(ArticleVO article);	
	public int insertFile(FileVO vo);
	public int selectCountTotal(@Param(value="keyword")String keyword );
	public ArticleVO selectArticle(String no);
	public List<ArticleVO> selectArticles(int start, @Param(value="keyword")String keyword);
	public FileVO selectFile(String fno);
	public Integer updateArticle(ArticleVO vo);
	public Integer deleteArticle(int no);
	public Integer deleteFile(String parent);
	
}