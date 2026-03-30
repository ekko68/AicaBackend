package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.api.survey.dto.SurveyListParam;
import aicluster.common.common.entity.CmmtSurvey;

@Repository
public interface CmmtSurveyDao {

	void insert(CmmtSurvey survey);

	CmmtSurvey select(String surveyId);

	long selectList_count(@Param("param") SurveyListParam param);

	List<CmmtSurvey> selectList(
			@Param("param") SurveyListParam param,
			@Param("beginRowNum") Long beginRowNum,
			@Param("itemsPerPage") Long itemsPerPage,
			@Param("totalItems") Long totalItems);

	void delete(String surveyId);

	void update(CmmtSurvey dbSurvey);
}
