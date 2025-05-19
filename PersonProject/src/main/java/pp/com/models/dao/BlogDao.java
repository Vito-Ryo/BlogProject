package pp.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import pp.com.models.entity.Blog;

@Repository
@Transactional
public interface BlogDao extends JpaRepository<Blog, Long> {
	// 保存処理と更新処理 insertとupdate
	// 用途：ブログ一覧を表示させるときに使用
	Blog save(Blog blog);

	// SElECT * FROM blog WHERE blog_title = ?
	// 用途：ブログの登録チェックに使用(同じブログが登録されないようにするチェックに使用）
	Blog findByBlogTitle(String blogTitle);

	// SELECT * FROM blog WHERE blog_id = ?
	// 用途：編集画面を表示する際に使用
	Blog findByBlogId(Long blogId);

	// DELETE FROM blog WHERE blog_id = ?
	// 用途：削除使用する
	void deleteByBlogId(Long blogId);

	// SELECT * FROM blog
	// WHERE (account_id = ? AND blog_title LIKE %?%)
	// OR (account_id = ? AND article LIKE %?%)
	// ログインユーザーのアカウントに一致し、タイトルまた内容にキーワードを含むブログを検索
	List<Blog> findByAccountIdAndBlogTitleContainingOrAccountIdAndArticleContaining(Long accountId1,
			String titleKeyword, Long accountId2, String articleKeyword);

}
