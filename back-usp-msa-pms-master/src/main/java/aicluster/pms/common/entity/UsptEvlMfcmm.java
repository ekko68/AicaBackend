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
public class UsptEvlMfcmm implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5256805615644339696L;

	/** 순번 */
	private int rn;

	/** 평가위원ID */
	private String evlMfcmmId;
	/** 평가위원명 */
	private String evlMfcmmNm;
	/** 평가위원회ID */
	private String evlCmitId;
	/** 추출위원ID */
	private String extrcMfcmmId;
	/** 전문가ID */
	private String expertId;
	/** 암호화된 비밀번호 */
	private String encPassword;
	/** 사용시작일 */
	private String useBgnde;
	/** 사용종료일 */
	private String useEndde;
	/** Refresh token */
	private String refreshToken;
	/** 위원장여부 */
	private Boolean charmn;
	/** 위원장여부명 */
	private String charmnNm;
	/** 출석상태코드(G:ATEND_STTUS) */
	private String atendSttusCd;
	/** 출석상태명(G:ATEND_STTUS) */
	private String atendSttusNm;
	/** 출석일시 */
	private Date atendDt;
	/** 회피여부 */
	private Boolean evasAgreAt;
	/** 회피여부명 */
	private String evasNm;
	/** 회피사유내용 */
	private String evasResnCn;
	/** 위원평가상태코드(G:MFCMM_EVL_STTUS) */
	private String mfcmmEvlSttusCd;

	/** 소속기관(직장명) */
	private String wrcNm;

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
