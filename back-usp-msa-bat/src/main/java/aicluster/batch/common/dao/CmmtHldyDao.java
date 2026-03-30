package aicluster.batch.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.batch.common.entity.CmmtHldy;

@Repository
public interface CmmtHldyDao {

	void deleteNotUserDsgn(String yyyymm);

	void insertList(@Param("list") List<CmmtHldy> list);

}
