package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptExpertClMapng implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7123342290998601950L;
	/***전문가ID**/
	String expertId;
	/** 전문가분류ID */
	String expertClId;
	/**전문가명**/
	String expertNm;
	/**부서명**/
	String wrcNm;
	/**직위명**/
	String ofcpsNm;
	 /** 부모전문가분류ID */
	String  parntsExpertClId;

	/** I:등록, U:수정, D:삭제 */
	private String flag;

	@JsonIgnore
	private String creatorId;		/** 생성자 ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */

}
