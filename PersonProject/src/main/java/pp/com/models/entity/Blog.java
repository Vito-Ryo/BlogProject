package pp.com.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

}
