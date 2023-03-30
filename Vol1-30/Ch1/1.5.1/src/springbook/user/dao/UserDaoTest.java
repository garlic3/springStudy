package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springbook.user.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// ApplicationContext 구현 클래스
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		// getBean() 메소드를 통해 UserDao 클래스를 가져온다 
		// 매개변수: 1. 등록된 빈의 이름 2. 리턴 타입
		UserDao dao = context.getBean("userDao", UserDao.class);

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
		
		// UserDao가 ConnectionMaker에 의존한다
		// 의존관
	}
}
