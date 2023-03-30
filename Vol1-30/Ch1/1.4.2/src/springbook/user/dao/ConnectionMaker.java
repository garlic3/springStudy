package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

// 기술 로직
public interface ConnectionMaker {

	public abstract Connection makeConnection() throws ClassNotFoundException,
			SQLException;

}