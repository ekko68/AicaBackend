package aicluster.member.api.member.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlackListDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6753392276518699840L;
	private String memberId;
	private String[] registerReasons;
	private String limitBeginDt;
	private String limitEndDt;
	private String detailReason;
}
