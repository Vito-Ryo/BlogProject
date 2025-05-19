package pp.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import pp.com.models.entity.Account;
import pp.com.models.entity.Blog;
import pp.com.services.BlogService;

@Controller
public class BlogEditController {
	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;

	// 編集画面表示
	@GetMapping("/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		// セッションからログインしている人のの情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もし、account == null,ログイン画面に戻る
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// 編集画面に表示させる情報を変数に格納 blog
			Blog blog = blogService.blogEditCheck(blogId);
			// もし、blog==null、ブログ一覧画面に戻る
			// そうでない場合、編集画面に編集する内容を渡す
			// 編集画面を表示
			if (blog == null) {
				return "redirect:/blog/list";
			} else {
				model.addAttribute("account", account.getAccountName());
				model.addAttribute("blog", blog);
				return "blog_edit.html";
			}
		}
	}

	// 更新処理
	@PostMapping("/blog/edit/process")
	public String blogUpdate(@RequestParam String titleName, @RequestParam String article,
			@RequestParam MultipartFile blogImage, @RequestParam Long blogId, @RequestParam String existingImagePath) {
		// セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もし、account == null、ログイン画面に戻る
		// そうでない場合、
		//画像が更新しない場合、画面をそのまま使う
		//新しい画像をアップロードした場合
		/**
		 * 現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
		 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している
		 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入
		 **/
		// ファイルの保存
		// もし、blogUpdateの結果がtrueの場合は、ブログ一覧画面に戻る
		// そうでない場合、ブログ編集画面を戻る
		if (account == null) {
			return "redirect:/account/login";
		}

		String fileName = existingImagePath;

		if (blogImage != null && !blogImage.isEmpty()) {
			fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			
			// 外部フォルダ：プロジェクの uploads/blog-img/
	        String uploadDir = System.getProperty("user.dir") + "/uploads/blog-img/";

			try {
				Files.copy(blogImage.getInputStream(), Path.of(uploadDir + fileName), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (blogService.blogUpdate(blogId, titleName, article, fileName, account.getAccountId())) {
			return "redirect:/blog/list";
		} else {
			return "redirect:/blog/edit" + blogId;
		}
	}
}
