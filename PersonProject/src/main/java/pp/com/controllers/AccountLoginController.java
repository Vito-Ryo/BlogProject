package pp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pp.com.models.entity.Account;
import pp.com.services.AccountService;

@Controller
public class AccountLoginController {

	@Autowired
	private AccountService accountService;

	// Session使用する宣言
	@Autowired
	private HttpSession session;

	// Login画面の表示
	@GetMapping("/account/login")
	public String getAccountLoginPage() {
		return "account_login.html";
	}

	// ログイン処理
	@PostMapping("/account/login/process")
	public String accountLoginProcess(@RequestParam String accountEmail, @RequestParam String password) {
		// loginCheckメソッドを呼び出しその結果をaccountという変数に格納
		Account account = accountService.loginCheck(accountEmail, password);
		// もし、account==nullログイン画面に戻る
		// そうでない場合、sessionにログイン情報に保存
		// ブログ一覧画面にリダイレクトする/blog/list
		if (account == null) {
			return "account_login.html";
		} else {
			session.setAttribute("loginAccountInfo", account);
			return "redirect:/blog/list";
		}
	}

}
