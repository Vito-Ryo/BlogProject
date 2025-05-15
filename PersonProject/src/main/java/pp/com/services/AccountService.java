package pp.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pp.com.models.dao.AccountDao;
import pp.com.models.entity.Account;

@Service
public class AccountService {
	@Autowired
	private AccountDao accountDao;

	// 登録処理
	// パスワードが一致するか判断
	// もし、findByAdminEmail == null だったら処理する
	// saveメソッドを使用して登録処理する
	// 保存できたらtrue
	// そうでない場合、保存処理失敗
	public boolean createAccount(String accountEmail, String accountName, String password) {
		if (accountDao.findByAccountEmail(accountEmail)==null) {
			accountDao.save(new Account(accountName, accountEmail, password));
			return true;
		} else {
			return false;
		}
	}

	// ログイン処理
	// もし、emailとpasswordがfindByAccountEmailAndPasswordを使用して存在しないかった場合==null
	// その場合、存在しないnullであることをコントローラークラスに知らせる
	// そうでない場合、ログイン処している人の情報をコントローラークラスに渡す

	public Account loginCheck(String accountEmail, String password) {
		Account account = accountDao.findByAccountEmailAndPassword(accountEmail, password);
		if (account == null) {
			return null;
		} else {
			return account;
		}

	}

}
