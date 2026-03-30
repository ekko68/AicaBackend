package aicluster.batch.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsptMvnCompany implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 6569706153961495574L;

	private String  mvnId          ;                /** 입주ID */
    private String  bsnsCd         ;                /** 사업코드(BS + 8자리 순번) */
    private String  rsltIdxId      ;                /** 성과지표ID */
    private String  bsnsSlctnId    ;                /** 사업선정대상ID */
    private String  mvnFcId        ;                /** 입주시설ID */
    private String  mvnBeginDay    ;                /** 입주시작일 */
    private String  mvnEndDay      ;                /** 입주종료일 */
    private String  cmpnyId        ;                /** 업체ID : CMMT_MEMBER.MEMBER_ID */
    private String  cmpnyNm        ;                /** 업체명 : CMMT_MEMBER.MEMBER_NM */
    private String  mvnCmpnySt     ;                /** 입주업체상태(G:MVN_CMPNY_ST) */
    private Date    mvnCmpnyStDt   ;                /** 입주업체상태변경일시 */
    private String  updaterId      ;                /** 수정자ID(CMMT_MEMBER.MEMBER_ID) */
    private Date    updatedDt      ;                /** 수정일시 */
}
