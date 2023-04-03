package springbook.learningtest.spring.pointcut;

public class Target implements TargetInterface {
	// 타켓 인터페이스 구현 메소드
	public void hello() {
	}

	public void hello(String a) {
	}

	public int minus(int a, int b) throws RuntimeException {
		return 0;
	}

	public int plus(int a, int b) {
		return 0;
	}
	
	// 타겟 클래스 정의 메소드
	public void method() {}
}
