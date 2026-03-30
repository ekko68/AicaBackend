package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtSurveyQuestion;

@Repository
public interface CmmtSurveyQuestionDao {

	List<CmmtSurveyQuestion> selectList(String surveyId);

	CmmtSurveyQuestion select(
			@Param("surveyId") String surveyId,
			@Param("questionId") String questionId);

	List<CmmtSurveyQuestion> selectResultList(
			@Param("surveyId") String surveyId,
			@Param("beginDay") String beginDay,
			@Param("endDay") String endDay);

	void deleteList_surveyId(String surveyId);

	void insertList(@Param("list") List<CmmtSurveyQuestion> questions);



}
