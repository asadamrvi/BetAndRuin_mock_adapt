package domain;

public enum BetType {

	SINGLE("Single", 1, 1),
	DOUBLE("Double", 2, 1),
	TREBLE("Treble", 3, 1),
	FOURFOLD("4-Fold", 4, 1),
	FIVEFOLD("5-Fold", 5, 1),
	SIXFOLD("6-Fold", 6, 1),
	SEVENFOLD("7-Fold", 7, 1),
	EIGHTFOLD("8-Fold", 8, 1),
	NINEFOLD("9-Fold", 9, 1),
	TENFOLD("10-Fold", 10, 1),
	TRIXIE("Trixie", 3, 4),
	PATENT("Patent", 3, 7),
	YANKEE("Yankee", 4, 11),
	SUPER_YANKEE("Super Yankee", 5, 26),
	HEINZ("Heinz", 6, 57),
	SUPER_HEINZ("Super Heinz", 7, 120),
	GOLIATH("Goliath", 8, 247);


	private final String name;
	private final int predictioncount;
	private final int betcount;

	BetType(String name,int predictioncount ,int betcount) {
		this.name = name;
		this.predictioncount = predictioncount;
		this.betcount = betcount;
	} 

	public String getString() {
		return name;
	}

	public int predictionCount() {
		return predictioncount;
	}
	
	public int getBetCount() {
		return betcount;
	}

	/**
	 * Retrieves BetType attached to the given value.
	 */
	public static BetType getType(int predictioncount) {
		for(BetType bt: BetType.values()) {
			if(bt.predictioncount == predictioncount) {
				return bt;
			}
		}
		return null;// not found
	}
}
