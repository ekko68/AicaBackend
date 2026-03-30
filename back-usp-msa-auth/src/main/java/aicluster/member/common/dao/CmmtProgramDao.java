package aicluster.member.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.CmmtProgram;
import aicluster.member.common.entity.CmmtProgramRole;

@Repository
public interface CmmtProgramDao {
	CmmtProgram select(String programId);

	List<CmmtProgram> selectList(
			@Param("systemId") String systemId,
			@Param("programNm") String programNm,
			@Param("urlPattern") String urlPattern);

	List<CmmtProgramRole> selectByRoleId(String roleId);

	CmmtProgram selectByName(
			@Param("systemId") String systemId,
			@Param("programNm") String programNm);

	CmmtProgram selectByUrlPattern(
			@Param("systemId") String systemId,
			@Param("httpMethod") String httpMethod,
			@Param("urlPattern") String urlPattern);

	void incCheckOrder(long checkOrder);

	void insert(CmmtProgram program);

	void update(CmmtProgram program);

	// delete 기능 추가
	void deleteProgramId(String programId);

	long selectTotalCount();
}
