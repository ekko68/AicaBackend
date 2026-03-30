package aicluster.member.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.CmmtMemberHist;

@Repository
public interface CmmtMemberHistDao {
	void insert(CmmtMemberHist cmmtMemberHist);
	List<CmmtMemberHist> selectListByMemberId(String memberId);
	void insertList(@Param("list") List<CmmtMemberHist> uplist);
	long selectCount(String memberId);
	List<CmmtMemberHist> selectList(
			@Param("memberId") String memberId,
			@Param("beginRowNum") Long beginRowNum,
			@Param("itemsPerPage") Long itemsPerPage);

}

