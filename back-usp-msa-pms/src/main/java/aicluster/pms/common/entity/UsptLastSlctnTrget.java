package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptLastSlctnTrget implements Serializable {
	private static final long serialVersionUID = -2043311312336543693L;
	/** 최종선정대상ID */
	private String lastSlctnTrgetId;
	/** 평가최종선정ID */
	private String evlLastSlctnId;
	/** 신청ID */
	private String applyId;
	/** 선정여부 */
	private Boolean slctn;
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
