package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptStdntMtch implements Serializable {
	private static final long serialVersionUID = -4710657788902948289L;
	/** 최종선정대상ID */
	private String lastSlctnTrgetId;
	/** 신청ID */
	private String applyId;

	/** 변경 플래그(I,U,D) */
	String  flag;
	/** 생성자ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;
}
