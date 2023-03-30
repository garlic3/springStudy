package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public abstract class UserDao {
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();

		PreparedStatement ps = c.prepareStatement(
			"insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}


	public User get(String id) throws ClassNotFoundException, SQLException {
		// 팩토리 메소드 패턴: 서브클래스에서 구체적인 객체 생성 방법을 결정
		Connection c = getConnection();
		PreparedStatement ps = c
				.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}

	// 상속을 통한 확장
	// 템플릿 메소드 패턴: 슈퍼 클래스에 기본 로직 흐름을 만들고
	// 그 기능의 일부를 추상 메소드나 protected 메소드로 선언후 서브클래스에서 재정의 하여 사용
	
	abstract protected Connection getConnection() throws ClassNotFoundException, SQLException ;


	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 상속 사용의 문제 
		// 다중 상속 불가
		// 슈퍼, 서브 클래스의 긴밀한 결합
		// 상속받지 않는 다른 클래스에 사용 불가

		
		UserDao dao = new NUserDao();

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
