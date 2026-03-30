package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptJobCareer implements Serializable{
	private static final long serialVersionUID = -8522138422997381827L;
	/** 직장경력ID */
	private String jobCareerId;
	/** 회원ID */
	@JsonIgnore
	private String memberId;
	/** 근무처 */
	private String wrkplc;
	/** 시작일 */
	private String bgnde;
	/** 종료일 */
	private String endde;
	/**  업무*/
	private String job;
	/** 퇴사사유 */
	private String retireResn;
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
