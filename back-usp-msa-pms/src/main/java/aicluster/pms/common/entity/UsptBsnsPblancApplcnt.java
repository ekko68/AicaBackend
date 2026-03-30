package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptBsnsPblancApplcnt implements Serializable {
	private static final long serialVersionUID = -7301351789062415751L;
	/** 신청ID */
	private String applyId;
	/** 공고ID */
	private String pblancId;
	/** 접수차수 */
	private Integer rceptOdr;
	/** 접수번호(BA + 8자리 순번) */
	private String receiptNo;
	/** 회원ID */
	private String memberId;
	/** 회원명 */
	private String memberNm;
	/** 신청파일그룹ID */
	private String applyFileGroupId;
	/** 접수일시 */
	private Date rceptDt;
	/** 접수상태코드(G:RCEPT_STTUS) */
	private String rceptSttusCd;
	/** 접수상태 */
	private String rceptSttus;
	/** 접수상태 변경일시 */
	@JsonIgnore
	private Date rceptSttusDt;
	/** 보완의견내용 */
	private String makeupOpinionCn;
	/** 보완요청내용 */
	private String makeupReqCn;
	/** 반려사유내용 */
	private String rejectReasonCn;
	/** 선정여부 */
	private Boolean slctn;
	/** 선정일시 */
	private Date slctnDt;
	/** 회원유형 */
	private String memberType;
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
