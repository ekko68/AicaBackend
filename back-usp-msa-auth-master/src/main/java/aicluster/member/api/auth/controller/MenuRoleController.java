package aicluster.member.api.auth.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.member.api.auth.service.MenuRoleService;
import aicluster.member.common.dto.MenuEnabledDto;
import bnet.library.util.dto.JsonList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/auth")
public class MenuRoleController {
	@Autowired
	MenuRoleService menuRoleService;

	@GetMapping("/menu-roles/{systemId}/{roleId}")
	public JsonList<MenuEnabledDto> getList(@PathVariable String systemId, @PathVariable String roleId) {
		log.debug("메뉴-역할 매핑 목록(getList) Parameter : ["+systemId+"]["+roleId+"]");
		return menuRoleService.getMenuEnabledList(systemId, roleId);
	}

	@PutMapping("/menu-roles/{systemId}/{roleId}")
	public JsonList<MenuEnabledDto> modify(@PathVariable String systemId, @PathVariable String roleId, String[] menuId) {
		log.debug("메뉴-역할 매핑 수정(modify) Parameter : ["+systemId+"]["+roleId+"]["+menuId+"]");
		return menuRoleService.updateMenuEnabled(systemId, roleId, Arrays.asList(menuId));
	}
}
