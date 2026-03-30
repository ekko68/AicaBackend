package aicluster.member.common.util.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionFindPwdDto implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5323324177170388782L;
	private String memberId;
	private String mobileNo;
	private String email;
	private String certNo;
	private Date certExpiredDt;
	private boolean checked;
}
