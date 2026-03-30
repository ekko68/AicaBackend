package aicluster.common.common.dao;

import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtAuthority;

@Repository
public interface CmmtAuthorityDao {

	CmmtAuthority select(String authorityId);

}
