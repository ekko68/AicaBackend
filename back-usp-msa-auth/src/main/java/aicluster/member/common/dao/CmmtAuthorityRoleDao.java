package aicluster.member.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.CmmtAuthorityRole;

@Repository
public interface CmmtAuthorityRoleDao {
	List<CmmtAuthorityRole> selectList(String authorityId);
	void insert( CmmtAuthorityRole role );
	void insertList(@Param("list") List<CmmtAuthorityRole> list);
	List<CmmtAuthorityRole> selectByRoleId(String roleId);
	void deleteAuthorityId(String authorityId);
}
