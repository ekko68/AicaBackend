package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptMsvc implements Serializable {
	private static final long serialVersionUID = -3726445265118131211L;
	/** 회원ID */
	@JsonIgnore
	private String memberId;
	/** 군복무유형코드(G:MSVC_TYPE) */
	private String msvcTypeCd;
	/** 군복무유형 */
	private String msvcType;
	/** 군복무시작일 */
	private String msvcBgnde;
	/** 군복무종료일 */
	private String msvcEndde;
	/** 군면제사유 */
	private String msvcExemptReason;
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
