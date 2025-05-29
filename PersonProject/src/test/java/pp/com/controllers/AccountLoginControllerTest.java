package pp.com.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import pp.com.models.entity.Account;
import pp.com.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountLoginControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	// サービスクラスを使ったモックデータを準備
	@BeforeEach
	public void prepareData() {
		Account account = new Account();
		account.setAccountEmail("account@test.com");
		account.setAccountName("Vito");
		account.setPassword("123");

		// ログイン成功パターン
		when(accountService.loginCheck("account@test.com", "123")).thenReturn(account);

		// メールだけ間違い
		when(accountService.loginCheck(eq("1@test.com"), any())).thenReturn(null);
		// パスワード間違い
		when(accountService.loginCheck(eq("account@test.com"), eq("asd"))).thenReturn(null);
		// 両方間違い
		when(accountService.loginCheck(eq("test@test.com"), eq("123"))).thenReturn(null);
	}

	// ログイン画面を正しく取得できるか
	@Test
	public void testGetLoginPage() throws Exception {
		mockMvc.perform(get("/account/login")).andExpect(status().isOk()).andExpect(view().name("account_login.html"));
	}

	// ログイン成功
	@Test
	public void testLogin_Success() throws Exception {
		mockMvc.perform(
				post("/account/login/process").param("accountEmail", "account@test.com").param("password", "123"))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/blog/list"));
	}

	// 間違ったメールアドレス ログイン失敗
	@Test
	public void testLogin_WrongEmail() throws Exception {
		mockMvc.perform(post("/account/login/process").param("accountEmail", "1@test.com").param("password", "123"))
				.andExpect(status().isOk()).andExpect(view().name("account_login.html"));
	}

	// 正しいメール＋間違ったパスワード ログイン失敗
	@Test
	public void testLogin_WrongPassword() throws Exception {
		mockMvc.perform(
				post("/account/login/process").param("accountEmail", "account@test.com").param("password", "asd"))
				.andExpect(status().isOk()).andExpect(view().name("account_login.html"));
	}

	// 両方間違い
	@Test
	public void testLogin_BothWrong() throws Exception {
		mockMvc.perform(post("/account/login/process").param("accountEmail", "test@test.com").param("password", "123"))
				.andExpect(status().isOk()).andExpect(view().name("account_login.html"));
	}

	// 初期表示テスト
	@Test
	public void testInitialFormDisplay() throws Exception {
		mockMvc.perform(get("/account/login")).andExpect(status().isOk()).andExpect(view().name("account_login.html"));
		// ※ここでは入力欄が空であるかの検証はHTML DOMにアクセスできるSelenium等で行う
	}
}