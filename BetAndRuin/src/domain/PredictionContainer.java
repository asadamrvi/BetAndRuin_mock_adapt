package domain;

public class PredictionContainer {

	private Prediction cPrediction;
	private Question cQuestion;
	private Event cEvent;
	
	public PredictionContainer() {}
	
	public PredictionContainer(Prediction p) {
		this.cPrediction = p;
		this.cQuestion = p.getQuestion();
		this.cEvent = cQuestion.getEvent();
	}

	public Prediction getPrediction() {
		return cPrediction;
	}

	public void setPrediction(Prediction cPrediction) {
		this.cPrediction = cPrediction;
	}

	public Question getQuestion() {
		return cQuestion;
	}

	public void setQuestion(Question cQuestion) {
		this.cQuestion = cQuestion;
	}

	public Event getEvent() {
		return cEvent;
	}

	public void setEvent(Event cEvent) {
		this.cEvent = cEvent;
	}
	
	
}
