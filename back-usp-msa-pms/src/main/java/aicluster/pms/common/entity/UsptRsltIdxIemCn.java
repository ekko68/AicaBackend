package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptRsltIdxIemCn implements Serializable {
	private static final long serialVersionUID = -7419130907407708640L;
	/** 성과지표항목내용ID */
	private String rsltIdxIemCnId;
	/** 성과지표항목ID */
	@JsonIgnore
	private String rsltIdxIemId;
	/** 성과지표세부항목ID */
	private String rsltIdxDetailIemId;
	/** 성과지표세부항목명 */
	private String detailIemNm;
	/** 성과지표기준항목ID */
	private String rsltIdxStdIemId;
	/** 항목단위코드(G:IEM_UNIT) */
	private String iemUnitCd;
	/** 성과지표기준항목명 */
	private String stdIemNm;
	/** 성과지표항목내용 */
	private String rsltIdxIemCn;
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
