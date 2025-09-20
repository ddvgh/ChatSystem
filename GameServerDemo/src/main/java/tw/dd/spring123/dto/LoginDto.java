package tw.dd.spring123.dto;

import lombok.Data;
import tw.dd.spring123.entity.User;

@Data
public class LoginDto {
	
    private String username;
    private String email;

    public LoginDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // 也可以寫個靜態工廠方法
    public static LoginDto from(User user) {
        return new LoginDto(user.getUsername(), user.getEmail());
    }
}
