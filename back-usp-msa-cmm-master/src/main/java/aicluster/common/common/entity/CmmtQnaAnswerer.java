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
public class CmmtQnaAnswerer implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 8938367906514298837L;
	private String qnaId;
	private String memberId;
	private String creatorId;
	private Date createdDt;

	/*
	 * Helper
	 */
	private String loginId;
	private String memberNm;
	private String deptNm;
	private String positionNm;
}
