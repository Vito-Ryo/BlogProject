package pp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import pp.com.models.entity.Account;
import pp.com.services.BlogService;

@Controller
public class BlogRegisterController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private HttpSession session;
	
	//ブログ画面の表示
	@GetMapping("/blog/register")
	public String getBlogRegisterPage(Model model) {
		//セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		//もし、account==null ログイン画面にリダイレクトする
		//そうでない場合、ログインしている人の名前を画面に渡す
		if(account == null) {
			return "redirect:/account/login";
		}else {
			model.addAttribute("accountName", account.getAccountName());
			return "blog_register.html";
		}
	}
	
	
	
	
	
	
	
}
