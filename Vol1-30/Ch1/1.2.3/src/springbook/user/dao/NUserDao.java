package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 상속을 통한 확장
public class NUserDao extends UserDao {
	protected Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("org.h2.Driver");
		Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/sa", "sa", "sa");
		return c;
	}
}
