package aicluster.pms.api.expert.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import aicluster.pms.api.expert.dto.ExpertDto;
import aicluster.pms.api.expert.dto.ExpertListExcelDto;
import aicluster.pms.api.expert.dto.ExpertListParam;
import aicluster.pms.api.expert.service.ExpertService;
import aicluster.pms.common.entity.UsptExpert;
import aicluster.pms.common.entity.UsptExpertCl;
import bnet.library.excel.dto.ExcelSheet;
import bnet.library.excel.dto.ExcelWorkbook;
import bnet.library.util.dto.JsonList;
import bnet.library.util.pagination.CorePagination;
import bnet.library.view.ExcelView;
import lombok.extern.slf4j.Slf4j;

/**
 * 전문가 신청 front
 * @author brainednet
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/expert")
public class ExpertController {

	@Autowired
	private ExpertService expertService;

	/**
	 * 전문가신청관리 목록조회
	 * @param ExpertListParam
	 * @return
	 */
	@GetMapping("")
	public CorePagination<UsptExpert> getExpertList(ExpertListParam expertListParam, @RequestParam(defaultValue = "1") Long page) {
		log.debug("#####	getExpertReqsList : {}", expertListParam);
		return expertService.getExpertList(expertListParam, page);
	}

	/**
	 * 전문가신청관리 목록 엑셀 다운로드
	 * @param ExpertListParam
	 * @return
	 */
	@GetMapping("/excel-dwld")
	public ModelAndView getExpertListExcel(ExpertListParam expertListParam){

		log.debug("#####	getExpertListExcel : {}", expertListParam);

		List<UsptExpert> list = expertService.getExpertListExcel(expertListParam);

		ExcelWorkbook wb = new ExcelWorkbook();
		wb.setFilename("전문가신청 목록");
		ExcelSheet<UsptExpert> sheet = new ExcelSheet<>();
		sheet.addRows(list);
		sheet.setHeaders(  "번호"	, "이름",   "처리상태",    "내/외국인"	, "직장명"	, "직위"	,"휴대폰"	, "이메일"	, "생년월일");
		sheet.setProperties("rn"	, "expertNm"	,"expertReqstProcessSttusNm"	, "nativeNm"	, "wrcNm"	, "ofcpsNm"	, "mbtlnum"	, "email"	, "brthdy");
		sheet.setTitle("전문가신청 목록");
		sheet.setSheetName("전문가신청 목록");
		wb.addSheet(sheet);
		return ExcelView.getView(wb);
	}


	/**
	 * 전문가 신청 상세정보
	 * @param expertId
	 * @return
	 */
	@GetMapping("/{expertId}")
	public ExpertDto getExpertReqsInfo(@PathVariable("expertId") String expertId) {
		log.debug("#####	getExpertReqsInfo : {}", expertId);
		return expertService.getExpertReqsInfo(expertId);
	}

	/**
	 * 전문가 변경
	 * @param expertId
	 * @param expertDto
	 * @param fileList
	 */
	@PutMapping("/{expertId}")
	public ExpertDto modifyExpert( @PathVariable("expertId") String expertId
			              , @RequestPart(value = "info", required = false) ExpertDto expertDto
			              , @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {
		return expertService.modifyExpert(expertId, expertDto, fileList);
	}

	/**
	 * 전문가 삭제
	 * @param expertId
	 * @param expertListParam
	 * @return
	 */
	@DeleteMapping("")
	public void removeExpert(ExpertListParam expertListParam){
		  expertService.deleteExpert(expertListParam);
	}

	/**
	 * 전문가 매칭이력
	 * @param expertId
	 * @return
	 */
	@GetMapping("/hist")
	public CorePagination<UsptExpert> getExpertMatchHistList(ExpertListParam expertListParam, @RequestParam(defaultValue = "1") Long page) {
		log.debug("#####	expertListParam : {}", expertListParam);
		return expertService.getExpertMatchHistList(expertListParam, page);
	}

	/**
	 * 전문가 매칭이력엑셀 다운로드
	 * @param ExpertListParam
	 * @return
	 */
	@GetMapping("/excel-dwld/hist")
	public ModelAndView getExpertMatchHistListExcel(ExpertListParam expertListParam){

		log.debug("#####	getExpertMatchHistListExcel : {}", expertListParam);

		List<UsptExpert> list = expertService.getExpertMatchHistListExcel(expertListParam);

		ExcelWorkbook wb = new ExcelWorkbook();
		wb.setFilename("전문가 매칭이력");
		ExcelSheet<UsptExpert> sheet = new ExcelSheet<>();
		sheet.addRows(list);
		sheet.setHeaders(  "번호"	, "매칭일",   "사업년도",    "사업명",		 "공고번호", 		"공고명",			"활동분양"	);
		sheet.setProperties("rn"	, "regDt",    "bsnsYear", 	"bsnsNm"	,	 "pblancNo"	,	 "pblancNm"	, 	"actRealm" );
		sheet.setTitle("전문가 매칭이력");
		sheet.setSheetName("전문가 매칭이력");
		wb.addSheet(sheet);
		return ExcelView.getView(wb);
	}

	/**
	 * 전문가 excel 등록
	 * @param expertListExcelDto
	 * @return expertListExcelDto
	 */
	@PutMapping("/excel-upld")
	public JsonList<ExpertListExcelDto> modifyExpertExcel( @RequestBody(required = false) List<ExpertListExcelDto> expertListExcelDto) {
		log.debug("#####	expertListExcelDto : {}", expertListExcelDto);

		return new JsonList<>(expertService.modifyExpertExcel(expertListExcelDto));
	}


	/**
	 * 전문가 등록 템플릿 엑셀 다운로드
	 * @param
	 * @return
	 */
	@GetMapping("/excel-templ")
	public ModelAndView getExpertListExcelTemp(){

		List<UsptExpert> list = new ArrayList<>();
		ExcelWorkbook wb = new ExcelWorkbook();
		wb.setFilename("전문가 등록");
		ExcelSheet<UsptExpert> sheet = new ExcelSheet<>();
		sheet.addRows(list);
		sheet.setHeaders(  "상태",          "전문가분류명",   "이름",        "생년월일",	 "성별", 		  "내/외국인",    "휴대폰번호",	"이메일"	,	"직장명",  "직위");
		sheet.setProperties("successYn"	, "expertClNm",    "expertNm", 	"encBrthdy",	 "genderCd"	,  "nativeYn"	, 	"encMbtlnum" , "encEmail" , "wrcNm",  "ofcpsNm");
		sheet.setTitle("전문가 등록");
		sheet.setSheetName("전문가 등록");
		wb.addSheet(sheet);

		//전문가 분류 코드 조회
		List<UsptExpertCl> list2 = expertService.selectExpertClfcTreeList();
		ExcelSheet<UsptExpertCl> sheet2 = new ExcelSheet<>();
		sheet2.addRows(list2);
		sheet2.setHeaders( "그룹코드" ,  "코드",          "코드명");
		sheet2.setProperties("parntsExpertClId"	,"expertClId"	, "expertClNm");
		sheet2.setTitle("전문가 분류 코드");
//		sheet2.setSheetName("전문가 등록");
		wb.addSheet(sheet2);

		return ExcelView.getView(wb);
	}

}