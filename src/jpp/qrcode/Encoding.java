package jpp.qrcode;

public enum Encoding {


	NUMERIC (0b1), ALPHANUMERIC (0b10), BYTE (0b100), KANJI (0b1000), ECI (0b111), INVALID (-1);

	int encoding;
	Encoding(int i) {
		encoding = i;
	}

	public static Encoding fromBits(int i) {
		Encoding result = INVALID;
		switch (i){
			case 1:
				result = NUMERIC;
				break;
			case 2:
				result = ALPHANUMERIC;
				break;
			case 4:
				result = BYTE;
				break;
			case 8:
				result = KANJI;
				break;
			case 7:
				result = ECI;
				break;
			default:
				// must do nothing cause result is already INVALID
				break;
		}
		return result;
//		throw new IllegalStateException();
	}
	
	public int bits() {
		int result = 0;
		switch (this) {
			case NUMERIC:
				result = 1;
				break;
			case INVALID:
				result = -1;
				break;
			case ALPHANUMERIC:
				result = 2;
				break;
			case BYTE:
				result = 4;
				break;
			case KANJI:
				result = 8;
				break;
			case ECI:
				result = 7;
				break;
		}
//		throw new IllegalStateException();
		return result;
	}

}
