package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptBsnsPblancApplyBhExmnt implements Serializable {
	private static final long serialVersionUID = 1127001760153981429L;
	/** 신청ID */
	private String applyId;
	/** 사전검토항목ID */
	private String bhExmntIemId;
	/** 체크결과구분코드(G:CECK_RESULT_DIV) */
	private String ceckResultDivCd;
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
