package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

// 추상화: 어떤것들의 공통적인 성격을 뽑아 따로 분리하는것
// 추상화를 위해 인터페이스를 사용
public interface ConnectionMaker {

	public abstract Connection makeConnection() throws ClassNotFoundException,
			SQLException;

}