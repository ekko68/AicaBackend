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
public class LogtIndvdlinfoLog implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -2421174334074882764L;
	private String logId;
	private Date logDt;
	private String memberId;
	private String memberIp;
	private String workTypeNm;
	private String workCn;
	private String trgterId;

	/*
	 * Helper
	 */
	private String memberNm;
	private String trgterNm;
	private Long rn;
}
