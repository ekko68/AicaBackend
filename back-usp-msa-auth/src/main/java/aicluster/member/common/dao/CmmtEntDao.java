package aicluster.member.common.dao;

import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.CmmtEnt;

@Repository
public interface CmmtEntDao {

	CmmtEnt select(String memberId);

	void insert(CmmtEnt cmmtEnt);

	void update(CmmtEnt cmmtEnt);

	void delete(String memberId);



}
