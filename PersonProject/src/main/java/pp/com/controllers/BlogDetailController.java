package pp.com.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pp.com.models.dao.BlogDao;
import pp.com.models.dao.CommentDao;
import pp.com.models.dao.LikeDao;
import pp.com.models.entity.Blog;
import pp.com.models.entity.Comment;

@Controller
public class BlogDetailController {

	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private LikeDao likeDao;
	
	//詳細ページを表示
	@GetMapping("/blog/detail/{blogId}")
	public String showBlogDetail(@PathVariable Long blogId,Model model) {
		Blog blog = blogDao.findByBlogId(blogId);
		if(blog != null) {
			//閲覧数
			blog.setViewCount(blog.getViewCount() + 1);
			blogDao.save(blog);
			model.addAttribute("blog", blog);
			model.addAttribute("commentList", commentDao.findByBlogId(blogId));
		}
		return "blog_detail";
	}
	
	//コメント投稿処理
	@PostMapping("/blog/comment")
	public String postComment(@RequestParam Long blogId,@RequestParam String text) {
		Comment comment = new Comment();
		comment.setBlogId(blogId);
		comment.setText(text);
		comment.setCreateAt(LocalDateTime.now());
		commentDao.save(comment);
		return "redirect:/blog/list";
		
	}
	
	//いいね処理
	@PostMapping("/blog/like/{blogId}")
	public String likeBlog(@PathVariable Long blogId) {
		// Likeテーブル用のエンティティを作成
		pp.com.models.entity.Like like = new pp.com.models.entity.Like();

		// 対象のブログIDを設定
		like.setBlogId(blogId);

		// 日付を設定（任意）
		like.setCreatedAt(LocalDateTime.now());

		// データベースに保存
		likeDao.save(like);

	    // 元の詳細ページにリダイレクトする
	    return "redirect:/blog/detail/" + blogId;
	}
	
	
	
	
	
}
