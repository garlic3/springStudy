package springbook.user.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

// 스프링의 어드바이스 인터페이스를 구현한다
public class TransactionAdvice implements MethodInterceptor {
	PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	// 프록시로부터 타겟을 호출하는 콜백 오브젝트를 전달받는다
	public Object invoke(MethodInvocation invocation) throws Throwable {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			// 콜백을 호출해서 타겟의 메소드를 실행
			Object ret = invocation.proceed();
			this.transactionManager.commit(status);
			return ret;
		} catch (RuntimeException e) {
			// 스프링의 MethodInvocation을 통한 타겟 호출은
			// 예외가 포장되지 않고 타겟에서 보낸 그대로 전달된다
			this.transactionManager.rollback(status);
			throw e;
		}
	}
}
