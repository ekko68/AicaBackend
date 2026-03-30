package aicluster.member.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.CmmtPasswdHist;

@Repository
public interface CmmtPasswdHistDao {
	int insert(CmmtPasswdHist hist);

	List<CmmtPasswdHist> selectList_recent(
			@Param("memberId") String memberId,
			@Param("cnt") int cnt);
}
