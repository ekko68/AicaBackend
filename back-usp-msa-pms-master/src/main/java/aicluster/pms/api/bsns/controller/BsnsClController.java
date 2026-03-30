package aicluster.pms.api.bsns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.pms.api.bsns.service.BsnsClService;
import aicluster.pms.common.entity.UsptBsnsCl;
import bnet.library.util.dto.JsonList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 기준사업 분류
 * @author brainednet
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/bsns-cl")
@RequiredArgsConstructor
public class BsnsClController {

	@Autowired
	private BsnsClService bsnsClService;


	@GetMapping("")
	public JsonList<UsptBsnsCl> getList(String parentBsnsClId) {
		log.debug("#####	parentBsnsClId : {}", parentBsnsClId);
		return bsnsClService.getList(parentBsnsClId);
	}


	@PostMapping("")
	public JsonList<UsptBsnsCl> add(@RequestBody List<UsptBsnsCl> infoList) {
		bsnsClService.add(infoList);
		if(infoList != null) {
			return bsnsClService.getList(infoList.get(0).getParentBsnsClId());
		} else {
			return bsnsClService.getList(null);
		}
	}


	@DeleteMapping("")
	public JsonList<UsptBsnsCl> remove(@RequestBody List<UsptBsnsCl> infoList) {
		bsnsClService.remove(infoList);
		if(infoList != null) {
			return bsnsClService.getList(infoList.get(0).getParentBsnsClId());
		} else {
			return bsnsClService.getList(null);
		}
	}
}
