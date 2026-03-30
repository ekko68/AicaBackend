package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptExpertCl implements Serializable {

	/**
	 *전문가분류
	 */
	private static final long serialVersionUID = 9006763112125254227L;
	/** 전문가분류ID */
	String  expertClId;
	/** 부모전문가분류ID */
	String  parntsExpertClId;
	/** 전문가분류명 */
	String  expertClNm;
	/** 정렬순서번호*/
	int  sortOrdrNo;
	/**사용여부**/
	Boolean  enabled;
	/**트리메뉴 레벨**/
	String  level;
	/**path**/
	String  path;
	/**cycle**/
	String  cycle;

	/** I:등록, U:수정, D:삭제 */
	private String flag;

	@JsonIgnore
	private String creatorId;
	@JsonIgnore
	private Date createdDt;
	@JsonIgnore
	String  updaterId;
	@JsonIgnore
	private Date updatedDt;
}
