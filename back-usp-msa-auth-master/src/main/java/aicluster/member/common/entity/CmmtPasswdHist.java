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
public class CmmtPasswdHist implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3167610183617814074L;
	private String memberId;
	private Date histDt;
	private String encPasswd;

	/*
	 * HELPER
	 */
	private Long rn;
}
