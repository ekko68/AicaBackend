package aicluster.common.api.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.common.api.survey.dto.SurveyAnsParam;
import aicluster.common.api.survey.service.SurveyAnsService;
import aicluster.common.common.entity.CmmtSurvey;
import bnet.library.exception.InvalidationException;

@RestController
@RequestMapping("api/survey-ans")
public class SurveyAnsController {

	@Autowired
	private SurveyAnsService service;

	@GetMapping("/{surveyId}")
	public CmmtSurvey get(@PathVariable String surveyId) {
		return service.get(surveyId);
	}

	@PostMapping("/{surveyId}/ans")
	public void answer(@PathVariable String surveyId, @RequestBody SurveyAnsParam param) {
		if (param == null) {
			throw new InvalidationException("입력이 없습니다.");
		}
		param.setSurveyId(surveyId);
		service.answer(param);
	}
}
