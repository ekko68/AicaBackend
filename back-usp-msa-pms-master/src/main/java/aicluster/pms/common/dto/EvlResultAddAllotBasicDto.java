package aicluster.pms.common.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class EvlResultAddAllotBasicDto implements Serializable {

	private static final long serialVersionUID = -1056828571102090423L;

	String  evlCmitId;                      /** 평가위원회ID */
	@JsonIgnore
	String  evlDivCd;                       /** 평가구분코드(G:EVL_DIV) */
	String  evlTrgetId;                     /** 평가대상ID */
	String  evlMthdCd;						/** 평가방식코드 */

	String  adsbtrResnCn;                   /** 가감사유내용 */
	String  taskNmKo;                       /** 과제명(국문) */
	String  applyId;                        /** 신청ID */
	String  receiptNo;                      /** 접수번호(BA + 8자리 순번) */
	String  memberId;                       /** 회원ID */
	String  memberNm;                       /** 회원명 */

	List<EvlResultDetailByIemDto> evlResultDetailByIemDtoList; //배점형결과상세 리스트

}
