package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtHldyExcl;

@Repository
public interface CmmtHldyExclDao {

	List<CmmtHldyExcl> selectlist(
			@Param("year") String year,
			@Param("ymdNm") String ymdNm);

	void insert(CmmtHldyExcl holidayExcl);

	CmmtHldyExcl select(String ymd);

	void update(CmmtHldyExcl holidayExcl);

	void delete(String ymd);

}
