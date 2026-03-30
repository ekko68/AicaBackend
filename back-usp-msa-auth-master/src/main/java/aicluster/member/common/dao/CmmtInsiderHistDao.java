package aicluster.member.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.common.dto.InsiderHistDto;
import aicluster.member.common.entity.CmmtInsiderHist;

@Repository
public interface CmmtInsiderHistDao {
	void insert(CmmtInsiderHist insiderHist);

	void insertList(@Param("list") List<CmmtInsiderHist> list);

	long selectCount(String memberId);

	List<InsiderHistDto> selectList(String memberId);
}
