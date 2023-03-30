package springbook.user.dao;



// 객체 생성 방법을 결정하고 객체를 반환하는 객체 : 팩토리 
// 애플리케이션을 구성하는 컴포넌트의 구조와 관계를 정의한다
public class UserDaoFactory {
	public UserDao userDao() {
		UserDao dao = new UserDao(connectionMaker());
		return dao;
	}

	public ConnectionMaker connectionMaker() {
		// 객체 생성 방법 결정
		ConnectionMaker connectionMaker = new DConnectionMaker();
		// 객체 반환
		return connectionMaker;
	}
}
