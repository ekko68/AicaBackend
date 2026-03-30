package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptCrqfc implements Serializable {
	private static final long serialVersionUID = -257184392128584354L;
	/** 자격증ID */
	private String crqfcId;
	/**  회원ID*/
	@JsonIgnore
	private String memberId;
	/** 자격증명 */
	private String crqfcNm;
	/** 등급 */
	private String grad;
	/** 취득일 */
	private String acqdt;
	/** 검정기관 */
	private String athrzInstt;
	/** 순번 */
	private Long rn;
	/** I:등록, U:수정, D:삭제 */
	private String flag;
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
