package tw.dd.spring123.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.dd.spring123.entity.User;
import tw.dd.spring123.repository.UserRepository;
import tw.dd.spring123.utils.BCrypt;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public User register(String username, String password, String email) {

		if (repo.existsByUsername(username)) {
			throw new RuntimeException("使用者已存在");
		}
		if (repo.existsByEmail(email)) {
			throw new RuntimeException("Email 已被使用");
		}

		String hashPasswd = BCrypt.hashpw(password, BCrypt.gensalt());

		User user = new User();
		user.setUsername(username);
		user.setPassword(hashPasswd);
		user.setEmail(email);

		User savedUser = repo.save(user);
        savedUser.setPassword(null); // 不回傳密碼
        return savedUser;
	}

	public User login(String email, String password) {
		User user = repo.findByEmail(email);
		if (user != null && BCrypt.checkpw(password, user.getPassword())) {
			return user;
		}
		return null;
	}
}
