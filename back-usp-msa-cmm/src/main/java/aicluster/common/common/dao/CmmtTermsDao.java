package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.dto.CmmtTermsListItem;
import aicluster.common.common.entity.CmmtTerms;

@Repository
public interface CmmtTermsDao {
	void insert(CmmtTerms cmmtTerms);

	CmmtTerms select(
		@Param("termsType") String termsType,
		@Param("beginDay") String beginDay,
		@Param("required") boolean required);

	List<CmmtTerms> selectSet(
		@Param("termsType") String termsType,
		@Param("beginDay") String beginDay);

	List<CmmtTermsListItem> selectList_beginDay(
		@Param("termsType") String termsType,
		@Param("srchCd") String srchCd);

	void update(CmmtTerms cmmtTerms);

	void delete(
		@Param("termsType") String termsType,
		@Param("beginDay") String beginDay);

	List<CmmtTerms> select_today(String termsType);
}
