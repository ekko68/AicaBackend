package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtSurveyAnswer;

@Repository
public interface CmmtSurveyAnswerDao {

	List<CmmtSurveyAnswer> selectList_surveyId(String surveyId);

	void deleteList_surveyId(String surveyId);

	void insertList(@Param("list") List<CmmtSurveyAnswer> answers);
}
