package aicluster.pms.api.expertReqst.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import aicluster.pms.api.expertReqst.dto.ExpertClIdDto;
import aicluster.pms.api.expertReqst.dto.ExpertClIdParntsDto;
import aicluster.pms.api.expertReqst.dto.FrontExpertReqstDto;
import aicluster.pms.api.expertReqst.dto.FrontExpertReqstParam;
import aicluster.pms.api.expertReqst.service.FrontExpertReqstService;
import bnet.library.util.dto.JsonList;
import lombok.extern.slf4j.Slf4j;

/**
 * 전문가 신청 front
 * @author brainednet
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/front/expert-reqst")
public class FrontExpertReqstController {

	@Autowired
	private FrontExpertReqstService frontExpertReqstService;


	/**
	 * 전문가 신청자정보 조회
	 * @return
	 */
	@GetMapping("")
	public FrontExpertReqstDto getExpertReqstInfo() {
		return frontExpertReqstService.getExpertReqstInfo();
	}


	/**
	 * 저장
	 * @param frontExpertReqstParam
	 * @param fileList
	 * @param fileList
	 */
	@PostMapping("")
	public FrontExpertReqstParam add( @RequestPart(value = "info", required = false) FrontExpertReqstParam frontExpertReqstParam
			    		  , @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

		log.debug("#####	getUsptExpert : {}", "["+frontExpertReqstParam.getUsptExpert()+"]");
		log.debug("#####	fileList : {}", "["+fileList+"]");
		return frontExpertReqstService.add(frontExpertReqstParam, fileList );
	}

	/**
	 * 전문가 분류조회_부모전문가분류 조회
	 * @return
	 */
	@GetMapping("/expert-clid/parnts")
	public JsonList<ExpertClIdParntsDto> selectParntsExpertClIdList() {
		return frontExpertReqstService.selectParntsExpertClIdList();
	}

	/**
	 * 전문가 분류조회_전문가분류보 조회
	 * @return
	 */
	@GetMapping("/expert-clid/{expertClId}")
	public JsonList<ExpertClIdDto> selectExpertClIdList(@PathVariable("expertClId") String expertClId) {
		return frontExpertReqstService.selectExpertClIdList(expertClId);
	}

}