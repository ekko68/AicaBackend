package aicluster.batch.common.entity;

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
public class CmmtMemberMonthStats implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5864815742904121398L;
	private String statsYm;
	private String memberType;
	private Long totalMbrCnt;
	private Long joinMbrCnt;
	private Long secessionMbrCnt;
	private Long dormantMbrCnt;
	private Long blackMbrCnt;
	private Date createdDt;
}
