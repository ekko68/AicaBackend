package aicluster.common.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import aicluster.common.common.entity.CmmtSms;
import aicluster.common.common.util.CodeExt;
import aicluster.common.support.TestDaoSupport;
import bnet.library.util.CoreUtils;
import bnet.library.util.CoreUtils.date;

//@Slf4j
public class CmmtSmsDaoTest extends TestDaoSupport {
	@Autowired
	private CmmtSmsDao smsDao;

	private CmmtSms sms;

	@BeforeEach
	public void init() {
		Date now = new Date();

		sms = CmmtSms.builder()
				.smsId("sms-test")
				.mobileNo("01011112222")
				.message("안녕하세요?")
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
		smsDao.insert(sms);

		// insertList
		List<CmmtSms> list = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			CmmtSms sms2 = new CmmtSms();
			CoreUtils.property.copyProperties(sms2, sms);
			sms2.setSmsId(CoreUtils.string.getNewId("sms-"));
			list.add(sms2);
		}
		smsDao.insertList(list);
		Date fromDt = new Date();
		fromDt = CoreUtils.date.addDays(fromDt, -1);
		list = smsDao.selectList_notSent(fromDt, 5);
		Assert.assertTrue(list.size() > 1);

		// select
		CmmtSms s1 = smsDao.select(sms.getSmsId());
		Assert.assertEquals(s1, sms);

		// selectList_notSent
		fromDt = new Date();
		fromDt = date.addDays(fromDt, -30);
		list = smsDao.selectList_notSent(fromDt, 10);
		Assert.assertTrue(list.size() > 0);

		// update
		sms.setSend(true);
		sms.setTryCnt(1);
		sms.setSendDt(new Date());
		smsDao.update(sms);
		s1 = smsDao.select(sms.getSmsId());
		Assert.assertEquals(s1, sms);

		// delete
		smsDao.delete(sms.getSmsId());
		s1 = smsDao.select(sms.getSmsId());
		Assert.assertNull(s1);

		// delete_old
		smsDao.insert(sms);
		s1 = smsDao.select(sms.getSmsId());
		Assert.assertNotNull(s1);
		smsDao.delete_old(new Date());
		s1 = smsDao.select(sms.getSmsId());
		Assert.assertNull(s1);
	}
}
