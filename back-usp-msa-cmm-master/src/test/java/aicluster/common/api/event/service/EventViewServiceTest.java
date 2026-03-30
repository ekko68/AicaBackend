package aicluster.common.api.event.service;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import aicluster.common.api.event.dto.EventViewGetListParam;
import aicluster.common.common.dto.EventPrevNextItem;
import aicluster.common.common.entity.CmmtEvent;
import aicluster.common.support.TestServiceSupport;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.dto.JsonList;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventViewServiceTest extends TestServiceSupport {

	@Autowired
	private EventViewService service;

	@Test
	void test() {
		// 오늘의 게시물 조회
		JsonList<CmmtEvent> todayList = service.getTodayList(4L);
		assertNotNull(todayList);
		log.info(String.format("### 오늘의 게시물 조회 결과 ###\n[%s]", todayList.toString()));

		// 게시된 이벤트 목록 조회
		EventViewGetListParam param = new EventViewGetListParam();
		param.setBeginDay(string.toDate("20220501"));
		param.setEndDay(string.toDate("20220630"));

		CorePaginationParam pageParam = new CorePaginationParam();
		pageParam.setPage(1L);
		pageParam.setItemsPerPage(3L);

		CorePagination<CmmtEvent> postingList = service.getList(param, pageParam);

		assertNotNull(postingList);
		log.info(String.format("### 게시 이벤트 목록 조회 결과 ###\n[%s]", postingList.toString()));

		// 게시된 이벤트의 이전글/다음글 조회
		String eventId = "event-3c772636442746b7a10f0685f8de5234";
		EventPrevNextItem prevNextItem = service.getPrevNext(eventId, param);
		assertNotNull(prevNextItem);

		log.info(String.format("### 이전글/다음글 조회 결과 ###\n[%s]", prevNextItem.toString()));
	}
}
