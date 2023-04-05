package springbook.learningtest.spring.ioc.bean;


public class Hello {
	private String name;
	private Printer printer;
	
	public Hello() {
	}

	public Hello(String name, Printer printer) {
		this.name = name;
		this.printer = printer;
	}
	
	// 프로퍼티로 DI 받은 이름을 사용한다
	public String sayHello() {
		return "Hello " + name;
	}
	
	// DI에 의해 의존 오브젝트를 제공받는다
	public void print() {
		this.printer.print(this.sayHello());
	}
	
	// 스트링 값을 DI 받는다
	public void setName(String name) {
		this.name = name;
	}
	
	// 구현 오브젝트를 DI 받는다
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}

	public Printer getPrinter() {
		return printer;
	}
}