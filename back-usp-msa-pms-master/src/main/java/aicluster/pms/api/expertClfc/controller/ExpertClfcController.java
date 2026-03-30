package aicluster.pms.api.expertClfc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aicluster.pms.api.expertClfc.dto.ExpertClfcMapngParam;
import aicluster.pms.api.expertClfc.service.ExpertClfcService;
import aicluster.pms.common.entity.UsptExpertCl;
import aicluster.pms.common.entity.UsptExpertClMapng;
import bnet.library.util.dto.JsonList;
import bnet.library.util.pagination.CorePagination;
import lombok.extern.slf4j.Slf4j;

/**
 * 전문가분류 관리
 * @author brainednet
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/expert-clfc")
public class ExpertClfcController {

	@Autowired
	private ExpertClfcService expertClfcService;

	/**
	 * 전문가단 트리 목록 조회
	 * @return List<ExpertClDto>
	 */

	@GetMapping("")
	public JsonList<UsptExpertCl> getExpertClfcTreeList() {
		return new JsonList<>(expertClfcService.getExpertClfcTreeList());
	}

	/**
	 * 전문가단  목록 조회
	 * @param expertClId
	 * @return List<ExpertClDto>
	 */

	@GetMapping("/{expertClId}")
	public JsonList<UsptExpertCl> getList(@PathVariable("expertClId") String expertClId) {
		log.debug("#####	expertClId : {}", expertClId);
		return new JsonList<>(expertClfcService.getList(expertClId));
	}

	/**
	 * 전문가단 등록,수정
	 * @param expertClId(조회용)
	 * @param expertClfcParamList
	 * @return List<ExpertClDto>
	 */
	@PutMapping("/{expertClId}")
	public JsonList<UsptExpertCl> modifyExpertClfc( @PathVariable("expertClId") String expertClId, @RequestBody(required = false) List<UsptExpertCl> usptExpertClList	) {
		expertClfcService.modifyExpertClfc(usptExpertClList);
		return new JsonList<>(expertClfcService.getList(expertClId));
	}

	/**
	 * 전문가단 삭제
	 * @param expertClId(조회용)
	 * @param expertClfcParamList
	 * @return List<ExpertClDto>
	 */
	@DeleteMapping("/{expertClId}")
	public JsonList<UsptExpertCl> removeExpertClfc(  @PathVariable("expertClId") String expertClId , @RequestBody(required = false) List<UsptExpertCl> usptExpertClList	) {
		expertClfcService.deleteExpertClfc(usptExpertClList);
		return new JsonList<>(expertClfcService.getList(expertClId));
	}


	/**
	 * 전문가단 담당자 목록 조회
	 * @param expertClId
	 * @return List<ExpertClMapngDto>
	 */

	@GetMapping("/{expertClId}/mapng")
	public JsonList<UsptExpertClMapng> getMapngList(@PathVariable("expertClId") String expertClId) {
		log.debug("#####	expertClId : {}", expertClId);
		return new JsonList<>(expertClfcService.getMapngList(expertClId));
	}

	/**
	 * 전문가단 담당자 등록
	 * @param expertClId(조회용)
	 * @param expertClfcParamList
	 * @return List<ExpertClMapngDto>
	 */
	@PostMapping("/{expertClId}/mapng")
	public JsonList<UsptExpertClMapng> addExpertClfcMapng( @PathVariable("expertClId") String expertClId, @RequestBody(required = false)  List<UsptExpertClMapng> usptExpertClMapngList) {
		expertClfcService.addExpertClfcMapng(usptExpertClMapngList);
		return new JsonList<>(expertClfcService.getMapngList(expertClId));
	}

	/**
	 * 전문가단 담당자 삭제
	 * @param expertClId(조회용)
	 * @param expertClfcParamList
	 * @return List<ExpertClMapngDto>
	 */
	@DeleteMapping("/{expertClId}/mapng")
	public JsonList<UsptExpertClMapng> removeExpertClfcMapng( @PathVariable("expertClId") String expertClId, @RequestBody(required = false) List<UsptExpertClMapng> usptExpertClMapngList	) {
		expertClfcService.deleteExpertClfcMapng(usptExpertClMapngList);
		return new JsonList<>(expertClfcService.getMapngList(expertClId));
	}

	/**
	 * 전문가단 담당자 추가 목록 조회(팝업)
	 * @param wrcNm
	 * @param expertNm
	 * @return List<ExpertClMapngDto>
	 */

	@GetMapping("/popUp")
	public CorePagination<UsptExpertClMapng> getExpertList( ExpertClfcMapngParam inputParam, @RequestParam(required = false) Long page){

		return expertClfcService.getExpertList(inputParam, page);
	}
}