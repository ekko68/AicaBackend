package aicluster.pms.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class EvlResultDetailByIemDto implements Serializable {

	private static final long serialVersionUID = 116486102493550918L;

	String  flag; 							/** 변경 플래그(I,U,D) */

	String  evlIemNm;                       /** 평가항목명 */
	String  evlIemId;                       /** 평가항목ID */

	String  evlTrgetId;                     /** 평가대상ID */
	String  evlCmitId;                      /** 평가위원회ID */
	String  evlCn;							/** 평가내용 */

	String  evlIemCn;                       /** 평가항목내용 */
	String  adsbtrResnCn;       			/** 가감사유내용 */
	Integer allotScore;                     /** 배점점수 */
	Integer evlScore;                       /** 평가점수 */
	String  evlResultId;                    /** 평가결과ID - 일반 평가 항목*/
	String  evlIemResultId;                 /** 평가항목결과ID */
	String  evlIemResultAdsbtrId;           /** 평가항목결과가감ID - 가감 평가 항목*/

	@JsonIgnore
	private String creatorId;		/** 생성자 ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */
	@JsonIgnore
	private String updaterId;		/** 수정자 ID */
	@JsonIgnore
	private Date updatedDt;			/** 수정일시 */

}
