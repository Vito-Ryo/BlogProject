package pp.com.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import pp.com.models.dao.BlogDao;
import pp.com.models.dao.CommentDao;
import pp.com.models.dao.LikeDao;
import pp.com.models.dto.BlogSummaryDTO;
import pp.com.models.entity.Account;
import pp.com.models.entity.Blog;
import pp.com.models.entity.Comment;
import pp.com.services.BlogService;

@Controller
public class BloglistController {

	@Autowired
	private HttpSession session;

	@Autowired
	BlogService blogService;

	@Autowired
	private LikeDao likeDao;

	@Autowired
	private CommentDao commentDao;

	// 検索機能（キーワード検索＋コメント＋いいね対応）
	@GetMapping("/search")
	public String searchBlog(@RequestParam("keyword") String keyword, Model model) {
		// セッションからログインユーザーを取得
		Account account = (Account) session.getAttribute("loginAccountInfo");

		// 未ログインの場合、ログイン画面へリダイレクト
		if (account == null) {
			return "redirect:/account/login";
		}

		// 管理者判定（表示切り替えのため）
		boolean isAdmin = "ADMIN".equals(account.getRole());

		// キーワード検索結果を取得
		List<Blog> searchResults = blogService.searchBlogKeyword(account.getAccountId(), keyword);

		// DTO に変換（コメント・いいね情報を付加）
		List<BlogSummaryDTO> summaryList = new ArrayList<>();
		for (Blog blog : searchResults) {
			BlogSummaryDTO dto = new BlogSummaryDTO();
			dto.setBlog(blog);

			// 最新コメントを取得
			List<Comment> comments = commentDao.findLatestCommentByBlogId(blog.getBlogId());
			Comment latestComment = comments.isEmpty() ? null : comments.get(0);
			dto.setLatestComment(latestComment != null ? latestComment.getText() : null);

			// いいね数を取得
			int likeCount = likeDao.countByBlogId(blog.getBlogId());
			dto.setLikeCount(likeCount);

			summaryList.add(dto);
		}

		// テンプレートに渡す
		model.addAttribute("accountName", account.getAccountName());
		model.addAttribute("blogSummaries", summaryList); // ✅ これでHTMLに一致
		model.addAttribute("isAdmin", isAdmin); // ✅ 管理者表示制御に必要
		model.addAttribute("keyword", keyword); // ✅ 入力キーワードを保持

		return "blog_list";
	}

	// 閲覧数取得
	@RestController
	public class BlogViewApiController {

		@Autowired
		private BlogDao blogDao;

		// 指定されるblogの閲覧数を返す
		@GetMapping("/api/blog/view-count/{blogId}")
		public int geetViewCount(@PathVariable Long blogId) {
			Blog blog = blogDao.findByBlogId(blogId);
			// blogがnullでなければ閲覧数を返し、なければ0
			return blog == null ? 0 : blog.getViewCount();
		}
	}

	// ブログ編集ページを表示する処理
	@GetMapping("/blog/edit")
	public String showBlogEditPage(Model model) {
		model.addAttribute("blog", new Blog());
		// resources/templates/blog_edit.html に遷移する
		return "blog_edit";
	}

	// ブログ一覧画面の表示（ログイン確認、コメント・いいね付き）
	@GetMapping("/blog/list")
	public String showBlogList(Model model) {
		// セッションからログインユーザーを取得
		Account account = (Account) session.getAttribute("loginAccountInfo");

		// 未ログインの場合、ログイン画面へリダイレクト
		if (account == null) {
			return "redirect:/account/login";
		}

		// 管理者かどうかを判定する（"ADMIN" の場合 true）
		boolean isAdmin = "ADMIN".equals(account.getRole());

		// ① ログインユーザーに紐づく全ブログを取得
		List<Blog> blogList = blogService.selectAllBlogList(account.getAccountId());

		// ② DTOに変換（コメントといいね情報を付加）
		List<BlogSummaryDTO> summaryList = new ArrayList<>();
		for (Blog blog : blogList) {
			BlogSummaryDTO dto = new BlogSummaryDTO();
			dto.setBlog(blog);

			// 最新コメントを取得
			List<Comment> comments = commentDao.findLatestCommentByBlogId(blog.getBlogId());
			Comment latestComment = comments.isEmpty() ? null : comments.get(0);
			dto.setLatestComment(latestComment != null ? latestComment.getText() : null);

			// いいね数を取得
			int likeCount = likeDao.countByBlogId(blog.getBlogId());
			dto.setLikeCount(likeCount);

			summaryList.add(dto);
		}

		// ③ モデルに渡す（テンプレートで使う）
		model.addAttribute("accountName", account.getAccountName());
		model.addAttribute("blogSummaries", summaryList);
		model.addAttribute("isAdmin", isAdmin);

		return "blog_list";

	}
}
