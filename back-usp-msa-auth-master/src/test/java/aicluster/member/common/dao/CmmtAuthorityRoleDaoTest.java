package aicluster.member.common.dao;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import aicluster.member.common.entity.CmmtAuthority;
import aicluster.member.common.entity.CmmtAuthorityRole;
import aicluster.member.common.entity.CmmtRole;
import aicluster.member.support.TestDaoSupport;

//@Slf4j
public class CmmtAuthorityRoleDaoTest extends TestDaoSupport {
	@Autowired
	private CmmtAuthorityRoleDao dao;

	@Autowired
	private CmmtAuthorityDao adao;

	@Autowired
	private CmmtRoleDao rdao;

	private String authorityId = "--id";
	private CmmtAuthorityRole ar;
	private List<CmmtAuthorityRole> list;

	@BeforeEach
	public void init() {
		Date now = new Date();
		CmmtAuthority authority = CmmtAuthority.builder()
				.authorityId(authorityId)
				.authorityNm("--nm")
				.createdDt(now)
				.creatorId("SYSTEM")
				.updatedDt(now)
				.updaterId("SYSTEM")
				.build();
		adao.insert(authority);

		for (int i = 1; i < 4; i++) {
			CmmtRole role = CmmtRole.builder()
					.roleId("ROLE_TEST"+i)
					.roleNm("Unit Test"+i)
					.createdDt(now)
					.creatorId("SYSTEM")
					.updatedDt(now)
					.updaterId("SYSTEM")
					.build();
			rdao.insert(role);
		}

		ar = CmmtAuthorityRole.builder()
				.authorityId(authorityId)
				.roleId("ROLE_TEST1")
				.createdDt(now)
				.creatorId("SYSTEM")
				.build();

		list = new ArrayList<>();
		for (int i = 2; i < 4; i++) {
			CmmtAuthorityRole r = CmmtAuthorityRole.builder()
					.authorityId(authorityId)
					.roleId("ROLE_TEST" + i)
					.createdDt(now)
					.creatorId("SYSTEM")
					.build();
			list.add(r);
		}
	}

	@Test
	public void test() {
		// 여러개 insert
		dao.insertList(list);
		List<CmmtAuthorityRole> l1 = dao.selectList(authorityId);
		assertTrue(list.size() == l1.size());

		// 1개 insert
		dao.insert(ar);
		List<CmmtAuthorityRole> l2 = dao.selectByRoleId(ar.getRoleId());
		assertTrue(l2.size() == 1);

		// 모두 삭제
		dao.deleteAuthorityId(authorityId);
		l1 = dao.selectList(authorityId);
		assertTrue(l1.size() == 0);
	}
}
