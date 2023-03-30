package springbook.user.dao;

import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 팩토리로부터 UserDao 객체를 전달받는다
		// 애플리케이션의 컴포넌트 역활을 하는 객체와 애플리케이션의 구조를 결정하는 객체를 분리 
		UserDao dao = new UserDaoFactory().userDao();

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
	}
}
