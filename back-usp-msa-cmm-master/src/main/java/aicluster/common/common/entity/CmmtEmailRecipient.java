package aicluster.common.common.entity;

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
public class CmmtEmailRecipient implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1353337332540788177L;

	private String  emailId    ;                    /** 이메일ID */
	private String  email      ;                    /** 이메일 */
	private String  recipientNm;                    /** 수신자명 */
	private Boolean send       ;                    /** 발신여부 */
	private Date    sendDt     ;                    /** 발신일시 */
	private Long    tryCnt     ;                    /** 시도회수 */
	private Date    createdDt  ;                    /** 생성일시 */

}
