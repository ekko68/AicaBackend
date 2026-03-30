package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtEmailRecipient;

@Repository
public interface CmmtEmailRecipientDao {
	void insert(CmmtEmailRecipient email);

	void insertList(@Param("list") List<CmmtEmailRecipient> list);

	void deleteList(String emailId);

	List<CmmtEmailRecipient> selectList(String emailId);
}
