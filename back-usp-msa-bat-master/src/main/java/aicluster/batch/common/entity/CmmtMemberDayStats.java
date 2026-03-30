package aicluster.batch.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmmtMemberDayStats implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5082568119033749141L;
	private String statsYmd;
	private String memberType;
	private String weekDayNm;
	private Long totalMbrCnt = 0L;
	private Long joinMbrCnt = 0L;
	private Long secessionMbrCnt = 0L;
	private Long dormantMbrCnt = 0L;
	private Long blackMbrCnt = 0L;
	private Date createdDt;
}
