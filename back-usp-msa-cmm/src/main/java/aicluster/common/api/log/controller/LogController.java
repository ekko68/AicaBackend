package aicluster.common.api.log.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.common.api.log.service.LogService;
import aicluster.common.common.dto.DayMemberSummary;
import aicluster.common.common.dto.ElapsedTimeLogSummary;
import aicluster.common.common.entity.LogtIndvdlinfoLog;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils.date;
import bnet.library.util.pagination.CorePagination;

@RestController
@RequestMapping("/api/log/")
public class LogController {

	@Autowired
	private LogService service;

	@GetMapping("/indvdlinfos")
	public CorePagination<LogtIndvdlinfoLog> getIndvdlinfoList(Date beginDt, Date endDt, String workerLoginId, Long page, Long itemsPerPage) {
		if (beginDt == null || endDt == null) {
			throw new InvalidationException("기간을 입력하세요.");
		}

		String beginDay = date.format(beginDt, "yyyyMMdd");
		String endDay = date.format(endDt, "yyyyMMdd");
		return service.getIndvdlinfoList(beginDay, endDay, workerLoginId, page, itemsPerPage);
	}

	@GetMapping("/elapsedtimes")
	public ElapsedTimeLogSummary getElapsedTimeSummary() {
		return service.getElapsedTimeSummary();
	}

	@GetMapping("/daymems")
	public DayMemberSummary getDayMembers() {
		return service.getDayMemberSummary();
	}
}
