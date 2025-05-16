package pp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import pp.com.models.entity.Account;
import pp.com.services.BlogService;

@Controller
public class BlogDeleteController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/blog/delete")
	public String blogDelete(Long blogId) {
		//セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		//もし、account=null ログイン画面に戻る
		if(account == null) {
			return "redirect:/account/login";
		}else {
			//もし、deleteBlogの結果がtrue
			if(blogService.deleteBlog(blogId)) {
				//ブログの一覧画ページに戻る
				return "redirect:/blog/list";
			}else {
				//そうでない場合
				//編集画面に戻る
				return "redirect:/blog/edit" + blogId;
			}
		}
	}
	
	
	
	
	
	
	
}
