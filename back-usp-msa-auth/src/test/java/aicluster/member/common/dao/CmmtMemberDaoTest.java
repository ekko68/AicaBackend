package aicluster.member.common.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import aicluster.member.common.dto.MemberStatsDto;
import aicluster.member.common.dto.MemberStatsListItemDto;
import aicluster.member.common.entity.CmmtMember;
import aicluster.member.common.util.CodeExt;
import aicluster.member.support.TestDaoSupport;

//@Slf4j
public class CmmtMemberDaoTest extends TestDaoSupport {
	@Autowired
	private CmmtMemberDao dao;

	private CmmtMember cmmtMember;

	@BeforeEach
	public void init() {
		Date now = new Date();

		cmmtMember = CmmtMember.builder()
				.memberId("test-member")
				.loginId("test-login-id")
				.memberNm("테스터이름")
				.authorityId(CodeExt.authorityId.일반사용자)
				.memberSt(CodeExt.memberSt.정상)
				.memberStDt(now)
				.memberType(CodeExt.memberTypeExt.개인)
				.build();
	}

	@Test
	public void test() {
		// insert
		dao.insert(cmmtMember);

		// select
		CmmtMember m1 = dao.select(cmmtMember.getMemberId());
		Assert.assertNotNull(m1);
		Assert.assertEquals(m1.getMemberId(), cmmtMember.getMemberId());

		// 사용자현황
		MemberStatsDto memberStats = dao.selectCurrStats();
		Assert.assertNotNull(memberStats);
		List<MemberStatsListItemDto> list = dao.selectDayStatsList(CodeExt.memberTypeExt.개인, "20210901", "20221231");
		Assert.assertTrue(list.size() > 0);
		list = dao.selectMonthStatsList(CodeExt.memberTypeExt.개인, "20210901", "20221231");
	}
}
