package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class FeedbackRecordContainer {

	private FeedbackRecord fbrecord;
	private User administrator;
	private Feedback feedback;
	
	public FeedbackRecordContainer() {

	}
	
	public FeedbackRecordContainer(FeedbackRecord fbr) {
		this.fbrecord = fbr;
		this.administrator = fbr.getAdministrator();
		this.feedback = fbr.getFeedback();
	}

	public FeedbackRecord getFbr() {
		return fbrecord;
	}

	public void setFbr(FeedbackRecord fbr) {
		this.fbrecord = fbr;
	}

	public User getAdministrator() {
		return administrator;
	}

	public void setAdministrator(User administrator) {
		this.administrator = administrator;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
	
}
