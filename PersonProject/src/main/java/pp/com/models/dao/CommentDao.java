package pp.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import pp.com.models.entity.Comment;

@Repository
@Transactional
public interface CommentDao extends JpaRepository<Comment, Long> {
	// 指定されるブログのコメント一覧を取得
	List<Comment> findByBlogId(Long blogId);

	/**
	 * 指定されたブログの最新のコメントを取得（1件目だけ使う）
	 * JPQLではLIMIT 1が使えないためListで返す
	 */
	@Query("SELECT c FROM Comment c WHERE c.blogId = :blogId ORDER BY c.id DESC")
	List<Comment> findLatestCommentByBlogId(@Param("blogId") Long blogId);
}
