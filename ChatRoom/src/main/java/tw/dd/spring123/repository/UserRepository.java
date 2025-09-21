package tw.dd.spring123.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.dd.spring123.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
