package springbook.learningtest.spring.pointcut;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class PointcutExpressionTest {
	@Test
	public void pointcut() throws Exception {
		
		// 메소드의 풀 시그니처 확인 방법
		System.out.println(Target.class.getMethod("minus", int.class, int.class));
		
		// 리턴 타입, 매개변수, 메소드 이름에 상관없이 모든 메소드 조건을 허용
		tagetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
		// 메소드명 hello, 매개변수 모든 종류
		tagetClassPointcutMatches("execution(* hello(..))", true, true, false, false, false, false);
		// 메소드명 hello, 매겨변수 없음
		tagetClassPointcutMatches("execution(* hello())", true, false, false, false, false, false);
		// 메소드명 hello, 매개변수 String
		tagetClassPointcutMatches("execution(* hello(String))", false, true, false, false, false, false);
		// meth로 시작하는 메소드, 매개변수 모든 종류
		tagetClassPointcutMatches("execution(* meth*(..))", false, false, false, false, true, true);
		// 매개변수 int, int 모든 메소드
		tagetClassPointcutMatches("execution(* *(int,int))", false, false, true, true, false, false);
		// 매개변수 없는 모든 메소드
		tagetClassPointcutMatches("execution(* *())", true, false, false, false, true, true);
		//  클래스 지정
		tagetClassPointcutMatches("execution(* springbook.learningtest.spring.pointcut.Target.*(..))", true, true, true, true, true, false);
		// 패키지 지정
		tagetClassPointcutMatches("execution(* springbook.learningtest.spring.pointcut.*.*(..))", true, true, true, true, true, true);
		// pointcut 서브 패키지 포함 지정
		tagetClassPointcutMatches("execution(* springbook.learningtest.spring.pointcut..*.*(..))", true, true, true, true, true, true);
		// springbook 서브 패키지 포함 지정
		tagetClassPointcutMatches("execution(* springbook..*.*(..))", true, true, true, true, true, true);
		// com으로 시작하는 패키지 지정
		tagetClassPointcutMatches("execution(* com..*.*(..))", false, false, false, false, false, false);
		// 클래스이름 Target
		tagetClassPointcutMatches("execution(* *..Target.*(..))", true, true, true, true, true, false);
		// 클래스이름 Tar으로 시작되는것
		tagetClassPointcutMatches("execution(* *..Tar*.*(..))", true, true, true, true, true, false);
		// 클래스이름 get으로 끝나는것
		tagetClassPointcutMatches("execution(* *..*get.*(..))", true, true, true, true, true, false);
		tagetClassPointcutMatches("execution(* *..B*.*(..))", false, false, false, false, false, true);
		
		// 해당 인터페이스를 구현한 메소드만
		tagetClassPointcutMatches("execution(* *..TargetInterface.*(..))", true, true, true, true, false, false);

		// 예외선언 확인
		tagetClassPointcutMatches("execution(* *(..) throws Runtime*)", false, false, false, true, false, true);

		// 리턴타입 int
		tagetClassPointcutMatches("execution(int *(..))", false, false, true, true, false, false);
		// 리턴타입 void
		tagetClassPointcutMatches("execution(void *(..))", true, true, false, false, true, true);
	}
		public void tagetClassPointcutMatches(String expression, boolean... expected) throws Exception {
		pointcutMatches(expression, expected[0], Target.class, "hello");
		pointcutMatches(expression, expected[1], Target.class, "hello", String.class);
		pointcutMatches(expression, expected[2], Target.class, "plus", int.class, int.class);
		pointcutMatches(expression, expected[3], Target.class, "minus", int.class, int.class);
		pointcutMatches(expression, expected[4], Target.class, "method");
		pointcutMatches(expression, expected[5], Bean.class, "method");
	}


	public void pointcutMatches(String expression, Boolean expected, Class<?> clazz, String methodName, Class<?>... args) throws Exception {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);
		// 포인트컷의 클래스 필터와 메소드 매처 일치여부 확인
		assertThat(pointcut.getClassFilter().matches(clazz) 
				   && pointcut.getMethodMatcher().matches(clazz.getMethod(methodName, args), null),
				   is(expected));
	}
	
	
	@Test
	public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(
				"execution(* minus(int,int))");
		
		// Target.minus() 
		assertThat(pointcut.getClassFilter().matches(Target.class) &&
				   pointcut.getMethodMatcher().matches(
					  Target.class.getMethod("minus", int.class, int.class), null), is(true));
		// Target.plus()
		assertThat(pointcut.getClassFilter().matches(Target.class) &&
				   pointcut.getMethodMatcher().matches(
					  Target.class.getMethod("plus", int.class, int.class), null), is(false));

		// Bean.method() 
		assertThat(pointcut.getClassFilter().matches(Bean.class) &&
				pointcut.getMethodMatcher().matches(
						Target.class.getMethod("method"), null), is(false));
	}
}
