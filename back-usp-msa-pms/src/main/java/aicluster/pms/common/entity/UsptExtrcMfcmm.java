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
public class UsptExtrcMfcmm implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2971930093801117123L;

	/** 추출위원ID */
	String  extrcMfcmmId;
	/** 위원추출ID */
	String  mfcmmExtrcId;
	/** 전문가ID */
	String  expertId;
	/** 섭외결과코드(G:LIAISON_RESULT) */
	String  lsnResultCd;

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
