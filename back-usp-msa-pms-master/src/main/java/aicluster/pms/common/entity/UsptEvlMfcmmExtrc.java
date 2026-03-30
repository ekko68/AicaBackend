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
public class UsptEvlMfcmmExtrc implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3331789917369019466L;

	/** 위원추출ID */
	String  mfcmmExtrcId;
	/** 평가위원회ID */
	String  evlCmitId;
	/** 차수번호(0부터시작. 0:기준차수) */
	Integer odrNo;
	/** 추출배수 */
	Integer extrcMultiple;
	/** 추가배제조건코드(G:ADD_EXCL_CND) */
	String[] aditExclCndCd;
	/** 참여제한조건코드(G:PARTCPTN_LMTT_CND) */
	String[] partcptnLmttCndCd;
	/** 전문가분류ID */
	String[] expertClId;

	/** 섭외결과상태코드 */
	String  lsnResultCd;

	/** 생성자 ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;

}
