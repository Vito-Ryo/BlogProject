package pp.com.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 「いいね」情報を保存するエンティティ
 */
@Entity
@Table(name = "likes") // SQLの予約語「like」を避けるため複数形に
public class Like {

    /** 主キーID（自動採番） */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 対象のブログID */
    @Column(nullable = false)
    private Long blogId;

    /** いいねされた日時（登録時に自動セット） */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // --- Getter / Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}