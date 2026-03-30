package aicluster.pms.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EvlResultBasicInfoDto implements Serializable {

	private static final long serialVersionUID = 4480112201906342482L;

	String  evlResultId;                    /** 평가결과ID */
	String  evlMfcmmId;                     /** 평가위원ID */
	String  evlMfcmmNm;                     /** 평가위원명 */
	String  evlOpinion;                     /** 평가의견 */
	String  applyId;                        /** 신청ID */
	String  evlTrgetId;						/** 평가대상ID */
	String  receiptNo;                      /** 접수번호(BA + 8자리 순번) */
	String  memberId;                       /** 회원ID */
	String  memberNm;                       /** 회원명 */
	String  evlMthdCd;						/** 평가방식코드 */

	List<EvlResultDetailByIemDto> evlResultDetailByIemDtoList; //배점형결과상세 리스트

}
