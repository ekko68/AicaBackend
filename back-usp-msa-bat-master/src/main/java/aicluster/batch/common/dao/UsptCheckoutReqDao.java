package aicluster.batch.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import aicluster.batch.common.entity.UsptCheckoutReq;

@Repository
public interface UsptCheckoutReqDao {
	void update(UsptCheckoutReq usptCheckoutReq);

	List<UsptCheckoutReq> selectList_mvnCheckoutYn();
}
