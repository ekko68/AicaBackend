package aicluster.common.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import aicluster.common.support.TestDaoSupport;
import aicluster.framework.common.dao.CmmtAttachmentDao;
import aicluster.framework.common.dao.CmmtAttachmentGroupDao;
import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.framework.common.entity.CmmtAttachmentGroup;

//@Slf4j
public class CmmtAttachmentDaoTest extends TestDaoSupport {
	@Autowired
	private CmmtAttachmentDao dao;

	@Autowired
	private CmmtAttachmentGroupDao groupDao;

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
		Date now = new Date();

		// group insert
		groupDao.insert(cmmtAttachmentGroup);
		CmmtAttachmentGroup g1 = groupDao.select(cmmtAttachmentGroup.getAttachmentGroupId());
		Assert.assertEquals(g1.getAttachmentGroupId(), cmmtAttachmentGroup.getAttachmentGroupId());

		// insertList, selectList
		List<CmmtAttachment> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			CmmtAttachment att = CmmtAttachment.builder()
					.attachmentId("attachment-test-" + i)
					.attachmentGroupId(cmmtAttachmentGroup.getAttachmentGroupId())
					.fileNm("file-name-" + i)
					.contentType("plain/text")
					.fileSize(100L)
					.savedFilePath("saved-file-path-" + i)
					.downloadCnt(0L)
					.fileDeleted(false)
					.createdDt(now)
					.creatorId("SYSTEM")
					.updatedDt(now)
					.updaterId("SYSTEM")
					.build();
			list.add(att);
		}
		dao.insertList(list);
		List<CmmtAttachment> list2 = dao.selectList(cmmtAttachmentGroup.getAttachmentGroupId());
		Assert.assertEquals(list.size(), list2.size());

		// select
		CmmtAttachment att1 = dao.select("attachment-test-0");
		Assert.assertEquals(att1.getAttachmentGroupId(), cmmtAttachmentGroup.getAttachmentGroupId());

		// increaseDownloadCnt
		dao.increaseDownloadCnt(att1.getAttachmentId());
		CmmtAttachment att2 = dao.select(att1.getAttachmentId());
		Assert.assertTrue(att1.getDownloadCnt() + 1L == att2.getDownloadCnt());

		// boolean existsGroupFiles(String attachmentGroupId);
		boolean exists = dao.existsGroupFiles(att1.getAttachmentGroupId());
		Assert.assertTrue(exists);

		// void updateRemoved(String attachmentId);
		dao.updateRemoved(att1.getAttachmentId());
		att2 = dao.select(att1.getAttachmentId());
		Assert.assertTrue(att2.getFileDeleted());

		// void updateRemoved_group(String attachmentGroupId);
		dao.updateRemoved_group(att1.getAttachmentGroupId());
		list2 = dao.selectList(att1.getAttachmentGroupId());
		for (CmmtAttachment att : list2) {
			Assert.assertTrue(att.getFileDeleted());
		}

		// long selectGroupFileSize(String attachmentGroupId);
		long size = dao.selectGroupFileSize(att1.getAttachmentGroupId());
		Assert.assertEquals(size, 0L);

		// void delete(String attachmentId);
		dao.delete(att1.getAttachmentId());
		att2 = dao.select(att1.getAttachmentId());
		Assert.assertNull(att2);

	}
}
