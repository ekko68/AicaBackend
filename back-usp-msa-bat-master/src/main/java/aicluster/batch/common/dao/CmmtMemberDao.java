package aicluster.batch.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.batch.common.entity.CmmtMember;

@Repository
public interface CmmtMemberDao {

	CmmtMember select(String memberId);

	List<CmmtMember> selectList_blackMemberToNormal();

	void update(CmmtMember cmmtMember);

	List<CmmtMember> selectList_lastLoginDt(String day);

	int updateToDormant(String day);

	List<CmmtMember> selectList_memberSt(
			@Param("memberSt") String memberSt,
			@Param("day") int day);

	void delete(String memberId);
}
