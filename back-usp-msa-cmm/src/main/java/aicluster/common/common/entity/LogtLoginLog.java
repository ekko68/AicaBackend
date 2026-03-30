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
public class LogtLoginLog implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5755613408895009224L;
	private String logId;
	private String apiSystemId;
	private Date logDt;
	private String memberId;
	private String loginId;
	private String memberNm;
	private String memberType;
	private String gender;
	private Integer age;
	private String memberIp;
	private String positionNm;
	private String deptNm;
	private String pstinstNm;
}
