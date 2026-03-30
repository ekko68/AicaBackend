package aicluster.common.common.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtSms;

@Repository
public interface CmmtSmsDao {
	void insert(CmmtSms sms);

	void insertList(@Param("list") List<CmmtSms> list);

	void update(CmmtSms sms);

	void delete(String smsId);

	CmmtSms select(String smsId);


	List<CmmtSms> selectList_notSent(
			@Param("fromDt") Date fromDt,
			@Param("maxTryCnt") int maxTryCnt);

	void delete_old(Date dtime);
}
