package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptBsnsJobStep implements Serializable {
	private static final long serialVersionUID = -2807229563678523156L;
	/** 사업코드 */
	private String bsnsCd;
	/** 업무단계 */
	private String jobStepCd;

	/** 생성자ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;
}
