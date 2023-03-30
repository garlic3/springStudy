package springbook.user.service;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class TxProxyFactoryBean implements FactoryBean<Object> {
	// TransactionHandler 생성시 필요한 정보
	Object target;
	PlatformTransactionManager transactionManager;
	String pattern;
	// 다이내믹 프록시 생성시 필요한 정보
	Class<?> serviceInterface;
	
	public void setTarget(Object target) {
		this.target = target;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setServiceInterface(Class<?> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	// FactoryBean 인터페이스 구현 메소드
	// DI 받은 정보를 통해 TransactionHandler 를 사용하는 다이내믹 프록시 생성
	public Object getObject() throws Exception {
		TransactionHandler txHandler = new TransactionHandler();
		txHandler.setTarget(target);
		txHandler.setTransactionManager(transactionManager);
		txHandler.setPattern(pattern);
		return Proxy.newProxyInstance(
			getClass().getClassLoader(),new Class[] { serviceInterface }, txHandler);
	}

	// 팩토리 빈이 생성하는 오브젝트 타입은 DI 받은 인터페이스이 타입이 된다
	// 다양한 타입으로 사용이 가능하도록 설정
	public Class<?> getObjectType() {
		return serviceInterface;
	}

	// getObject() 가 매번 같은 오브젝트를 리턴하지 않는다
	public boolean isSingleton() {
		return false;
	}
}
