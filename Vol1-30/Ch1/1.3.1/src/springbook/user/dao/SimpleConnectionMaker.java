package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 상속을 사용하지 않으니 추상 클래스로 선언하지 않음
public class SimpleConnectionMaker {
	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("org.h2.Driver");
		Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/sa", "sa", "sa");
		return c;
	}
}
