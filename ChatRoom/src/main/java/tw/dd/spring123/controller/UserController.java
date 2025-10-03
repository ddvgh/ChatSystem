package tw.dd.spring123.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import tw.dd.spring123.dto.LoginDto;
import tw.dd.spring123.entity.User;
import tw.dd.spring123.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userservice;

	@PostMapping("/register")
	public ResponseEntity<?> apiRegister(@RequestBody User user) {
		User newUser = userservice.register(user.getUsername(), user.getPassword(), user.getEmail());
		
		Map<String, Object> response = new HashMap<>();
        response.put("message", "註冊成功");
        response.put("user", newUser.getUsername());

        return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<?> apiLogin(@RequestBody User user, HttpServletRequest request) {
		User iuser = userservice.login(user.getEmail(), user.getPassword());
		if (iuser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號或密碼錯誤");
		}

		// 清除舊 session
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null)
			oldSession.invalidate();

		// 建立新 session
		HttpSession newSession = request.getSession(true);
		newSession.setAttribute("user", iuser);

		LoginDto ldto = LoginDto.from(iuser);
		return ResponseEntity.ok(ldto);
	}


	
}