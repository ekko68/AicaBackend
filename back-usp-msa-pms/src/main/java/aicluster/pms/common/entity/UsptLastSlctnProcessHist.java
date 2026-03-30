package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptLastSlctnProcessHist implements Serializable {
	private static final long serialVersionUID = -2172267282639975493L;
	/** 최종선정처리이력ID */
	private String lastSlctnProcessHistId;
	/** 평가최종선정ID */
	private String evlLastSlctnId;
	/** 최종선정처리구분코드(G:LAST_SLCTN_PROCESS_DIV) */
	private String lastSlctnProcessDivCd;
	/** 사유내용 */
	private String resnCn;
	/** 생성자ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;
}
