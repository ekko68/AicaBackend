package aicluster.member.common.entity;

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
public class CmmtInsiderHist implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7991152251981031073L;
	private String histId;                         /** 이력ID */
	private Date histDt;                           /** 이력일시 */
	private String memberId;                       /** 회원ID */
	private String workerId;                       /** 작업자ID */
	private String workCn;                         /** 작업내용 */
}
