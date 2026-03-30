package aicluster.common.api.holiday.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.common.api.holiday.dto.MonthHolidaysDto;
import aicluster.common.api.holiday.dto.YearHolidaysDto;
import aicluster.common.common.dao.CmmtHldyDao;
import aicluster.common.common.dao.CmmtHldyExclDao;
import aicluster.common.common.dto.HldySmmryDto;
import aicluster.common.common.entity.CmmtHldy;
import aicluster.common.common.entity.CmmtHldyExcl;
import aicluster.common.common.util.CodeExt.validateMessageExt;
import aicluster.framework.security.SecurityUtils;
import bnet.library.exception.InvalidationException;
import bnet.library.exception.InvalidationsException;
import bnet.library.util.CoreUtils.date;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.dto.JsonList;

@Service
public class HolidayService {

	@Autowired
	private CmmtHldyDao cmmtHldyDao;

	@Autowired
	private CmmtHldyExclDao cmmtHldyExclDao;

	public YearHolidaysDto getlist(String year, String ymdNm) {

		// 로그인 여부 검사, 내부사용자 여부 검사
		SecurityUtils.checkWorkerIsInsider();

		// 입력값 검증
		if (string.isBlank(year)) {
			throw new InvalidationException(String.format(validateMessageExt.입력없음, "연도"));
		}

		// 휴일 요약 목록 정보 조회
		List<HldySmmryDto> smmryList = cmmtHldyDao.selectSummary(year);

		// 휴일 목록 정보 조회
		List<CmmtHldy> hldyList = cmmtHldyDao.selectList(year, ymdNm);

		// 휴일 제외 목록 정보 조회
		List<CmmtHldyExcl> exclList = cmmtHldyExclDao.selectlist(year, ymdNm);

		// 목록 조회 정보 출력
		YearHolidaysDto yearHolidaysDto = YearHolidaysDto.builder()
				.summary(smmryList)
				.holidays(hldyList)
				.exclHolidays(exclList)
				.build();

		return yearHolidaysDto;
	}

	public CmmtHldy addHoliday(CmmtHldy holiday) {

		if (holiday == null) {
			throw new InvalidationException("입력이 없습니다.");
		}

		InvalidationsException errs = new InvalidationsException();

		// 로그인 여부 검사, 내부사용자 인지 검사
		SecurityUtils.checkWorkerIsInsider();

		// 입력값 검증
		if (string.isBlank(holiday.getYmd())) {
			errs.add("ymd", String.format(validateMessageExt.입력없음, "날짜"));
		} else {
			Date day = string.toDate(holiday.getYmd());
			if (day == null) {
				errs.add("ymd", String.format(validateMessageExt.유효오류, "날짜"));
			}
			else {
				holiday.setYmd(date.format(day, "yyyyMMdd"));
			}
		}

		if (string.isBlank(holiday.getYmdNm())) {
			errs.add("ymdNm", String.format(validateMessageExt.입력없음, "날짜명"));
		}

		if (errs.size() > 0) {
			throw errs;
		}

		CmmtHldy cmmtHldy = cmmtHldyDao.select(holiday.getYmd());
		if (cmmtHldy != null) {
			throw new InvalidationException("휴일 정보가 이미 등록되어 있습니다.");
		}

		CmmtHldyExcl cmmtHldyExcl = cmmtHldyExclDao.select(holiday.getYmd());
		if (cmmtHldyExcl != null) {
			throw new InvalidationException("휴일 제외 정보에 등록되어 있는 날짜입니다.");
		}

		// 입력값 세팅
		cmmtHldy = CmmtHldy.builder()
				.ymd(holiday.getYmd())
				.ymdNm(holiday.getYmdNm())
				.userDsgn(true)
				.build();

		// 입력값 저장
		cmmtHldyDao.insert(cmmtHldy);

		cmmtHldy = cmmtHldyDao.select(cmmtHldy.getYmd());

		return cmmtHldy;
	}

	public CmmtHldy getHoliday(String ymd) {

		// 로그인 여부 검사, 내부사용자 여부 검사
		SecurityUtils.checkWorkerIsInsider();

		// 입력값 검증
		if (string.isBlank(ymd)) {
			throw new InvalidationException(String.format(validateMessageExt.입력없음, "날짜"));
		}

		CmmtHldy cmmtHldy = cmmtHldyDao.select(ymd);
		if (cmmtHldy == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "휴일 정보"));
		}

		return cmmtHldy;
	}

	public CmmtHldy modifyHoliday(CmmtHldy holiday) {

		InvalidationsException errs = new InvalidationsException();

		// 로그인 여부 검사, 내부사용자 여부 검사
		SecurityUtils.checkWorkerIsInsider();

		// 입력값 검증
		if (string.isBlank(holiday.getYmd())) {
			errs.add("ymd", String.format(validateMessageExt.입력없음, "날짜"));
		} else {
			Date day = string.toDate(holiday.getYmd());
			if (day == null) {
				errs.add("ymd", String.format(validateMessageExt.유효오류, "날짜"));
			}
			else {
				holiday.setYmd(date.format(day, "yyyyMMdd"));
			}
		}

		if (string.isBlank(holiday.getYmdNm())) {
			errs.add("ymdNm", String.format(validateMessageExt.입력없음, "날짜명"));
		}

		if (errs.size() > 0) {
			throw errs;
		}

		CmmtHldy cmmtHldy = cmmtHldyDao.select(holiday.getYmd());
		if (cmmtHldy == null) {
			throw new InvalidationException("휴일 정보가 없습니다.");
		}

		// 입력값 세팅
		cmmtHldy.setUserDsgn(true);
		cmmtHldy.setYmdNm(holiday.getYmdNm());

		// 입력값 업데이트
		cmmtHldyDao.update(cmmtHldy);

		cmmtHldy = cmmtHldyDao.select(cmmtHldy.getYmd());

		return cmmtHldy;
	}

	public void removeHoliday(String ymd) {

		// 로그인 여부 검사, 내부사용자 여부 검사
		SecurityUtils.checkWorkerIsInsider();

		// 입력값 검증
		if (string.isBlank(ymd)) {
			throw new InvalidationException(String.format(validateMessageExt.입력없음, "날짜"));
		}

		CmmtHldy cmmtHldy = cmmtHldyDao.select(ymd);
		if (cmmtHldy == null) {
			throw new InvalidationException("휴일 정보가 없습니다.");
		}

		cmmtHldyDao.delete(ymd);

	}

	public JsonList<MonthHolidaysDto> getMonthHolidaysList(String ym) {

		// 입력값 검증
		if (string.isBlank(ym)) {
			throw new InvalidationException(String.format(validateMessageExt.입력없음, "연월"));
		}
		else {
			// ym이 "yyyy-MM"형식으로 입력될 가능성이 있으므로 조정해 주어야 함
			Date dt = string.toDate(ym);
			if (dt == null) {
				throw new InvalidationException(String.format(validateMessageExt.입력오류, "연월"));
			}
			ym = date.format(dt, "yyyyMM");
		}

		List<MonthHolidaysDto> list = cmmtHldyDao.selectMonthList(ym);

		return new JsonList<>(list);
	}

}
