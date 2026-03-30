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
public class UsptMvnFc implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 5767146611844490975L;

	private String  mvnFcId      ;                  /** 입주시설ID */
    private String  mvnSt        ;                  /** 입주상태(G:MVN_ST) */
    private Date    mvnStDt      ;                  /** 입주상태변경일시 */
    private String  curOccupantId;                  /** 현재입주자ID(MEMBER_ID) */
    private String  updaterId    ;                  /** 수정자ID(CMMT_MEMBER.MEMBER_ID) */
    private Date    updatedDt    ;                  /** 수정일시 */
}
