package pp.com.models.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;

@Entity
public class Blog {
	// blog_idの設定
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long blogId;

	// blog_title
	private String blogTitle;
	// article
	private String article;
	// blog_image
	private String blogImage;
	// account_id
	private Long accountId;
	// 作成日時
	private LocalDateTime createAt;
	// 更新日時
	private LocalDateTime updateAt;

	// 空のコンストラク
	public Blog() {
	}

	// コンストラク
	public Blog(String blogTitle, String article, String blogImage, Long accountId) {
		this.blogTitle = blogTitle;
		this.article = article;
		this.blogImage = blogImage;
		this.accountId = accountId;
	}

	// データ新規作成時に自動実行
	@PrePersist
	protected void onCreate() {
		this.createAt = LocalDateTime.now();
		this.updateAt = LocalDateTime.now();
	}

	// データ更新時に自動実行
	@PreUpdate
	protected void onUpdate() {
		this.updateAt = LocalDateTime.now();
	}

	// JPA に保存されないフィールド
	@Transient
	public String getFormattedUpdateAt() {
		if (updateAt == null)
			return "";
		return updateAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	// データベースのカラムにはならない
	@Transient
	public String getFormattedCreateAt() {
		if (createAt == null)
			return "";
		return createAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	// Getter/setter
	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getBlogImage() {
		return blogImage;
	}

	public void setBlogImage(String blogImage) {
		this.blogImage = blogImage;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

}
