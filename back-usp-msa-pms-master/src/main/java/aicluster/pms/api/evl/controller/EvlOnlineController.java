package aicluster.pms.api.evl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import aicluster.pms.api.evl.dto.EvasResnCnDto;
import aicluster.pms.api.evl.dto.EvlCharmnOpinionDto;
import aicluster.pms.api.evl.dto.EvlResultDto;
import aicluster.pms.api.evl.dto.EvlTableDto;
import aicluster.pms.api.evl.dto.EvlTableParam;
import aicluster.pms.api.evl.dto.EvlTargetListParam;
import aicluster.pms.api.evl.dto.EvlTrgetIdParam;
import aicluster.pms.api.evl.dto.PointListDto;
import aicluster.pms.api.evl.dto.PointListParam;
import aicluster.pms.api.evl.dto.PointParam;
import aicluster.pms.api.evl.service.EvlOnlineService;
import aicluster.pms.common.dto.EvlAtendListDto;
import aicluster.pms.common.dto.EvlResultAddAllotBasicDto;
import aicluster.pms.common.dto.EvlResultBasicInfoDto;
import aicluster.pms.common.dto.EvlResultByTargetListDto;
import aicluster.pms.common.dto.EvlResutlDspthInfoDto;
import aicluster.pms.common.dto.EvlSttusListDto;
import aicluster.pms.common.dto.EvlTargetListDto;
import aicluster.pms.common.dto.OnlineEvlResutlAgreDto;
import aicluster.pms.common.dto.OnlineEvlResutlDto;
import aicluster.pms.common.dto.OnlineEvlResutlGnrlzDto;
import aicluster.pms.common.entity.UsptEvlMfcmm;
import aicluster.pms.common.util.Code;
import bnet.library.excel.dto.ExcelSheet;
import bnet.library.excel.dto.ExcelWorkbook;
import bnet.library.util.dto.JsonList;
import bnet.library.util.pagination.CorePagination;
import bnet.library.view.ExcelView;
import lombok.extern.slf4j.Slf4j;

/**
 * 평가진행관리 - 온라인 평가
 * @author brainednet
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/evl-online")
public class EvlOnlineController {

	@Autowired
	private EvlOnlineService evlOnlineService;


	/**
	 * 평가진행 목록조회
	 * @param evlCmitExtrcTargetParam
	 * @param page
	 * @return
	 */
	@GetMapping("/target-list")
	public CorePagination<EvlTargetListDto> getEvlTargetList(EvlTargetListParam param, @RequestParam(defaultValue = "1") Long page) {
		return evlOnlineService.getEvlTargetList(param, page);
	}


	/**
	 * 평가진행 목록 엑셀 다운로드
	 * @param param
	 * @return
	 */
	@GetMapping("/target-list/excel-dwld")
	public ModelAndView getEvlTargetListExcel(EvlTargetListParam param){
		List<EvlTargetListDto> list = evlOnlineService.getEvlTargetListExcel(param);

		ExcelWorkbook wb = new ExcelWorkbook();
		wb.setFilename("평가진행 관리 목록");
		ExcelSheet<EvlTargetListDto> sheet = new ExcelSheet<>();
		sheet.addRows(list);
		sheet.setHeaders("진행상태", "평가일시", "사업년도", "공고명", "평가위원회명", "평가유형", "평가단계", "분과", "모집유형", "접수차수", "간사", "평가예정일", "통보상태", "평가방법");
		sheet.setProperties("EvlSttusNm", "evlSttusDate", "bsnsYear", "pblancNm", "evlCmitNm", "evlTypeCd", "evlStepNm", "sectNm", "ordtmRcritNm", "rceptOdr", "orgnzrNm", "evlPrarnde", "dspthSttusNm", "evlMthNm");
		sheet.setTitle("평가진행 관리 목록");
		sheet.setSheetName("평가진행 관리 목록");
		wb.addSheet(sheet);
		return ExcelView.getView(wb);
	}


	/**
	 * 온라인평가 기본정보 조회
	 * @param evlCmitId
	 * @return
	 */
	@GetMapping("/{evlCmitId}/basic")
	public EvlTargetListDto getEvlBasicInfo(@PathVariable("evlCmitId") String evlCmitId) {
		return evlOnlineService.getEvlBasicInfo(evlCmitId);
	}


	/**
	 * 평가위원 출석표 목록 조회
	 * @param evlCmitId
	 * @return
	 */
	@GetMapping("/{evlCmitId}/atend")
	public JsonList<EvlAtendListDto> getEvlAtendList(@PathVariable("evlCmitId") String evlCmitId) {
		return new JsonList<>(evlOnlineService.getEvlAtendList(evlCmitId));
	}


	/**
	 * 위원장 설정
	 * @param evlCmitId
	 * @param usptEvlMfcmm
	 * @return
	 */
	@PutMapping("/{evlCmitId}/charmn")
	public JsonList<EvlAtendListDto> modifyCharmn(@PathVariable("evlCmitId") String evlCmitId, @RequestBody UsptEvlMfcmm usptEvlMfcmm) {
		usptEvlMfcmm.setEvlCmitId(evlCmitId);
		evlOnlineService.modifyCharmn(usptEvlMfcmm);
		return new JsonList<>(evlOnlineService.getEvlAtendList(evlCmitId));
	}


	/**
	 * 위원 불참 처리
	 * @param evlCmitId
	 * @param evlMfcmmId
	 * @param usptEvlMfcmm
	 * @return
	 */
	@PutMapping("/{evlCmitId}/atend/{evlMfcmmId}/nonatd")
	public JsonList<EvlAtendListDto> modifyAttendNonatd(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlMfcmmId") String evlMfcmmId) {
		UsptEvlMfcmm uemInfo = new UsptEvlMfcmm();
		uemInfo.setEvlCmitId(evlCmitId);
		uemInfo.setEvlMfcmmId(evlMfcmmId);
		uemInfo.setAtendSttusCd(Code.atendSttus.불참);
		evlOnlineService.modifyAtendSttus(uemInfo);
		return new JsonList<>(evlOnlineService.getEvlAtendList(evlCmitId));
	}
	/**
	 * 위원 불참 취소 처리
	 * @param evlCmitId
	 * @param evlMfcmmId
	 * @param usptEvlMfcmm
	 * @return
	 */
	@PutMapping("/{evlCmitId}/atend/{evlMfcmmId}/nonatd-cancle")
	public JsonList<EvlAtendListDto> modifyAttendNonatdCancle(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlMfcmmId") String evlMfcmmId) {
		UsptEvlMfcmm uemInfo = new UsptEvlMfcmm();
		uemInfo.setEvlCmitId(evlCmitId);
		uemInfo.setEvlMfcmmId(evlMfcmmId);
		uemInfo.setAtendSttusCd(Code.atendSttus.불참);
		evlOnlineService.modifyAtendSttusCancel(uemInfo);
		return new JsonList<>(evlOnlineService.getEvlAtendList(evlCmitId));
	}


	/**
	 * 위원 회피 처리
	 * @param evlCmitId
	 * @param evlMfcmmId
	 * @param usptEvlMfcmm
	 * @return
	 */
	@PutMapping("/{evlCmitId}/atend/{evlMfcmmId}/evas")
	public JsonList<EvlAtendListDto> modifyAttendEvas(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlMfcmmId") String evlMfcmmId, @RequestBody UsptEvlMfcmm uemInfo) {
		uemInfo.setEvlCmitId(evlCmitId);
		uemInfo.setEvlMfcmmId(evlMfcmmId);
		uemInfo.setAtendSttusCd(Code.atendSttus.회피);
		evlOnlineService.modifyAtendSttus(uemInfo);
		return new JsonList<>(evlOnlineService.getEvlAtendList(evlCmitId));
	}
	/**
	 * 위원 회피 취소 처리
	 * @param evlCmitId
	 * @param evlMfcmmId
	 * @param usptEvlMfcmm
	 * @return
	 */
	@PutMapping("/{evlCmitId}/atend/{evlMfcmmId}/evas-cancle")
	public JsonList<EvlAtendListDto> modifyAttendEvasCancle(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlMfcmmId") String evlMfcmmId) {
		UsptEvlMfcmm uemInfo = new UsptEvlMfcmm();
		uemInfo.setEvlCmitId(evlCmitId);
		uemInfo.setEvlMfcmmId(evlMfcmmId);
		uemInfo.setAtendSttusCd(Code.atendSttus.회피);
		evlOnlineService.modifyAtendSttusCancel(uemInfo);
		return new JsonList<>(evlOnlineService.getEvlAtendList(evlCmitId));
	}


	/**
	 * 회피사유 조회
	 * @param evlCmitId
	 * @param evlMfcmmId
	 * @return
	 */
	@GetMapping("/{evlCmitId}/atend/{evlMfcmmId}/evas-resn")
	public EvasResnCnDto getEvasResnCn(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlMfcmmId") String evlMfcmmId) {
		UsptEvlMfcmm usptEvlMfcmm = new UsptEvlMfcmm();
		usptEvlMfcmm.setEvlCmitId(evlCmitId);
		usptEvlMfcmm.setEvlMfcmmId(evlMfcmmId);
		return evlOnlineService.getEvasResnCn(usptEvlMfcmm);
	}


	/**
	 *  회피사유 수정
	 * @param evlCmitId
	 * @param evlMfcmmId
	 * @param usptEvlMfcmm
	 */
	@PutMapping("/{evlCmitId}/atend/{evlMfcmmId}/evas-resn")
	public void modifyEvasResnCn(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlMfcmmId") String evlMfcmmId, @RequestBody UsptEvlMfcmm usptEvlMfcmm) {
		usptEvlMfcmm.setEvlCmitId(evlCmitId);
		usptEvlMfcmm.setEvlMfcmmId(evlMfcmmId);
		evlOnlineService.modifyEvasResnCn(usptEvlMfcmm);
	}


	/**
	 * 평가현황 목록 조회
	 * @param evlCmitId
	 * @return
	 */
	@GetMapping("/{evlCmitId}/evl-sttus")
	public JsonList<EvlSttusListDto> getEvlSttusList(@PathVariable("evlCmitId") String evlCmitId) {
		return new JsonList<>(evlOnlineService.getEvlSttusList(evlCmitId));
	}


	/**
	 * 가점부여 조회
	 * @param evlCmitId
	 * @param param
	 * @return
	 */
	@GetMapping("/{evlCmitId}/evl-sttus/{evlTrgetId}/add-point")
	public PointListDto getAddPointList(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlTrgetId") String evlTrgetId, PointListParam param) {
		param.setEvlCmitId(evlCmitId);
		param.setEvlTrgetId(evlTrgetId);
		param.setEvlDivCd(Code.evlDiv.가점);
		return evlOnlineService.getPointList(param);
	}

	/**
	 * 가점부여 저장
	 * @param evlCmitId
	 * @param evlTrgetId
	 * @param param
	 */
	@PutMapping("/{evlCmitId}/evl-sttus/{evlTrgetId}/add-point")
	public void modifyAddPoint(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlTrgetId") String evlTrgetId, @RequestBody PointParam param) {
		evlOnlineService.modifyPoint(evlCmitId, evlTrgetId, param);
	}


	/**
	 * 감정부여 조회
	 * @param evlCmitId
	 * @param param
	 * @return
	 */
	@GetMapping("/{evlCmitId}/evl-sttus/{evlTrgetId}/sub-point")
	public PointListDto getSubPointList(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlTrgetId") String evlTrgetId, PointListParam param) {
		param.setEvlCmitId(evlCmitId);
		param.setEvlTrgetId(evlTrgetId);
		param.setEvlDivCd(Code.evlDiv.감점);
		return evlOnlineService.getPointList(param);
	}

	/**
	 * 감정부여 저장
	 * @param evlCmitId
	 * @param evlTrgetId
	 * @param param
	 */
	@PutMapping("/{evlCmitId}/evl-sttus/{evlTrgetId}/sub-point")
	public void modifySubPoint(@PathVariable("evlCmitId") String evlCmitId, @PathVariable("evlTrgetId") String evlTrgetId, @RequestBody PointParam param) {
		evlOnlineService.modifyPoint(evlCmitId, evlTrgetId, param);
	}


	/**
	 * 평가표 조회
	 * @param evlCmitId
	 * @param param
	 * @return
	 */
	@GetMapping("/{evlCmitId}/evl-table")
	public EvlTableDto getEvlTable(@PathVariable("evlCmitId") String evlCmitId, EvlTableParam param) {
		return evlOnlineService.getEvlTable(param);
	}


	/**
	 * 위원장 의견 조회
	 * @param evlCmitId
	 * @return
	 */
	@GetMapping("/{evlCmitId}/charmn-opinion")
	public EvlCharmnOpinionDto getEvlCharmnOpinion(@PathVariable("evlCmitId") String evlCmitId) {
		return evlOnlineService.getEvlCharmnOpinion(evlCmitId);
	}


	/**
	 * 평가결과 조회
	 * @param evlCmitId
	 * @return
	 */
	@GetMapping("/{evlCmitId}/evl-result")
	public EvlResultDto getEvlResult(@PathVariable("evlCmitId") String evlCmitId) {
		return evlOnlineService.getEvlResult(evlCmitId);
	}


	/**
	 * 선정 처리
	 * @param evlCmitId
	 * @param evlTrgetList
	 */
	@PutMapping("/{evlCmitId}/evl-result/evl-slctn")
	public void modifyEvlSlctn(@PathVariable("evlCmitId") String evlCmitId, @RequestBody List<EvlTrgetIdParam> evlTrgetList) {
		evlOnlineService.modifyEvlSlctn(evlCmitId, evlTrgetList, true);
	}


	/**
	 * 탈락 처리
	 * @param evlCmitId
	 * @param evlTrgetList
	 */
	@PutMapping("/{evlCmitId}/evl-result/evl-ptrmsn")
	public void modifyEvlPtrmsn(@PathVariable("evlCmitId") String evlCmitId, @RequestBody List<EvlTrgetIdParam> evlTrgetList) {
		evlOnlineService.modifyEvlSlctn(evlCmitId, evlTrgetList, false);
	}


	@PutMapping("/{evlCmitId}/evl-result/evl-compt")
	public void modifyEvlCompt(@PathVariable("evlCmitId") String evlCmitId) {
	}














	//평가결과항목별상세(3가지 유형 공통)
//	@GetMapping("/{evlResultId}/evl-result-trget")
	public EvlResultBasicInfoDto getEvlResultAllotDetail(@PathVariable String evlResultId) {
		log.debug("#####	evlResultId : {}", evlResultId);
		return evlOnlineService.getEvlResultIemDetail(evlResultId);
	}


	//위원별 평가결과 항목별 등록처리(오프라인 평가- 관리자가 직접 등록)
//	@PostMapping("{evlMfcmmId}/evl-result/{evlTrgetId}")
	public EvlResultBasicInfoDto addEvlResultIemDetail(@PathVariable String evlMfcmmId, @PathVariable String evlTrgetId, @RequestBody EvlResultBasicInfoDto evlResultBasicInfoDto) {
		log.debug("#####	evlMfcmmId : {}", evlMfcmmId);
		log.debug("#####	evlTrgetId : {}", evlTrgetId);

		//return evlOnlineService.modifyEvasResnCn(evlMfcmmId, usptEvlMfcmm);
		evlResultBasicInfoDto.setEvlMfcmmId(evlMfcmmId);
		evlResultBasicInfoDto.setEvlTrgetId(evlTrgetId);

		String evlResultId = evlOnlineService.addEvlResultIemDetail(evlResultBasicInfoDto);

		return evlOnlineService.getEvlResultIemDetail(evlResultId);
	}

	//위원별 평가결과 항목별 수정처리(오프라인 평가- 관리자가 직접 등록)
//	@PutMapping("{evlMfcmmId}/evl-result/{evlResultId}")
	public EvlResultBasicInfoDto modifyEvlResultIemDetail(@PathVariable String evlMfcmmId, @PathVariable String evlResultId, @RequestBody EvlResultBasicInfoDto evlResultBasicInfoDto) {
		log.debug("#####	evlMfcmmId : {}", evlMfcmmId);
		log.debug("#####	evlResultId : {}", evlResultId);

		//return evlOnlineService.modifyEvasResnCn(evlMfcmmId, usptEvlMfcmm);
		evlResultBasicInfoDto.setEvlMfcmmId(evlMfcmmId);
		evlResultBasicInfoDto.setEvlResultId(evlResultId);

		evlOnlineService.modifyEvlResultIemDetail(evlResultBasicInfoDto);

		return evlOnlineService.getEvlResultIemDetail(evlResultId);
	}

	//평가결과 가점부여 상세조회(배점형)
//	@GetMapping("/{evlCmitId}/evl-result-add-allot/{evlTrgetId}")
	public EvlResultAddAllotBasicDto getEvlResultAddAllotDetail(@PathVariable String evlCmitId, @PathVariable String evlTrgetId) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		log.debug("#####	evlTrgetId : {}", evlTrgetId);
		EvlResultAddAllotBasicDto inputParam = new EvlResultAddAllotBasicDto();

		inputParam.setEvlCmitId(evlCmitId);
		inputParam.setEvlTrgetId(evlTrgetId);
		inputParam.setEvlDivCd(Code.evlDiv.가점);

		return evlOnlineService.getEvlResultAddAllotDetail(inputParam);
	}


	//평가결과 가점부여 저장(배점형)
//	@PutMapping("/{evlCmitId}/evl-result-add-allot/{evlTrgetId}")
	public EvlResultAddAllotBasicDto modifyEvlResultAddAllotDetail(@PathVariable String evlCmitId, @PathVariable String evlTrgetId, @RequestBody EvlResultAddAllotBasicDto paramDto) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		log.debug("#####	evlTrgetId : {}", evlTrgetId);

		paramDto.setEvlCmitId(evlCmitId);
		paramDto.setEvlTrgetId(evlTrgetId);
		paramDto.setEvlDivCd(Code.evlDiv.가점);

		evlOnlineService.modifyEvlResultAddAllotDetail(paramDto);

		return evlOnlineService.getEvlResultAddAllotDetail(paramDto);
	}


	//평가결과 감점부여 상세조회(배점형)
//	@GetMapping("/{evlCmitId}/evl-result-minus-allot/{evlTrgetId}")
	public EvlResultAddAllotBasicDto getEvlResultMinusAllotDetail(@PathVariable String evlCmitId, @PathVariable String evlTrgetId) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		log.debug("#####	evlTrgetId : {}", evlTrgetId);
		EvlResultAddAllotBasicDto inputParam = new EvlResultAddAllotBasicDto();

		inputParam.setEvlCmitId(evlCmitId);
		inputParam.setEvlTrgetId(evlTrgetId);
		inputParam.setEvlDivCd(Code.evlDiv.감점);

		return evlOnlineService.getEvlResultAddAllotDetail(inputParam);
	}


	//평가결과 감점부여 저장(배점형)
//	@PutMapping("/{evlCmitId}/evl-result-minus-allot/{evlTrgetId}")
	public EvlResultAddAllotBasicDto modifyEvlResultMinusAllotDetail(@PathVariable String evlCmitId, @PathVariable String evlTrgetId, @RequestBody EvlResultAddAllotBasicDto paramDto) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		log.debug("#####	evlTrgetId : {}", evlTrgetId);

		paramDto.setEvlCmitId(evlCmitId);
		paramDto.setEvlTrgetId(evlTrgetId);
		paramDto.setEvlDivCd(Code.evlDiv.감점);

		evlOnlineService.modifyEvlResultAddAllotDetail(paramDto);

		return evlOnlineService.getEvlResultAddAllotDetail(paramDto);
	}


	//온라인 평가진행-평가결과 상세조회(점수형/서술형- 위원회로 확인한 후 조회)
//	@GetMapping("/{evlCmitId}/evl-result")
	public OnlineEvlResutlDto getEvlOnlineEvlReusltDetail(@PathVariable String evlCmitId) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		return evlOnlineService.getEvlOnlineEvlResultDetail(evlCmitId);
	}


	//온라인 평가진행-평가결과 평가결과동의서(점수형/서술형- 위원회로 확인한 후 조회)
//	@GetMapping("/{evlCmitId}/evl-result-agre")
	public OnlineEvlResutlAgreDto getEvlOnlineEvlReusltAgreDetail(@PathVariable String evlCmitId) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		return evlOnlineService.getEvlOnlineEvlReusltAgreDetail(evlCmitId);
	}


	//온라인 평가진행-평가결과-엑셀다운로드(점수형/서술형- 위원회로 확인한 후 조회)
//	@GetMapping("/{evlCmitId}/evl-result/excel-dwld")
	public ModelAndView getEvlOnlineSttusListExcel(@PathVariable String evlCmitId){
		OnlineEvlResutlDto onlineEvlSttusDto = evlOnlineService.getEvlOnlineEvlResultDetail(evlCmitId);

		List<EvlResultByTargetListDto> list = onlineEvlSttusDto.getEvlResultByTargetListDto();//평가결과 목록

		List<HashMap<String, String>> realList = new ArrayList<HashMap<String, String>>();

		for(EvlResultByTargetListDto listinfo : list){
			HashMap<String, String> newMap = new HashMap<>();

			newMap.put("rn", listinfo.getRn()+"");
			newMap.put("slctnNm", listinfo.getSlctnNm());
			newMap.put("receiptNo", listinfo.getReceiptNo());

			realList.add(newMap);

		}

		ExcelWorkbook wb = new ExcelWorkbook();
		wb.setFilename("온라인 평가결과 목록");
		ExcelSheet<HashMap<String, String>> sheet = new ExcelSheet<>();
		sheet.addRows(realList);

		Object[] headerTitles = new Object[3];
		headerTitles[0] = "번호";
		headerTitles[1] = "평가결과";
		headerTitles[2] = "접수번호";

//		for(int i=0 ; i < headerTitles.length; i++){
//			headerTitles[i]=i;
//		}

		String[] headerVals = new String[3];

		headerVals[0] = "rn";
		headerVals[1] = "slctnNm";
		headerVals[2] = "receiptNo";

        sheet.setHeaders(headerTitles);
		sheet.setProperties(headerVals);

		sheet.setTitle("온라인 평가결과 목록");
		sheet.setSheetName("온라인 평가결과 목록");
		wb.addSheet(sheet);
		return ExcelView.getView(wb);
	}


	//평가결과 탈락처리
//	@PutMapping("{evlCmitId}/trget-drop")
	public OnlineEvlResutlDto modifyEvlOnlineTrgetDrop(@PathVariable String evlCmitId, @RequestBody OnlineEvlResutlDto onlineEvlResutlDto) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		log.debug("#####	onlineEvlResutlDto : {}", onlineEvlResutlDto.toString());

		onlineEvlResutlDto.setEvlCmitId(evlCmitId);

		evlOnlineService.modifyEvlOnlineTrgetDrop(onlineEvlResutlDto);

		return evlOnlineService.getEvlOnlineEvlResultDetail(evlCmitId);
	}


	//평가결과 선정처리
//	@PutMapping("{evlCmitId}/trget-sltcn")
	public OnlineEvlResutlDto modifyEvlOnlineTrgetSltcn(@PathVariable String evlCmitId, @RequestBody OnlineEvlResutlDto onlineEvlResutlDto) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		log.debug("#####	onlineEvlResutlDto : {}", onlineEvlResutlDto.toString());

		onlineEvlResutlDto.setEvlCmitId(evlCmitId);

		evlOnlineService.modifyEvlOnlineTrgetSltcn(onlineEvlResutlDto);

		return evlOnlineService.getEvlOnlineEvlResultDetail(evlCmitId);
	}


	//종합평가결과(서술형)
//	@GetMapping("/{evlCmitId}/evl-result-gnrlz/{evlTrgetId}")
	public OnlineEvlResutlGnrlzDto getEvlOnlineEvlReusltGnrlz(@PathVariable String evlCmitId, @PathVariable String evlTrgetId) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		return evlOnlineService.getEvlOnlineEvlReusltGnrlz(evlCmitId, evlTrgetId);
	}


	//통보처리 팝업 조회
//	@GetMapping("/{evlCmitId}/evl-result-dspth")
	public EvlResutlDspthInfoDto getEvlReusltDspthInfo(@PathVariable String evlCmitId) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		return evlOnlineService.getEvlReusltDspthInfo(evlCmitId);
	}


	//통보처리
//	@PutMapping("/{evlCmitId}/evl-result-dspth")
	public EvlResutlDspthInfoDto modifyEvlReusltDspthInfo(@PathVariable String evlCmitId, @RequestBody EvlResutlDspthInfoDto evlResutlDspthInfoDto) {
		log.debug("#####	evlCmitId : {}", evlCmitId);
		evlResutlDspthInfoDto.setEvlCmitId(evlCmitId);
		return evlOnlineService.modifyEvlReusltDspthInfo(evlResutlDspthInfoDto);
	}

}
