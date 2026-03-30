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
public class LogtElapsedTimeLog implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8257113292250101956L;
	private String logId;
	private Date logDt;
	private String url;
	private Long elapsedTime;
	private String apiSystemId;
}
