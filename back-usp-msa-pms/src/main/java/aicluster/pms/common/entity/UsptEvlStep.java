package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsptEvlStep implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6845062456912169754L;

	/** 변경 플래그(I,U,D) */
	String  flag;
	/** 평가단계ID */
	String  evlStepId;
	/** 평가계획ID */
	String  evlPlanId;
	/** 평가단계명 */
	String  evlStepNm;
	/** 정렬순서번호 */
	Integer sortOrdrNo;
	/** 단계별평가진행상태코드(G:EVL_STEP_STTUS) */
	String  evlStepSttusCd;
	/** 선정대상수 */
	Integer slctnTargetCo;
	/** 탈락기준코드(G:DRPT_STDR) */
	String  drptStdrCd;
	/** 최고최저제외여부 */
	Boolean topLwetExcl;
	/** 통보여부 */
	Boolean dspth;
	/** 통보일시 */
	Date    dspthDt;

	/** 생성자 ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;
	/** 수정자 ID */
	@JsonIgnore
	private String updaterId;
	/** 수정일시 */
	@JsonIgnore
	private Date updatedDt;
}
