package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptWnpz implements Serializable {
	private static final long serialVersionUID = -5093253494649151964L;
	/** 수상ID */
	private String wnpzId;
	/** 회원ID */
	@JsonIgnore
	private String memberId;
	/** 수상명 */
	private String wnpzNm;
	/** 취득일 */
	private String acqdt;
	/** 발행기관 */
	private String isuInstt;
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
