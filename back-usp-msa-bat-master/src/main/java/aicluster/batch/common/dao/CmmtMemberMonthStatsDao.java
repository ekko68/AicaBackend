package aicluster.batch.common.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface CmmtMemberMonthStatsDao {

	int upsert(String ym);

}
