package aicluster.member.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.CmmtProgramRole;

@Repository
public interface CmmtProgramRoleDao {
	List<CmmtProgramRole> selectList(String programId);
	List<CmmtProgramRole> selectByRoleId(String roleId);
	void insertList(@Param("list") List<CmmtProgramRole> list);
	void deleteProgramId(String programId);
}
