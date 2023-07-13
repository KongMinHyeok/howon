package kr.co.voard.repository;


import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import lombok.Data;

@Data
@Entity
@Table(name = "board_article")
@EntityListeners(AuditingEntityListener.class)
public class ArticleEntity {
	@Id
	private int no;
	private int parent;
	private int comment;
	private String cate;
	private String title;
	private String content;
	private int file;
	private int hit;
	private String uid;
	private String regip;
	@CreatedDate
	private String rdate;
	
	public String getRdate() {
		return rdate.substring(2,10);
	}
	
	
}