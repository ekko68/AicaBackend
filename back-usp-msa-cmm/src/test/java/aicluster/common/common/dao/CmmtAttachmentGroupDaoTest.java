package aicluster.common.common.dao;

import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import aicluster.common.support.TestDaoSupport;
import aicluster.framework.common.dao.CmmtAttachmentGroupDao;
import aicluster.framework.common.entity.CmmtAttachmentGroup;

//@Slf4j
public class CmmtAttachmentGroupDaoTest extends TestDaoSupport {
	@Autowired
	private CmmtAttachmentGroupDao dao;

	private CmmtAttachmentGroup cmmtAttachmentGroup;

	@BeforeEach
	public void init() {
		Date now = new Date();
		cmmtAttachmentGroup = CmmtAttachmentGroup.builder()
				.attachmentGroupId("attachmengroup-test")
				.createdDt(now)
				.creatorId("SYSTEM")
				.updatedDt(now)
				.updaterId("SYSTEM")
				.build();
	}

	@Test
	public void test() {
		// insert
		dao.insert(cmmtAttachmentGroup);
		CmmtAttachmentGroup g1 = dao.select(cmmtAttachmentGroup.getAttachmentGroupId());
		Assert.assertEquals(g1.getAttachmentGroupId(), cmmtAttachmentGroup.getAttachmentGroupId());

		// select
		g1 = dao.select(cmmtAttachmentGroup.getAttachmentGroupId());
		Assert.assertEquals(g1.getAttachmentGroupId(), cmmtAttachmentGroup.getAttachmentGroupId());

		// delete
		dao.delete(cmmtAttachmentGroup.getAttachmentGroupId());
		g1 = dao.select(cmmtAttachmentGroup.getAttachmentGroupId());
		Assert.assertNull(g1);
	}
}
