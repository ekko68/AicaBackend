package aicluster.common.api.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.common.common.dao.CmmtSurveyDao;
import aicluster.common.common.dao.CmmtSurveyMemberAnsDao;
import aicluster.common.common.dao.CmmtSurveyQuestionDao;
import aicluster.common.common.dto.ResultAnswerDto;
import aicluster.common.common.entity.CmmtSurvey;
import aicluster.common.common.entity.CmmtSurveyQuestion;
import aicluster.common.common.entity.CmmvUser;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.dto.JsonList;

@Service
public class SurveyResultService {

	@Autowired
	private CmmtSurveyDao cmmtSurveyDao;

	@Autowired
	private CmmtSurveyQuestionDao cmmtSurveyQuestionDao;

	@Autowired
	private CmmtSurveyMemberAnsDao cmmtSurveyMemberAnsDao;

	private List<ResultAnswerDto> findAnswers(List<ResultAnswerDto> allAnswers, String surveyId, String questionId) {
		List<ResultAnswerDto> answers = new ArrayList<>();
		for (ResultAnswerDto answer: allAnswers) {
			if (string.equals(answer.getSurveyId(), surveyId) && string.equals(answer.getQuestionId(), questionId)) {
				answers.add(answer);
			}
		}
		return answers;
	}

	public CmmtSurvey getResult(String surveyId, String beginDay, String endDay) {
		CmmtSurvey cmmtSurvey = cmmtSurveyDao.select(surveyId);
		if (cmmtSurvey == null) {
			throw new InvalidationException("설문지 정보가 없습니다.");
		}

		if (string.isBlank(beginDay)) {
			beginDay = cmmtSurvey.getBeginDay();
		}
		if (string.isBlank(endDay)) {
			endDay = cmmtSurvey.getEndDay();
		}

		List<CmmtSurveyQuestion> questions = cmmtSurveyQuestionDao.selectResultList(surveyId, beginDay, endDay);
		List<ResultAnswerDto> allAnswers = cmmtSurveyMemberAnsDao.selectResultAnswerList(surveyId, beginDay, endDay);

		for (CmmtSurveyQuestion question: questions) {
			List<ResultAnswerDto> answers = findAnswers(allAnswers, surveyId, question.getQuestionId());
			question.setResultAnswerList(answers);
		}
		cmmtSurvey.setQuestionList(questions);
		return cmmtSurvey;
	}

	public JsonList<CmmvUser> getAnswerers(String surveyId, String beginDay, String endDay) {
		CmmtSurvey cmmtSurvey = cmmtSurveyDao.select(surveyId);
		if (cmmtSurvey == null) {
			throw new InvalidationException("설문지 정보가 없습니다.");
		}

		if (string.isBlank(beginDay)) {
			beginDay = cmmtSurvey.getBeginDay();
		}
		if (string.isBlank(endDay)) {
			endDay = cmmtSurvey.getEndDay();
		}

		List<CmmvUser> list = cmmtSurveyMemberAnsDao.selectCmmvUserList_answerer(surveyId, beginDay, endDay);
		return new JsonList<>(list);
	}

}
