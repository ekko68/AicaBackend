package aicluster.pms.api.evl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.pms.api.evl.service.EvlOnlineService;
import aicluster.pms.common.dto.EvlResultBasicInfoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/front/evl-online")
public class FrontEvlOnlineController {

	@Autowired
	private EvlOnlineService evlOnlineService;


	//평가결과항목별상세(3가지 유형 공통)-front용
	@GetMapping("/{evlResultId}/evl-result-trget")
	public EvlResultBasicInfoDto getEvlResultIemDetail(@PathVariable String evlResultId) {
		log.debug("#####	evlResultId : {}", evlResultId);

		//BnMember worker = SecurityUtils.checkWorkerIsEvaluator();

		return evlOnlineService.getEvlResultIemDetail(evlResultId);
	}
	
	

}
