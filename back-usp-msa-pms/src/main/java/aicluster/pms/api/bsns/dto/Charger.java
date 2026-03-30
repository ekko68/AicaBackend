package aicluster.pms.api.bsns.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Charger {
	/** 내부자 ID */
	private String insiderId;
	/** 내부자명 */
	private String insiderNm;
	/** 직급 */
	private String positionNm;
	/** 부서명 */
	private String deptNm;
	/** 담당자 권한코드 */
	private String chargerAuthorCd;
	/** 대표담당자 여부 */
	private Boolean reprsntCharger;
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
