package aicluster.common.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import aicluster.common.common.entity.CmmtEmail;
import aicluster.common.common.util.CodeExt;
import aicluster.common.support.TestDaoSupport;
import bnet.library.util.CoreUtils;
import bnet.library.util.CoreUtils.date;

//@Slf4j
public class CmmtEmailDaoTest extends TestDaoSupport {
	@Autowired
	private CmmtEmailDao emailDao;

	private CmmtEmail email;

	@BeforeEach
	public void init() {
		Date now = new Date();

		email = CmmtEmail.builder()
				.emailId("email-test")
				.title("01011112222")
				.article("안녕하세요?")
				.html(false)
				.send(false)
				.sendDt(null)
				.tryCnt(0)
				.systemId(CodeExt.systemId.사용자지원포털)
				.createdDt(now)
				.build();
	}

	@Test
	public void test() {
		// insert
		emailDao.insert(email);

		// insertList
		List<CmmtEmail> list = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			CmmtEmail sms2 = new CmmtEmail();
			CoreUtils.property.copyProperties(sms2, email);
			sms2.setEmailId(CoreUtils.string.getNewId("email-"));
			list.add(sms2);
		}
		emailDao.insertList(list);
		Date fromDt = new Date();
		fromDt = CoreUtils.date.addDays(fromDt, -1);
		list = emailDao.selectList_notSent(fromDt, 5);
		Assert.assertTrue(list.size() > 1);

		// select
		CmmtEmail s1 = emailDao.select(email.getEmailId());
		Assert.assertEquals(s1, email);

		// selectList_notSent
		fromDt = new Date();
		fromDt = date.addDays(fromDt, -30);
		list = emailDao.selectList_notSent(fromDt, 10);
		Assert.assertTrue(list.size() > 0);

		// update
		email.setSend(true);
		email.setTryCnt(1);
		email.setSendDt(new Date());
		emailDao.update(email);
		s1 = emailDao.select(email.getEmailId());
		Assert.assertEquals(s1, email);

		// delete
		emailDao.delete(email.getEmailId());
		s1 = emailDao.select(email.getEmailId());
		Assert.assertNull(s1);

		// delete_old
		emailDao.insert(email);
		s1 = emailDao.select(email.getEmailId());
		Assert.assertNotNull(s1);
		emailDao.delete_old(new Date());
		s1 = emailDao.select(email.getEmailId());
		Assert.assertNull(s1);
	}
}
