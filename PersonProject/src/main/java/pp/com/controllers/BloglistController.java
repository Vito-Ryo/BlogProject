package pp.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pp.com.models.entity.Account;
import pp.com.models.entity.Blog;
import pp.com.services.BlogService;

@Controller
public class BloglistController {

	@Autowired
	private HttpSession session;

	@Autowired
	BlogService blogService;

	// ブログ一覧画面の表示
	@GetMapping("/blog/list")
	public String getBlogList(Model model) {
		// セッションからログインしている
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もし、account==null ログイン画面に戻る
		// そうでない場合、ログインしている人の名前の情報を画面に渡してブログ一覧画面のhtmlを表示
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// ブログ情報を取得
			List<Blog> blogList = blogService.selectAllBlogList(account.getAccountId());
			model.addAttribute("accountName", account.getAccountName());
			model.addAttribute("blogList", blogList);
			return "blog_list.html";
		}
	}

	// 検索機能
	@GetMapping("/search")
	public String searchBlog(@RequestParam("keyword") String keyword, Model model) {
		//// セッションからログインしている
		Account account = (Account) session.getAttribute("loginAccountInfo");

		// もし、account==null ログイン画面に戻る
		if (account == null) {
			return "redirect:/account/login";
		}
		
		//キーワードで検索する
		List<Blog> searchResults = blogService.searchBlogKeyword(account.getAccountId(), keyword);
				
		
		//検索結果とユーザー名をビューに渡す
		model.addAttribute("accountName", account.getAccountName());
		model.addAttribute("blogList", searchResults);
		//入力キーワードを保持して表示する
		model.addAttribute("keyword", keyword);
				
		//検索結果を表示する
		return "blog_list.html";
				
				
				
	}

}
