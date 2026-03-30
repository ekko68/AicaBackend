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
public class UsptEvlLastSlctn implements Serializable {
	private static final long serialVersionUID = 3493037621989596634L;
	/** 평가최종선정ID */
	private String evlLastSlctnId;
	/** 평가계획ID */
	private String evlPlanId;
	/** 최종선정여부 */
	private Boolean lastSlctn;
	/** 최종선정일시 */
	private Date lastSlctnDt;
	/** 선정통보여부 */
	private Boolean slctnDspth;
	/** 선정통보일시 */
	private Date slctnDspthDt;
	/** 선정발송방법 */
	private String slctnSndngMth;
	/** 선정발송내용 */
	private String slctnSndngCn;
	/** 탈락통보여부 */
	private Boolean ptrmsnDspth;
	/** 탈락통보일시 */
	private Date ptrmsnDspthDt;
	/** 탈락발송방법 */
	private String ptrmsnSndngMth;
	/** 탈락발송내용 */
	private String ptrmsnSndngCn;
	/** 최종선정처리구분코드(G:LAST_SLCTN_PROCESS_DIV) */
	private String lastSlctnProcessDivCd;
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
