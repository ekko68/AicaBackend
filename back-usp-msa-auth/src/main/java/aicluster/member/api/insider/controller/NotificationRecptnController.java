package aicluster.member.api.insider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.member.api.insider.dto.NotificaionRecptnParam;
import aicluster.member.api.insider.service.NotificationRecptnService;
import aicluster.member.common.entity.CmmtSystemPic;
import bnet.library.util.dto.JsonList;

@RestController
@RequestMapping("api/insiders/notification-reception")
public class NotificationRecptnController {
	@Autowired
	private NotificationRecptnService service;

	@GetMapping("")
	JsonList<CmmtSystemPic> getList() {
		return service.getList();
	}

	@PostMapping("")
	JsonList<CmmtSystemPic> saveAll(@RequestBody NotificaionRecptnParam param) {
		return service.insertList(param);
	}
}
