package springbook.learningtest.spring.factorybean;

public class Message {
	String text;
	
	// 외부에서 생성자를 통한 객체 생성 불가 -> 직접 스프링빈으로 등록 불가
	// 리플렉션은 private을 위반할수 있지만 등록하지 않는다
	private Message(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	// 생성자 대신 사용할 객체 생성 static 메소드
	public static Message newMessage(String text) {
		return new Message(text);
	}
}
