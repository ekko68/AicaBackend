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
public class CmmtLoginTry implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -2745047595319320591L;
	private String memberId;
	private String memberIp;
	private Integer tryCnt;
	private Date updatedDt;
}
