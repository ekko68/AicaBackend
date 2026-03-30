package aicluster.member.excel.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;

import aicluster.member.support.TestControllerSupport;


public class ExcelControllerTest extends TestControllerSupport {

	@Test
	public void test() throws Exception {
		this.mvc.perform(getMethodBuilder("/member/api/excels/download-form")).andDo(print());
	}
}
