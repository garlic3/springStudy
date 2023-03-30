package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 빈 팩토리를 위한 오브젝트 설정을 담당하는 클래스
// 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정 정보
@Configuration
public class DaoFactory {
	// 오브젝트를 만들어주는 IoC 메소드
	@Bean
	public UserDao userDao() {
		UserDao dao = new UserDao(connectionMaker());
		return dao;
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		ConnectionMaker connectionMaker = new DConnectionMaker();
		return connectionMaker;
	}
}
