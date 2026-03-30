package aicluster.member.api.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.member.api.member.service.MemberService;
import aicluster.member.common.dto.MemberStatsDto;

@RestController
@RequestMapping("/api/ust")
public class UstController {

	@Autowired
	private MemberService service;

	@GetMapping("/user-stats")
	public MemberStatsDto getList(String searchType, String memberType, String beginDay, String endDay) {
		return service.getStatsList(searchType, memberType, beginDay, endDay);
	}
}
