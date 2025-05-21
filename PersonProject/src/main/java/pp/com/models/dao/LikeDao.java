package pp.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pp.com.models.entity.Like;

@Repository
public interface LikeDao extends JpaRepository<Like, Long> {

    /**
     * 指定されたブログに対する「いいね」数を数える
     */
	@Query("SELECT COUNT(l) FROM Like l WHERE l.blogId = :blogId")
    int countByBlogId(@Param("blogId") Long blogId);
}