package aicluster.common.common.dao;

import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtInsider;

@Repository
public interface CmmtInsiderDao {
	CmmtInsider select(String memberId);
}
