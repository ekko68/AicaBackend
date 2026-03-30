package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtBoardArticleCn;

@Repository
public interface CmmtBoardArticleCnDao {

	void insertList(@Param("list") List<CmmtBoardArticleCn> list);

	List<CmmtBoardArticleCn> selectList(String articleId);

	void deleteList(String articleId);
}
