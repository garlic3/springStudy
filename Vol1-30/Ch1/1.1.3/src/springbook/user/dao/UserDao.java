package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao {
	public void add(User user) throws ClassNotFoundException, SQLException {
		// DB 드라이버 
		Class.forName("org.h2.Driver");
		// DB 연결을 위한 Connection 
		Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/sa", "sa", "sa");

		// SQL을 담은 Statement
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		// 쿼리 실행
		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		// DB 드라이버
		Class.forName("org.h2.Driver");
		// DB 연결을 위한 Connection
		Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/sa", "sa", "sa");

		// SQL을 담은 Statement
		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		// 쿼리 실행 결과 ResultSet으로 받기 
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

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new UserDao();

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
