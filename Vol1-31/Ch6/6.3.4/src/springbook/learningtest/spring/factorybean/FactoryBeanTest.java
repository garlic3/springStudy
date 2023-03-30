package springbook.learningtest.spring.factorybean;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration // 설정파일 이름 미지정시 클래스 이름 + "-context.xml"로 사용 
public class FactoryBeanTest {
	@Autowired
	ApplicationContext context;
	
	// 팩토리 빈 테스트
	@Test
	public void getMessageFromFactoryBean() {
		Object message = context.getBean("message");
		// 타입 확인
		assertThat(message, is(Message.class));
		// 설정과 기능 확인
		assertThat(((Message)message).getText(), is("Factory Bean"));
	}
	
	// 팩토리 빈을 가져오는 기능 테스트
	@Test
	public void getFactoryBean() throws Exception {
		// &을 이름 앞에 붙이지 않으면 팩토리 빈이 만들어주는 빈 오브젝트를 가져온다
		// &을 이름 앞에 붙여주면 팩토리 빈 자체를 가져온다
		Object factory = context.getBean("&message");
		assertThat(factory, is(MessageFactoryBean.class));
	}
}
