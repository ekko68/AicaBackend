package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aicluster.framework.common.entity.CmmtAttachment;
import lombok.Data;

@Data
public class UsptCnvnTaskInfoHist implements Serializable {
	/**
	 * 협약과제정보변경이력
	 */
	private static final long serialVersionUID = -3056087765380620932L;
	/** 협약과제정보변경이력ID */
	String  cnvnTaskInfoHistId;
	 /** 협약변경요청ID */
	String  cnvnChangeReqId;
	/** 승인협약과제정보변경이력ID */
	String  confmCnvnTaskInfoHistId;
	/** 과제명(국문) */
	String  taskNmKo;
	  /** 과제명(영문) */
	String  taskNmEn;
	/** 지원분야 */
	String  applyField;

	/** 사업기간 */
	String bsnsPd = "";
	/** 사업기간(전체) */
	String bsnsPdAll = "";
	/** 사업기간(당해) */
	String bsnsPdYw = "";
	 /*변경일자*/
	String changeDe;
	/*변경전후*/
	String changeBeAf;

	/** 생성자ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;

}
