package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Association class between Feedback and User (specifically directed at administrators) that records 
 * activity of an admin regarding that feedback(whether he/she has read the feedback..)
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class FeedbackRecord implements Serializable{

	@Id 
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer fbrecordnumber;
	
	@XmlIDREF
	private User administrator;
	
	@XmlIDREF
	private Feedback feedback;
	
	private boolean read;
	
	public FeedbackRecord() {
		
	}
	
	public FeedbackRecord(User u , Feedback fb, boolean read) {
		this.administrator = u;
		this.feedback = fb;
		this.read = read;
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

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
}
