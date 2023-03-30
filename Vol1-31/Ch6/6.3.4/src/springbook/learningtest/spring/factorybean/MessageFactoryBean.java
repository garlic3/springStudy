package springbook.learningtest.spring.factorybean;

import org.springframework.beans.factory.FactoryBean;

// 팩토리 메소드를 가진 오브젝트 
public class MessageFactoryBean implements FactoryBean<Message> {
	// 오브젝트를 생성할때 필요한 정보를 팩토리 빈의 프로퍼티로 설정해서 DI

	String text;

	public void setText(String text) {
		this.text = text;
	}
	
	// 실제 빈으로 사용될 오브젝트 직접 생성
	// 빈 오브젝트를 생성해서 리턴
	public Message getObject() throws Exception {
		return Message.newMessage(this.text);
	}

	// 생성되는 오브젝트의 타입
	public Class<? extends Message> getObjectType() {
		return Message.class;
	}

	// getObject() 리턴 오브젝트 싱글톤 확인
	// 만들어진 빈 오브젝트는 싱글톤으로 스프링에서 관리
	public boolean isSingleton() {
		return true;
	}
}
