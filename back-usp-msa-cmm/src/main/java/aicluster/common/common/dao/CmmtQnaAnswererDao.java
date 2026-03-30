package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtQnaAnswerer;

@Repository
public interface CmmtQnaAnswererDao {

	CmmtQnaAnswerer select(
			@Param("qnaId") String qnaId,
			@Param("memberId") String memberId);


	List<CmmtQnaAnswerer> selectList(
			@Param("qnaId") String qnaId,
			@Param("memberNm") String memberNm,
			@Param("loginId") String loginId);

	void insert(CmmtQnaAnswerer qa);

	void insertList(@Param("list") List<CmmtQnaAnswerer> list);

	void delete(
			@Param("qnaId") String qnaId,
			@Param("memberId") String memberId);

	void deleteList(String qnaId);

}