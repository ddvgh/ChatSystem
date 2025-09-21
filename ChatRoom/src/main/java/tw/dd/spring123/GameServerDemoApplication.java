package tw.dd.spring123;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameServerDemoApplication.class, args);
		System.out.println("遊戲伺服器啟動成功");
	}

}
