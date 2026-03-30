package aicluster.common.common.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtEmail;

@Repository
public interface CmmtEmailDao {
	void insert(CmmtEmail email);

	void insertList(@Param("list") List<CmmtEmail> list);

	void update(CmmtEmail email);

	void delete(String emailId);

	CmmtEmail select(String emailId);


	List<CmmtEmail> selectList_notSent(
			@Param("fromDt") Date fromDt,
			@Param("maxTryCnt") int maxTryCnt);

	void delete_old(Date dtime);
}
