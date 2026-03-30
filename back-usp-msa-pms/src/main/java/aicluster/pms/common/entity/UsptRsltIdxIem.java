package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptRsltIdxIem implements Serializable {
	private static final long serialVersionUID = -4287386478907996616L;
	/** 성과지표항목ID */
	private String rsltIdxIemId;
	/** 성과ID */
	private String rsltId;
	/** 성과지표ID */
	private String rsltIdxId;
	/** 성과지표명 */
	private String rsltIdxNm;
	/** 성과지표유형코드(G:RSLT_IDX_TYPE) */
	private String rsltIdxTypeCd;
	/** 증빙첨부파일ID */
	private String prufAttachmentId;
	/** 생성자ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;
	/** 수정자ID */
	@JsonIgnore
	private String updaterId;
	/** 수정일시 */
	@JsonIgnore
	private Date updatedDt;
}