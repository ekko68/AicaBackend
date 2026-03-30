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
public class LogtIndvdlinfoLog implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6174866794638918819L;
	private String logId;
	private Date logDt;
	private String memberId;
	private String memberIp;
	private String workTypeNm;
	private String workCn;
	private String trgterId;
}
