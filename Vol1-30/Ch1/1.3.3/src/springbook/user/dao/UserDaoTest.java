package springbook.user.dao;

import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ConnectionMaker connectionMaker = new DConnectionMaker();
		UserDao dao = new UserDao(connectionMaker);

		User user = new User();
		user.setId("user1");
		user.setName("유저1");
		user.setPassword("pass");

		dao.add(user);
			
		System.out.println(user.getId() + " 유저 아이디");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
			
		System.out.println(user2.getId() + " 유저 아이디");
		
		
		// 개방 페쇄 원칙(OCP, Open-Closed Principle)
		// 클래스나 모듈은 확장에는 열려있고 변경에는 닫혀 있어야 한다
		
		// UserDao : 전략 패턴의 컨텍스트
		// DB 연결 방식 알고리즘을 ConnectionMaker 인터페이스로 정의하고 
		// 이를 구현한 클래스(전략)을 바꿔가면서 사용
		
		// 클라이언트(UserDaoTest) 는 컨텍스트(UserDao) 가 사용할 전략(DConnectionMaker)을
		// 컨텍스트의 생성자를 통해 제공(일반적 경우)
		
		
		
	}
}
