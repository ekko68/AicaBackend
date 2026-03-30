package aicluster.common.api.popup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import aicluster.common.api.popup.dto.PopupGetListParam;
import aicluster.common.api.popup.service.PopupService;
import aicluster.common.common.entity.CmmtPopup;
import bnet.library.util.dto.JsonList;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationParam;
import lombok.RequiredArgsConstructor;

//@Slf4j
@RestController
@RequestMapping("api/popups")
@RequiredArgsConstructor
public class PopupController {

	@Autowired
	private PopupService popupService;

	/**
	 * 팝업공지 목록 조회
	 *
	 * @param param: 검색조건(systemId, posting, title, beginDt, endDt)
	 * @param pageParam
	 * @return
	 */
	@GetMapping("")
	public CorePagination<CmmtPopup> getList(PopupGetListParam param, CorePaginationParam pageParam) {
		return popupService.getlist(param, pageParam);
	}

	/**
	 * 팝업공지 등록
	 *
	 * @param popup
	 * @param attachment
	 * @return
	 */
	@PostMapping("")
	public CmmtPopup add(
				@RequestPart(value = "popup", required = true) CmmtPopup popup,
				@RequestPart(value = "attachment", required = false) MultipartFile attachment) {
		CmmtPopup cmmtPopup = popupService.addPopup(popup, attachment);
		return cmmtPopup;
	}

	/**
	 * 팝업공지 조회
	 *
	 * @param popupId
	 * @return
	 */
	@GetMapping("/{popupId}")
	public CmmtPopup get(@PathVariable String popupId) {
		CmmtPopup cmmtPopup = popupService.getPopup(popupId);
		return cmmtPopup;
	}

	/**
	 * 팝업공지 수정
	 *
	 * @param popupId
	 * @param popup
	 * @param attachment
	 * @return
	 */
	@PutMapping("/{popupId}")
	public CmmtPopup modify(
				@PathVariable String popupId,
				@RequestPart(value = "popup", required = true) CmmtPopup popup,
				@RequestPart(value = "attachment", required = false) MultipartFile attachment) {
		popup.setPopupId(popupId);
		CmmtPopup cmmtPopup = popupService.modifyPopup(popup, attachment);
		return cmmtPopup;
	}

	/**
	 * 팝업공지 삭제
	 *
	 * @param popupId
	 */
	@DeleteMapping("/{popupId}")
	public void remove(@PathVariable String popupId) {
		popupService.removePopup(popupId);
	}

	/**
	 * 지금 유효한 팝업공지 ID 목록 조회
	 *
	 * @param systemId
	 * @return
	 */
	@GetMapping("/now/{systemId}")
	public JsonList<CmmtPopup> getTodayList(@PathVariable String systemId) {
		return popupService.getTodayList(systemId);
	}

	/**
	 * 팝업공지 게시상태변경
	 *
	 * @param popupId
	 * @param posting
	 * @return
	 */
	@PutMapping("/{popupId}/posting")
	public CmmtPopup modifyPosting(@PathVariable String popupId, Boolean posting) {
		CmmtPopup cmmtPopup = popupService.modifyStatus(popupId, posting);
		return cmmtPopup;
	}

}
