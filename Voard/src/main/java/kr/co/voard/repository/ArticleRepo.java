package kr.co.voard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ArticleRepo extends JpaRepository<ArticleEntity, Integer>{
	
}

	