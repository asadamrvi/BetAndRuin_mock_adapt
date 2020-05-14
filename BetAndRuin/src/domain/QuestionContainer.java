package domain;

public class QuestionContainer {

	private Question cQuestion;
	private Event cEvent;
	
	public QuestionContainer() {
		
	}
	
	public QuestionContainer(Question q) {
		this.cQuestion = q;
		this.cEvent = q.getEvent();
	}

	public Question getcQuestion() {
		return cQuestion;
	}

	public void setcQuestion(Question cQuestion) {
		this.cQuestion = cQuestion;
	}

	public Event getcEvent() {
		return cEvent;
	}

	public void setcEvent(Event cEvent) {
		this.cEvent = cEvent;
	}
	
	
}
