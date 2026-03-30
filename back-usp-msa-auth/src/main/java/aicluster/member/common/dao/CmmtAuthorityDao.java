package aicluster.member.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.CmmtAuthority;
import aicluster.member.common.entity.CmmtAuthorityRole;

@Repository
public interface CmmtAuthorityDao {
	List<CmmtAuthority> selectAll();
	List<CmmtAuthorityRole> selectByRoleId(String roleId);
	CmmtAuthority select(String authorityId);
	CmmtAuthority selectByName(String authorityNm);
	void insert(CmmtAuthority authority);
	void update(CmmtAuthority authority);
	void delete(String authorityId);
}
