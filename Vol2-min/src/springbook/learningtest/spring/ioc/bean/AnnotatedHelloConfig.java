package springbook.learningtest.spring.ioc.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//빈 설정 메타정보를 담고 있는 클래스
@Configuration 
public class AnnotatedHelloConfig {
	// 메소드의 이름 : 등록되는 빈의 이름
	@Bean 
	public AnnotatedHello annotatedHello() {
		// 리턴 오브젝트를 빈으로 사용 
		return new AnnotatedHello();
	}
}
