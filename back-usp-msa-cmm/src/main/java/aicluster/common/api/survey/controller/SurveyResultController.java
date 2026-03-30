package aicluster.common.api.survey.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.common.api.survey.service.SurveyResultService;
import aicluster.common.common.entity.CmmtSurvey;
import aicluster.common.common.entity.CmmvUser;
import bnet.library.util.CoreUtils.date;
import bnet.library.util.dto.JsonList;

@RestController
@RequestMapping("/api/survey/{surveyId}/result")
public class SurveyResultController {

	@Autowired
	private SurveyResultService service;

	@GetMapping("")
	public CmmtSurvey getResult(@PathVariable String surveyId, Date beginDay, Date endDay) {
		String fmtBeginDay = date.format(beginDay, "yyyyMMdd");
		String fmtEndDay = date.format(endDay, "yyyyMMdd");
		return service.getResult(surveyId, fmtBeginDay, fmtEndDay);
	}

	@GetMapping("/answerers")
	public JsonList<CmmvUser> getAnswerers(@PathVariable String surveyId, Date beginDay, Date endDay) {
		String fmtBeginDay = date.format(beginDay, "yyyyMMdd");
		String fmtEndDay = date.format(endDay, "yyyyMMdd");

		return service.getAnswerers(surveyId, fmtBeginDay, fmtEndDay);
	}
}
