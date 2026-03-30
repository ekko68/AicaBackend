package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtBoardCmmnt;

@Repository
public interface CmmtBoardCmmntDao {

	void deleteList(String articleId);

	long selectList_count(String articleId);

	List<CmmtBoardCmmnt> selectList(
			@Param("articleId") String articleId,
			@Param("latest") Boolean latest,
			@Param("beginRowNum") Long beginRowNum,
			@Param("itemsPerPage") Long itemsPerPage,
			@Param("totalItems") Long totalItems);

	void insert(CmmtBoardCmmnt boardCmmnt);

	CmmtBoardCmmnt select(
			@Param("articleId") String articleId,
			@Param("cmmntId") String cmmntId);

	void update(CmmtBoardCmmnt boardCmmnt);

	void delete(
			@Param("articleId") String articleId,
			@Param("cmmntId") String cmmntId);

}