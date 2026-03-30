package aicluster.member.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.api.insider.dto.InsiderListParam;
import aicluster.member.api.insider.dto.SrchPicItemDto;
import aicluster.member.api.insider.dto.SrchPicParam;
import aicluster.member.common.dto.InsiderDto;
import aicluster.member.common.entity.CmmtInsider;

@Repository
public interface CmmtInsiderDao {
	CmmtInsider selectByLoginId(String loginId);
	CmmtInsider select(String memberId);
	CmmtInsider selectByRefreshToken(String refreshToken);
	long selectCountByAuthorityId(String authorityId);
	long selectCount(
			@Param("param") InsiderListParam param
			);
	List<InsiderDto> selectList(
			@Param("param") InsiderListParam param,
			@Param("beginRowNum") Long beginRowNum,
			@Param("itemsPerPage") Long itemsPerPage);
	void insert(CmmtInsider insider);
	void update(CmmtInsider insider);
	Long selectSrchPicCount(
			@Param("param") SrchPicParam param);
	List<SrchPicItemDto> selectSrchPicList(
			@Param("param") SrchPicParam param,
			@Param("beginRowNum") Long beginRowNum,
			@Param("itemsPerPage") Long itemsPerPage);
}
