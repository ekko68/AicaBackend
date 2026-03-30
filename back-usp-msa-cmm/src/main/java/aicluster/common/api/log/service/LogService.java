package aicluster.common.api.log.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.common.common.dao.LogtDayMemberLogDao;
import aicluster.common.common.dao.LogtElapsedTimeLogDao;
import aicluster.common.common.dao.LogtIndvdlinfoLogDao;
import aicluster.common.common.dto.DayMemberSummary;
import aicluster.common.common.dto.ElapsedTimeLogSummary;
import aicluster.common.common.entity.LogtIndvdlinfoLog;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;

@Service
public class LogService {

	@Autowired
	private LogtIndvdlinfoLogDao logtIndvdlinfoLogDao;
	@Autowired
	private LogtElapsedTimeLogDao logtElapsedTimeLogDao;
	@Autowired
	private LogtDayMemberLogDao logtDayMemberLogDao;

	public CorePagination<LogtIndvdlinfoLog> getIndvdlinfoList(String beginDay, String endDay, String workerLoginId, Long page,
			Long itemsPerPage) {
		if (page == null) {
			page = 1L;
		}
		if (itemsPerPage == null) {
			itemsPerPage = 20L;
		}
		long totalItems = logtIndvdlinfoLogDao.selectList_count(beginDay, endDay, workerLoginId);
		CorePaginationInfo info = new CorePaginationInfo(page, itemsPerPage, totalItems);
		List<LogtIndvdlinfoLog> list = logtIndvdlinfoLogDao.selectList(beginDay, endDay, workerLoginId, info.getBeginRowNum(), info.getItemsPerPage(), info.getTotalItems());
		CorePagination<LogtIndvdlinfoLog> dto = new CorePagination<>(info, list);
		return dto;
	}

	public ElapsedTimeLogSummary getElapsedTimeSummary() {
		return logtElapsedTimeLogDao.selectElapsedTimeLogSummary();
	}

	public DayMemberSummary getDayMemberSummary() {
		return logtDayMemberLogDao.selectDayMemberSummary();
	}

}
