package aicluster.batch.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.batch.common.entity.CmmtMemberHist;

@Repository
public interface CmmtMemberHistDao {
	void insert(CmmtMemberHist cmmtMemberHist);
	void insertList(@Param("list") List<CmmtMemberHist> list);
}

