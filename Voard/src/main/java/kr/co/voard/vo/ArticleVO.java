package kr.co.voard.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVO {
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
	private String rdate;
	
	// 파일 업로드를 위해 추가
	private MultipartFile fname;
	
	// 추가빌드
	private String nick;
	private FileVO fileVO;
	
	// 파일 다운로드를 위해 추가
	private List<FileVO> fileList;
}