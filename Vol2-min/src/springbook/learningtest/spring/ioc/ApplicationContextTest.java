package springbook.learningtest.spring.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import springbook.learningtest.spring.ioc.bean.AnnotatedHello;
import springbook.learningtest.spring.ioc.bean.AnnotatedHelloConfig;
import springbook.learningtest.spring.ioc.bean.Hello;
import springbook.learningtest.spring.ioc.bean.Printer;
import springbook.learningtest.spring.ioc.bean.StringPrinter;

public class ApplicationContextTest {

	public void registerBeanTest() {
		// IoC 컨테이너 생성
		// hello1 이름의 싱글톤 빈을 컨테이너에 등록
		StaticApplicationContext ac = new StaticApplicationContext();
		ac.registerSingleton("hello1", Hello.class);

		Hello hello1 = ac.getBean("hello1", Hello.class);
		assertThat(hello1, is(notNullValue()));

		// 빈 메타정보를 담은 오브젝트 생성.
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		// 빈 name 프로퍼티로 들어갈 값 지정
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		// 메타 정보를 hello2 라는 이름의 빈으로 등록
		ac.registerBeanDefinition("hello2", helloDef);

		// BeanDefinition 으로 등록된 빈이 컨테이너에 생성되고 프로퍼티 설정 이상 없는지 확인
		Hello hello2 = ac.getBean("hello2", Hello.class);
		assertThat(hello2.sayHello(), is("Hello Spring"));

		assertThat(hello1, is(not(hello2)));

		assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));

	}

	@Test
	public void registerBeanWithDependency() {
		// IoC 컨테이너 생성
		// hello1 이름의 싱글톤 빈을 컨테이너에 등록
		// 코드를 빈 메타정보로 등록하기 위해 사용
		StaticApplicationContext ac = new StaticApplicationContext();
		ac.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));

		// 빈 메타정보를 담은 오브젝트 생성.
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		// 빈 name 프로퍼티로 들어갈 값 지정
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		// 빈 래퍼런스 프로퍼티로 들어갈 값 지정
		helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));

		// 메타 정보를 hello2 라는 이름의 빈으로 등록
		ac.registerBeanDefinition("hello", helloDef);

		// BeanDefinition 으로 등록된 빈이 컨테이너에 생성되고 프로퍼티 설정 이상 없는지 확인
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();

		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));

	}

	@Test
	public void genericApplicationContext() {
//		GenericApplicationContext ac = new GenericApplicationContext();
//		// 외부 리로스의 빈 설정 메타정ㅂ로를 읽는 빈 설정정보 리더
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
//		reader.loadBeanDefinitions("springbook/learningtest/spring/ioc/bean/genericApplicationContext.xml");
//		// 메타정보 등록 완료. 애플리케이션 컨테이너 초기화
//		ac.refresh();


		// 애플리케이션 컨텍스트 생성, XML 읽기, 초기화까지 한번에 수행한다
		GenericApplicationContext ac = new GenericXmlApplicationContext("springbook/learningtest/spring/ioc//genericApplicationContext.xml");
		
		
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();

		assertThat(ac.getBean("printer").toString(), is("Hello Spring"));

	}
	
	@Test
	public void applicationContextWithParent() {
		// 현재 클래스의 패키지 정보를 클래스패스 형식으로 미리 저장해둔것
		String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()));
		ApplicationContext parent = new GenericXmlApplicationContext(basePath + "/parentContext.xml");
		
		// 자식 컨텍스트 생성
		GenericApplicationContext child = new GenericApplicationContext(parent);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
		reader.loadBeanDefinitions(basePath + "/childContext.xml");
		// 리더를 사용해서 설정을 읽은 경우 refresh() 를 통해 반드시 초기화 해야한다
		child.refresh();
		
		// 자식 컨텍스트에 존재하지 않는 빈은 부모 컨텍스트에서 찾는다
		Printer printer = child.getBean("printer", Printer.class);
		assertThat(printer, is(notNullValue()));
		
		Hello hello = child.getBean("hello", Hello.class);
		assertThat(hello, is(notNullValue()));
		
		// 부모 컨텍스트와 자식 컨텍스트 동일한 빈이 존재할 경우
		// 자식 컨텍스트가 우선된다
		hello.print();
		assertThat(printer.toString(), is("Hello Child"));
		
		
	}
	
	
	@Test
	public void simpleBeanScanning() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext("springbook.learningtest.spring.ioc.bean");
		// @Component 어노테이션이 붙은 클래스를 빈스캐너를 통해 자동등록 
		AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
		assertThat(hello, is(notNullValue()));
	}
	
	
	@Test
	public void configurationBean() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
		
		AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
		assertThat(hello, is(notNullValue()));

		// 빈 메타정보를 담고 있는 빈 오브젝트 팩토리도 빈으로 등록된다
		AnnotatedHelloConfig config = ctx.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);
		assertThat(config, is(notNullValue()));
		
		// 빈 설정 메타정보의 스코프가 디폴트인 싱글톤으로 되어 있다
		assertThat(config.annotatedHello(), is(sameInstance(hello)));
		assertThat(config.annotatedHello(), is(config.annotatedHello()));
		
		System.out.println(ctx.getBean("systemProperties").getClass());
	}
	

}
