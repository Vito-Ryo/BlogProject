package pp.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pp.com.models.dao.BlogDao;
import pp.com.models.entity.Blog;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

	// ブログ一覧のチェック
	// もし、accountId==null 戻り値としてnull
	// finAll内容をコントローラークラスに渡す
	public List<Blog> selectAllBlogList(Long accountId) {
		if (accountId == null) {
			return null;
		} else {
			return blogDao.findAll();
		}
	}

	// 商品の登録処理チェック
	// もし、findByBlogTitleが==nullだったら
	// 保存処理true
	// そうでない場合、false
	public boolean createBlog(String titleName, String article, String blogImage, Long accountId) {
		if (blogDao.findByBlogTitle(titleName) == null) {
			blogDao.save(new Blog(titleName, article, blogImage, accountId));
			return true;
		} else {
			return false;
		}
	}

	// 編集画面を表示するをチェックする
	// もし、blogId == null null
	// そうでない場合、findByBlogIdの情報をコントローラークラスに渡す
	public Blog blogEditCheck(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}
	}

	// 更新処理のチェック
	// もし、blogId ==null だったら、更新処理しない
	// flase
	// そうでない場合、更新処理する
	// コントローラークラスからもらった、blogIdを使って、編集する前にデータ取得
	// 変更するべきところだけ、セッターを使って、データを更新する
	// true
	public boolean blogUpdate(Long blogId, String titleName, String article, String blogImage, Long accountId) {
		if (blogId == null) {
			return false;
		} else {
			Blog blog = blogDao.findByBlogId(blogId);
			blog.setBlogTitle(titleName);
			blog.setArticle(article);
			blog.setBlogImage(blogImage);
			blog.setAccountId(accountId);
			blogDao.save(blog);
			return true;
		}
	}

	// 削除処理のチェック
	// もし、コントローラーからもらったblogIdがnullであれば
	// 削除できない false
	// そうでない場合、
	// deleteByBlogIdを使用してブログ削除
	// true
	public boolean deleteBlog(Long blogId) {
		if (blogId == null) {
			return false;
		} else {
			blogDao.deleteByBlogId(blogId);
			return true;
		}
	}

	// 指定されたアカウントのブログの中から、タイトルまた内容にキーワードを含むものを検索
	public List<Blog> searchBlogKeyword(Long accountId, String keyword) {
		if (accountId == null || keyword == null || keyword.trim().isEmpty()) {
			// アカウントまたキーワードがない場合、空のリストを返す
			return List.of();
		}

		// タイトルまた内容にキーワードを含むブログを検索
		return blogDao.findByAccountIdAndBlogTitleContainingOrAccountIdAndArticleContaining(accountId, keyword,
				accountId, keyword);
	}

}
