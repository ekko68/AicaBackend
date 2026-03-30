package aicluster.member.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.common.dto.MemberDto;
import aicluster.member.common.dto.MemberSelfDto;
import aicluster.member.common.dto.MemberStatsDto;
import aicluster.member.common.dto.MemberStatsListItemDto;
import aicluster.member.common.entity.CmmtMember;

@Repository
public interface CmmtMemberDao {
	CmmtMember select(String memberId);
	CmmtMember selectByLoginId(String loginId);
	CmmtMember selectByNaverToken(String naverToken);
	CmmtMember selectByGoogleToken(String googleToken);
	CmmtMember selectByKakaoToken(String kakaoToken);
	CmmtMember selectByRefreshToken(String refreshToken);
	long selectCountByAuthorityId(String authorityId);
	long selectCount(
			@Param("loginId") String loginId,
			@Param("memberNm") String memberNm,
			@Param("memberSt") String memberSt,
			@Param("memberType") String memberType);
	List<MemberDto> selectList(
			@Param("loginId") String loginId,
			@Param("memberNm") String memberNm,
			@Param("memberSt") String memberSt,
			@Param("memberType") String memberType,
			@Param("beginRowNum") Long beginRowNum,
			@Param("itemsPerPage") Long itemsPerPage);
	void insert(CmmtMember member);
	void update(CmmtMember member);
	MemberStatsDto selectCurrStats();
	List<MemberStatsListItemDto> selectDayStatsList(
			@Param("memberType") String memberType,
			@Param("beginDay") String beginDay,
			@Param("endDay") String endDay);
	List<MemberStatsListItemDto> selectMonthStatsList(
			@Param("memberType") String memberType,
			@Param("beginDay") String beginDay,
			@Param("endDay") String endDay);
	List<CmmtMember> selectByMemberNm(String memberNm);
	CmmtMember selectByCi(String ci);
	MemberSelfDto selectMe(String memberId);
}
