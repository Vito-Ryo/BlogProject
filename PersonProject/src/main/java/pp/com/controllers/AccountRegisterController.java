package pp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pp.com.services.AccountService;

@Controller
public class AccountRegisterController {
	
	@Autowired
	private AccountService accountService;
	
	//登録画面の表示
	@GetMapping("/account/register")
	public String getAdminRegisterPage() {
		return "account_register.html";
	}
	
	//登録処理
	@PostMapping("/account/register/process")
	public String accountRegisterProcess(@RequestParam String accountName,
			@RequestParam String accountEmail,
			@RequestParam String password,
			@RequestParam String confirmPassword) {
		//パスワードが一致するか確認
		//一致しないと、account_register.htmlに戻る
		if(!password.equals(confirmPassword)) {
			return "account_register.html";
		} 

		//もし、createAccountがtrue(存在する)account_login.html遷移
		//そうでない場合、account_register.htmlに戻る
		if(accountService.createAccount(accountEmail, accountName, password)) {
			return "account_login.html";
		}else {
			return "account_register.html";
		}

		
	}
}
