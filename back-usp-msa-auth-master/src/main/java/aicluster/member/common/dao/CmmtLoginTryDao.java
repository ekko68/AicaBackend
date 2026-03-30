package aicluster.member.common.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.CmmtLoginTry;

@Repository
public interface CmmtLoginTryDao {
	void save(
			@Param("memberId") String memberId,
			@Param("memberIp") String memberIp);
	CmmtLoginTry select(
			@Param("memberId") String memberId,
			@Param("memberIp") String memberIp);
	void delete(
			@Param("memberId") String memberId,
			@Param("memberIp") String memberIp);
	void deleteOld(
			@Param("memberId") String memberId,
			@Param("memberIp") String memberIp);
}
