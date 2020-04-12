package domain;

import java.util.ResourceBundle;

public enum Sport {
	FOOTBALL("Football"),
	BASKETBALL("BasketBall"),
	TENNIS("Tennis"), 
	GOLF("Golf"),
	BOXING("Boxing"),
	HORSE_RACING("HorseRacing");
	
	
	String asString;
	
	Sport(String name){
		asString = name;
	}
	
	public String getString() {
		return ResourceBundle.getBundle("Etiquetas").getString(asString);
		
	}
	
	public static String[] namesArray() {
		Sport[] values = Sport.values();
		String[] nameArray = new String[values.length];
		for(Sport s : values) {
			nameArray[s.ordinal()] = ResourceBundle.getBundle("Etiquetas").getString(s.asString);
		}
		return nameArray;
	}
	
}
