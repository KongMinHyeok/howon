package kr.co.voard.vo;

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
@Builder
public class FileVO {
	private int fno;
	private int parent;
	private String newName;
	private String oriName;
	private int download;
	private String rdate;
}