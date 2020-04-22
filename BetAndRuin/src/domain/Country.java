package domain;

public enum Country {
	AD ("Andorra"),
	AE ("United Arab Emirates"), 
	AR ("Argentina"),  
	AT ("Austria"),  
	AU ("Australia"),  
	BA ("Bosnia and Herzegovina"),  
	BB ("Barbados"),  
	BE ("Belgium"),  
	BG ("Bulgaria"),  
	BO ("Bolivia"),  
	BR ("Brazil"),  
	CA ("Canada"),  
	CH ("Switzerland"), 
	CI ("Ivory Coast"), 
	CL ("Chile"), 
	CM ("Cameroon"), 
	CN ("China"), 
	CO ("Colombia"), 
	CR ("Costa Rica"), 
	CS ("Czechoslovakia"), 
	CY ("Cyprus"), 
	CZ ("Czech Republic"), 
	DD ("East-Germany"), 
	DE ("Germany"), 
	DK ("Denmark"), 
	EC ("Ecuador"), 
	EE ("Estonia"), 
	EG ("Egypt"), 
	ES ("Spain"), 
	FI ("Finland"), 
	FR ("France"), 
	GB ("United Kingdom"), 
	GE ("Georgia"), 
	GR ("Greece"), 
	HK ("Hong Kong"), 
	HR ("Croatia"), 
	HU ("Hungary"), 
	ID ("Indonesia"), 
	IE ("Ireland"), 
	IL ("Israel"), 
	IN ("India"), 
	IQ ("Iraq"), 
	IR ("Iran"), 
	IS ("Iceland"), 
	IT ("Italy"), 
	JM ("Jamaica"), 
	JO ("Jordan"), 
	JP ("Japan"), 
	KE ("Kenya"), 
	KR ("South Korea"), 
	KW ("Kuwait"), 
	KZ ("Kazakhstan"), 
	LB ("Lebanon"), 
	LT ("Lithuania"), 
	LU ("Luxembourg"), 
	LV ("Latvia"), 
	MA ("Morocco"), 
	MC ("Monaco"), 
	MX ("Mexico"), 
	MY ("Malaysia"), 
	NA ("Namibia"), 
	NL ("Netherlands"), 
	NO ("Norway"), 
	NZ ("New Zealand"), 
	OM ("Oman"), 
	PE ("Peru"), 
	PH ("Philippines"), 
	PL ("Poland"), 
	PS ("Palestine"), 
	PT ("Portugal"), 
	PY ("Paraguay"), 
	QA ("Qatar"), 
	RO ("Romania"), 
	RS ("Serbia"), 
	RU ("Russia"), 
	SA ("Saudi Arabia"), 
	SE ("Sweden"), 
	SG ("Singapore "),
	SI ("Slovenia"),
	SK ("Slovakia"),
	SM ("San Marino"),
	SU ("Soviet Union"),
	TH ("Thailand"),
	TU ("Turkey"),
	TW ("Taiwan"),
	TZ ("Tanzania"),
	UA ("Ukraine"),
	UG ("Uganda"),
	US ("United States"), 
	UY ("Uruguay"),
	VE ("Venezuela"), 
	YU ("Yugoslavia"), 
	ZA ("South Africa"), 
	ZM ("Zambia"), 
	ZW ("Zimbabwe");

	private final String asString;
	
	Country(String name) {
		this.asString = name;
	} 

	public String getString() {
		return asString;
	}
	
	public static String[] namesArray() {
		Country[] values = Country.values();
		String[] nameArray = new String[values.length];
		for(Country n : values) {
			nameArray[n.ordinal()] = n.asString + ", " + n;
		}
		return nameArray;
	}
	
	/**
	 * Retrieves Country attached to the given name.
	 */
	public static Country getValue(String name) {
		for(Country c: Country.values()) {
			if(c.asString.equals(name)) {
				return c;
			}
		}
		return null;// not found
	}

}
