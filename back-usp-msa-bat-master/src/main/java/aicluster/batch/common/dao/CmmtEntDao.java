package aicluster.batch.common.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface CmmtEntDao {
	void delete(String memberId);
}
