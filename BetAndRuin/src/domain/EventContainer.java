package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class EventContainer{

	private Event cEvent;
	private Competition cCompetition;
	
	public EventContainer() {
		
	}
	
	public EventContainer(Event ev) {
		this.cEvent = ev;
		this.cCompetition = ev.getCompetition();
	}

	public Event getEvent() {
		return cEvent;
	}

	public void setEvent(Event cEvent) {
		this.cEvent = cEvent;
	}

	public Competition getCompetition() {
		return cCompetition;
	}

	public void setCompetition(Competition cCompetition) {
		this.cCompetition = cCompetition;
	}

}
