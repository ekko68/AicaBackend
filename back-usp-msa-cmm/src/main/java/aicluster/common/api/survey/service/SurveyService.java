package aicluster.common.api.survey.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.common.api.survey.dto.Question;
import aicluster.common.api.survey.dto.QuestionSaveParam;
import aicluster.common.api.survey.dto.SurveyAddParam;
import aicluster.common.api.survey.dto.SurveyListParam;
import aicluster.common.api.survey.dto.SurveyModifyParam;
import aicluster.common.common.dao.CmmtSurveyAnswerDao;
import aicluster.common.common.dao.CmmtSurveyDao;
import aicluster.common.common.dao.CmmtSurveyMemberAnsDao;
import aicluster.common.common.dao.CmmtSurveyQuestionDao;
import aicluster.common.common.entity.CmmtSurvey;
import aicluster.common.common.entity.CmmtSurveyAnswer;
import aicluster.common.common.entity.CmmtSurveyQuestion;
import aicluster.common.common.util.CodeExt;
import aicluster.common.common.util.SurveyUtils;
import aicluster.framework.common.entity.BnMember;
import aicluster.framework.security.SecurityUtils;
import bnet.library.exception.InvalidationException;
import bnet.library.exception.InvalidationsException;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.dto.JsonList;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;
import bnet.library.util.pagination.CorePaginationParam;

@Service
public class SurveyService {

	@Autowired
	private CmmtSurveyDao cmmtSurveyDao;

	@Autowired
	private CmmtSurveyMemberAnsDao cmmtSurveyMemberAnsDao;

	@Autowired
	private CmmtSurveyAnswerDao cmmtSurveyAnswerDao;

	@Autowired
	private CmmtSurveyQuestionDao cmmtSurveyQuestionDao;

	@Autowired
	private SurveyUtils surveyUtils;

	public CorePagination<CmmtSurvey> getSurveyList(SurveyListParam param, CorePaginationParam pageParam)
	{
		if (param.getBeginDay() == null || param.getEndDay() == null) {
			throw new InvalidationException("설문기간을 입력하세요.");
		}

		long totalItems = cmmtSurveyDao.selectList_count(param);
		CorePaginationInfo info = new CorePaginationInfo(pageParam.getPage(), pageParam.getItemsPerPage(), totalItems);
		List<CmmtSurvey> list = cmmtSurveyDao.selectList(param, info.getBeginRowNum(), info.getItemsPerPage(), info.getTotalItems());
		CorePagination<CmmtSurvey> dto = new CorePagination<>(info, list);
		return dto;
	}

	public CmmtSurvey addSurvey(SurveyAddParam param) {
		Date now = new Date();
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			throw new InvalidationException("권한이 없습니다.");
		}
		if (!string.equals(worker.getMemberType(), CodeExt.memberType.내부사용자)) {
			throw new InvalidationException("권한이 없습니다.");
		}

		InvalidationsException exs = new InvalidationsException();
		if (string.isBlank(param.getSystemId())) {
			exs.add("systemId", "포털구분을 입력하세요.");
		}
		if (string.isBlank(param.getSurveyNm())) {
			exs.add("surveyNm", "설문지명을 입력하세요.");
		}
		if (param.getBeginDay() == null) {
			exs.add("beginDay", "시작일을 입력하세요.");
		}
		if (param.getEndDay() == null) {
			exs.add("endDay", "종료일을 입력하세요.");
		}
		if (param.getEnabled() == null) {
			param.setEnabled(true);
		}
		if (param.getDuplicated() == null) {
			param.setDuplicated(false);
		}

		if (exs.size() > 0) {
			throw exs;
		}

		CmmtSurvey cmmtSurvey = CmmtSurvey.builder()
				.surveyId(string.getNewId("survey-"))
				.surveyNm(param.getSurveyNm())
				.systemId(param.getSystemId())
				.beginDay(param.getBeginDay())
				.endDay(param.getEndDay())
				.remark(param.getRemark())
				.enabled(param.getEnabled())
				.duplicated(param.getDuplicated())
				.createdDt(now)
				.creatorId(worker.getMemberId())
				.updatedDt(now)
				.updaterId(worker.getMemberId())
				.build();
		cmmtSurveyDao.insert(cmmtSurvey);

		cmmtSurvey = cmmtSurveyDao.select(cmmtSurvey.getSurveyId());
		return cmmtSurvey;
	}

	public CmmtSurvey getSurvey(String surveyId) {
		CmmtSurvey cmmtSurvey = cmmtSurveyDao.select(surveyId);
		if (cmmtSurvey == null) {
			throw new InvalidationException("설문지가 존재하지 않습니다.");
		}
		return cmmtSurvey;
	}

	public CmmtSurvey modifySurvey(SurveyModifyParam param) {
		Date now = new Date();
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			throw new InvalidationException("권한이 없습니다.");
		}
		if (!string.equals(worker.getMemberType(), CodeExt.memberType.내부사용자)) {
			throw new InvalidationException("권한이 없습니다.");
		}
		CmmtSurvey cmmtSurvey = cmmtSurveyDao.select(param.getSurveyId());
		if (cmmtSurvey == null) {
			throw new InvalidationException("설문지 정보가 없습니다.");
		}

		InvalidationsException exs = new InvalidationsException();

		if (string.isBlank(param.getSystemId())) {
			exs.add("systemId", "포털구분을 입력하세요.");
		}
		if (string.isBlank(param.getSurveyNm())) {
			exs.add("surveyNm", "설문지명을 입력하세요.");
		}
		if (param.getBeginDay() == null) {
			exs.add("beginDay", "시작일을 입력하세요.");
		}
		if (param.getEndDay() == null) {
			exs.add("endDay", "종료일을 입력하세요.");
		}
		if (param.getEnabled() == null) {
			param.setEnabled(true);
		}
		if (param.getDuplicated() == null) {
			param.setDuplicated(false);
		}

		if (exs.size() > 0) {
			throw exs;
		}

		cmmtSurvey.setSurveyNm(param.getSurveyNm());
		cmmtSurvey.setSystemId(param.getSystemId());
		cmmtSurvey.setBeginDay(param.getBeginDay());
		cmmtSurvey.setEndDay(param.getEndDay());
		cmmtSurvey.setRemark(param.getRemark());
		cmmtSurvey.setEnabled(param.getEnabled());
		cmmtSurvey.setDuplicated(param.getDuplicated());
		cmmtSurvey.setUpdatedDt(now);
		cmmtSurvey.setUpdaterId(worker.getMemberId());
		cmmtSurveyDao.update(cmmtSurvey);

		cmmtSurvey = cmmtSurveyDao.select(cmmtSurvey.getSurveyId());
		return cmmtSurvey;
	}

	public void removeSurvey(String surveyId) {
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			throw new InvalidationException("권한이 없습니다.");
		}
		if (!string.equals(worker.getMemberType(), CodeExt.memberType.내부사용자)) {
			throw new InvalidationException("권한이 없습니다.");
		}
		CmmtSurvey cmmtSurvey = cmmtSurveyDao.select(surveyId);
		if (cmmtSurvey == null) {
			throw new InvalidationException("설문지 정보가 없습니다.");
		}

		// 답변이 존재하는 설문지는 삭제불가
		Boolean ansExists = cmmtSurveyMemberAnsDao.existsSurveyAns(surveyId);
		if (ansExists) {
			throw new InvalidationException("사용자의 답변이 존재하는 설문지는 변경할 수 없습니다.");
		}

		// 진행중인 설문지는 삭제불가
		if (surveyUtils.isOnGoing(cmmtSurvey)) {
			throw new InvalidationException("진행중인 설문은 삭제할 수 없습니다.");
		}

		// 답변 목록 삭제
		cmmtSurveyAnswerDao.deleteList_surveyId(surveyId);

		// 질문 목록 삭제
		cmmtSurveyQuestionDao.deleteList_surveyId(surveyId);

		// 설문지 삭제
		cmmtSurveyDao.delete(surveyId);
	}

	public JsonList<CmmtSurveyQuestion> getQuestionList(String surveyId) {
		CmmtSurvey cmmtSurvey = cmmtSurveyDao.select(surveyId);
		if (cmmtSurvey == null) {
			throw new InvalidationException("설문지 기본정보가 없습니다.");
		}

		List<CmmtSurveyQuestion> questionList = cmmtSurveyQuestionDao.selectList(surveyId);
		List<CmmtSurveyAnswer> allAnswerList = cmmtSurveyAnswerDao.selectList_surveyId(surveyId);

		for (CmmtSurveyQuestion question : questionList) {
			List<CmmtSurveyAnswer> answerList = surveyUtils.findQuestionAnswers(allAnswerList, question);
			question.setAnswerList(answerList);
		}
		return new JsonList<>(questionList);
	}

	public JsonList<CmmtSurveyQuestion> saveQuestions(QuestionSaveParam param) {
		CmmtSurvey cmmtSurvey = cmmtSurveyDao.select(param.getSurveyId());
		if (cmmtSurvey == null) {
			throw new InvalidationException("설문지 기본정보가 없습니다.");
		}

		// 사용자 답변이 존재하는 경우, 수정할 수 없다.
		boolean ansExists = cmmtSurveyMemberAnsDao.existsSurveyAns(param.getSurveyId());
		if (ansExists) {
			throw new InvalidationException("사용자의 답변이 존재하는 설문지는 변경할 수 없습니다.");
		}

		/*
		 * 질문 삭제
		 */
		cmmtSurveyAnswerDao.deleteList_surveyId(param.getSurveyId());
		cmmtSurveyQuestionDao.deleteList_surveyId(param.getSurveyId());

		/*
		 * 질문 추가
		 */
		List<CmmtSurveyQuestion> questionList = new ArrayList<>();
		List<CmmtSurveyAnswer> answerList = new ArrayList<>();
		int questionNo = 0;
		for (Question question : param.getQuestionList()) {
			questionNo++;
			if (question.getRequired() == null) {
				question.setRequired(false);
			}
			if (string.isBlank(question.getQuestionCn())) {
				throw new InvalidationException("[질문 " + questionNo + "] 질문을 입력하세요.");
			}
			if (string.isBlank(question.getQuestionType())) {
				throw new InvalidationException("[질문 " + questionNo + "] 질문유형을 입력하세요.");
			}

			CmmtSurveyQuestion cmmtSurveyQuestion = CmmtSurveyQuestion.builder()
					.surveyId(param.getSurveyId())
					.questionId(string.getNewId("quest-"))
					.questionNo(questionNo)
					.questionType(question.getQuestionType())
					.required(question.getRequired())
					.questionCn(question.getQuestionCn())
					.build();
			questionList.add(cmmtSurveyQuestion);

			int answerNo = 0;
			for (String answerCn : question.getAnswerCnList()) {
				answerNo++;
				if (string.isBlank(answerCn)) {
					throw new InvalidationException("[질문 " + questionNo + " > 항목 " + answerNo + "] 항목 내용을 입력하세요.");
				}
				CmmtSurveyAnswer cmmtSurveyAnswer = CmmtSurveyAnswer.builder()
						.surveyId(cmmtSurveyQuestion.getSurveyId())
						.questionId(cmmtSurveyQuestion.getQuestionId())
						.answerId(string.getNewId("ans-"))
						.answerNo(answerNo)
						.answerCn(answerCn)
						.build();
				answerList.add(cmmtSurveyAnswer);
			}

			if (string.equals(question.getQuestionType(), CodeExt.surveyQuestionType.주관식) && answerNo != 1) {
				throw new InvalidationException("[질문 " + questionNo + "] 주관식 질문은 답변 항목이 1개여야 합니다.");
			}
			else if (!string.equals(question.getQuestionType(), CodeExt.surveyQuestionType.주관식) && answerNo < 2) {
				throw new InvalidationException("[질문 " + questionNo + "] 객관식과 다중선택 질문은 답변 항목이 2개 이상이어야 합니다.");
			}
		}

		cmmtSurveyQuestionDao.insertList(questionList);
		cmmtSurveyAnswerDao.insertList(answerList);

		return getQuestionList(param.getSurveyId());
	}

}
