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
	
	//ブログ一覧のチェック
	//もし、accountId==null 戻り値としてnull
	//finAll内容をコントローラークラスに渡す
	public List<Blog> selectAllBlogList(Long accountId){
		if(accountId == null) {
			return null;
		}else {
			return blogDao.findAll();
		}
	}
	
	
	
	
	
	
}
