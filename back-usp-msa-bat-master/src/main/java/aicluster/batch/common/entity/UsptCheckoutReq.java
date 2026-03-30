package aicluster.batch.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsptCheckoutReq implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 3203838769564277143L;

	private String  checkoutReqId  ;                /** 퇴실신청ID */
    private String  mvnId          ;                /** 입주ID */
    private Date    checkoutReqDt  ;                /** 퇴실신청일시 */
    private String  checkoutPlanDay;                /** 퇴실예정일 */
    private String  checkoutReqSt  ;                /** 퇴실신청상태(G:CHECKOUT_REQ_ST) */
    private Date    checkoutReqStDt;                /** 퇴실신청상태변경일시 */
    private boolean mvnCheckoutYn  ;                /** 입주업체퇴실여부 */
    private String  updaterId      ;                /** 수정자ID(CMMT_MEMBER.MEMBER_ID) */
    private Date    updatedDt      ;                /** 수정일시 */
}
