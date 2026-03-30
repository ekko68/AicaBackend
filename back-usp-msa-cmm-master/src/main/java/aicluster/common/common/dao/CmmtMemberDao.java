package aicluster.common.common.dao;

import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtMember;

@Repository
public interface CmmtMemberDao {
	CmmtMember select(String memberId);
}
