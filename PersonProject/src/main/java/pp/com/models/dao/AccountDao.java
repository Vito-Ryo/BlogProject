package pp.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pp.com.models.entity.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
	
	//保存処理と更新処理　insertとupdate
	Account save(Account account);
	
	//SELECT * FROM account WHERE account_email = ?
	//同じメールがあったら登録させない
	//一行だけしかコードは取得できない
	Account findByAccountEmail(String accountEmail);
	
	//SELECT * FROM account WHERE account_email=? AND password=?
	//入力メールアドレスとパスワードが一致しているか
	Account findByAccountEmailAndPassword(String accountEmail,String password);
}
