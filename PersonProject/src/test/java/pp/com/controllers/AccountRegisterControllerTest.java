package pp.com.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import pp.com.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountRegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	// サービスクラスを使ったデータ作成
	@BeforeEach
	public void prepareData() {
		// 登録できる場合 "vito@test.com", "Vito","Alice1234" true
		when(accountService.createAccount("vito@test.com", "Vito", "asd")).thenReturn(true);

		// ログインが失敗： accountEmail "test@test.com"と等しい、accountNameと パスワードはどんな値でもいい false
		when(accountService.createAccount(eq("test@test.com"), any(), any())).thenReturn(false);
	}

	//登録画面を正しく取得できるか
	@Test
	public void testGetRegisterPage() throws Exception {
		mockMvc.perform(get("/account/register")).andExpect(status().isOk())
				.andExpect(view().name("account_register.html"));
	}

	//ユーザー登録成功
	@Test
	public void testRegister_Success() throws Exception {
		mockMvc.perform(post("/account/register/process").param("accountName", "Vito")
				.param("accountEmail", "vito@test.com").param("password", "asd").param("confirmPassword", "asd"))
				.andExpect(status().isOk()).andExpect(view().name("account_login.html"));

		verify(accountService, times(1)).createAccount("vito@test.com", "Vito", "asd");
	}

	//登録失敗
	@Test
	public void testRegister_EmailAlreadyExists() throws Exception {
		mockMvc.perform(
				post("/account/register/process").param("accountName", "account").param("accountEmail", "test@test.com")
						.param("password", "1234abcd").param("confirmPassword", "1234abcd"))
				.andExpect(status().isOk()).andExpect(view().name("account_register.html"));

		verify(accountService, times(1)).createAccount("test@test.com", "account", "1234abcd");
	}

	//二回パスワード一致テスト
	@Test
	public void testRegister_ConfirmPasswordMatch() throws Exception {
		mockMvc.perform(
				post("/account/register/process").param("accountName", "account").param("accountEmail", "test@test.com")
						.param("password", "1234abcd").param("confirmPassword", "1234abcd"))
				.andExpect(status().isOk());

		verify(accountService, times(1)).createAccount("test@test.com", "account", "1234abcd");
	}

	//二回パスワード不一致テスト
	@Test
	public void testRegister_ConfirmPasswordMismatch() throws Exception {
		mockMvc.perform(
				post("/account/register/process").param("accountName", "account").param("accountEmail", "test@test.com")
						.param("password", "1234abcd").param("confirmPassword", "abcd1234"))
				.andExpect(status().isOk()).andExpect(view().name("account_register.html"));

		verify(accountService, times(0)).createAccount(any(), any(), any());
	}

	//初期表示チェック（※フォームの空欄確認はHTML DOM操作が必要）
	@Test
	public void testRegisterFormInitialDisplay() throws Exception {
		mockMvc.perform(get("/account/register")).andExpect(status().isOk())
				.andExpect(view().name("account_register.html"));
	}
}