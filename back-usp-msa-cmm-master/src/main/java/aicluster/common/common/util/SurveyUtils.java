package aicluster.common.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aicluster.common.common.dao.CmmtSurveyMemberAnsDao;
import aicluster.common.common.entity.CmmtSurvey;
import aicluster.common.common.entity.CmmtSurveyAnswer;
import aicluster.common.common.entity.CmmtSurveyQuestion;
import bnet.library.util.CoreUtils.date;
import bnet.library.util.CoreUtils.string;

@Component
public class SurveyUtils {

	@Autowired
	private CmmtSurveyMemberAnsDao cmmtSurveyMemberAnsDao;

	/**
	 * 설문이 현재 진행중인가?
	 *
	 * @param cmmtSurvey
	 * @return
	 */
	public boolean isOnGoing(CmmtSurvey cmmtSurvey) {
		if (!cmmtSurvey.getEnabled()) {
			return false;
		}
		Date now = new Date();
		String today = date.format(now, "yyyyMMdd");
		boolean between = string.compare(cmmtSurvey.getBeginDay(), today) <= 0 && string.compare(today, cmmtSurvey.getEndDay()) <= 0;
		return between;
	}

	public boolean answered(CmmtSurvey cmmtSurvey, String memberId) {
		Date now = new Date();
		String today = date.format(now, "yyyyMMdd");

		// 중복응답 허용인 경우
		if (cmmtSurvey.getDuplicated()) {
			boolean exists = cmmtSurveyMemberAnsDao.existsMember_day(cmmtSurvey.getSurveyId(), memberId, today);
			if (exists) {
				return true;
			}
		}
		// 중복응답 불가인 경우
		else {
			boolean exists = cmmtSurveyMemberAnsDao.existsMember(cmmtSurvey.getSurveyId(), memberId);
			if (exists) {
				return true;
			}
		}

		return false;
	}
	/**
	 * 질문과 답변목록을 연결한다.
	 *
	 * @param allAnswers
	 * @param cmmtSurveyQuestion
	 * @return
	 */
	public List<CmmtSurveyAnswer> findQuestionAnswers(List<CmmtSurveyAnswer> allAnswers, CmmtSurveyQuestion cmmtSurveyQuestion) {
		List<CmmtSurveyAnswer> answers = new ArrayList<>();
		for (CmmtSurveyAnswer answer: allAnswers) {
			if (string.equals(answer.getSurveyId(), cmmtSurveyQuestion.getSurveyId()) && string.equals(answer.getQuestionId(), cmmtSurveyQuestion.getQuestionId())) {
				answers.add(answer);
			}
		}
		return answers;
	}

}
