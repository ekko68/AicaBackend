package aicluster.batch.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import aicluster.batch.common.entity.UsptCheckoutHist;

@Repository
public interface UsptCheckoutHistDao {
	void insertList(List<UsptCheckoutHist> list);
}
