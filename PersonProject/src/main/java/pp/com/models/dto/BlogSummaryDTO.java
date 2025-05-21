package pp.com.models.dto;

import pp.com.models.entity.Blog;

/**
 * ブログ一覧表示用のDTO
 * Blogエンティティに加えて、最新コメントといいね数を保持する
 */
public class BlogSummaryDTO {

    /** ブログ本体 */
    private Blog blog;

    /** 最新コメントのテキスト（存在しない場合はnull） */
    private String latestComment;

    /** このブログの「いいね」の総数 */
    private int likeCount;

    // --- Getter / Setter ---

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getLatestComment() {
        return latestComment;
    }

    public void setLatestComment(String latestComment) {
        this.latestComment = latestComment;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}