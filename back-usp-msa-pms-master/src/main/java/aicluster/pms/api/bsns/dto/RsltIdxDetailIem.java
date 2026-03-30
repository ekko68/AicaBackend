package aicluster.pms.api.bsns.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class RsltIdxDetailIem {
	/** 성과지표ID */
	@JsonIgnore
	private String rsltIdxId;
	/** 성과지표세부항목ID */
	private String rsltIdxDetailIemId;
	/** 세부항목명 */
	private String detailIemNm;
	/** 항목단위코드 (G:IEM_UNIT) */
	private String iemUnitCd;
	/** 항목단위 */
	private String iemUnit;
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
