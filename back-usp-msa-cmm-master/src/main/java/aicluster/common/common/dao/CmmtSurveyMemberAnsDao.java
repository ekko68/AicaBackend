package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.dto.ResultAnswerDto;
import aicluster.common.common.entity.CmmtSurveyMemberAns;
import aicluster.common.common.entity.CmmvUser;

@Repository
public interface CmmtSurveyMemberAnsDao {

	void insertList(@Param("list") List<CmmtSurveyMemberAns> list);

	Boolean existsSurveyAns(String surveyId);

	List<CmmvUser> selectCmmvUserList_answerer(
			@Param("surveyId") String surveyId,
			@Param("beginDay") String beginDay,
			@Param("endDay") String endDay);

	Boolean existsMember(
			@Param("surveyId") String surveyId,
			@Param("memberId") String memberId);

	boolean existsMember_day(
			@Param("surveyId") String surveyId,
			@Param("memberId") String memberId,
			@Param("day") String day);

	List<ResultAnswerDto> selectResultAnswerList(
			@Param("surveyId") String surveyId,
			@Param("beginDay") String beginDay,
			@Param("endDay") String endDay);
}
