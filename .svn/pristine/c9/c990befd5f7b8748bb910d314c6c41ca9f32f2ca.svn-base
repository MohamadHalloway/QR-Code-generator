package jpp.qrcode.decode;

import jpp.qrcode.*;

public class DataDecoder {
	public static Encoding readEncoding(byte[] bytes) {
		int bits = (bytes[0] >> 4)& 0x0F ;
		Encoding result = Encoding.fromBits(bits);
		return result;
	}
	
	public static int readCharacterCount(byte[] bytes, int count) {
		int bits = bytes[0] & 0xF;
		if (count == 8){
			bits = (bits << 4) | ((bytes[1] >> 4) & 0xF);
		}
		else {
			int first =((bits << 4)& 0xF0) | ((bytes[1] >> 4) & 0xF);
			int second = ((bytes[1] << 4 & 0xF0) | ((bytes[2] >> 4) & 0xF));
			int third = ((first << 8) | second) ;
			bits = third & 0xFFFF;
		}
		return bits;
	}
	
	public static String decodeToString(byte[] bytes, Version version, ErrorCorrection errorCorrection) {
		int minus = (version.number() >= 10) ? 3 : 2;
		int n = version.correctionInformationFor(errorCorrection).totalDataByteCount();
		if (n != bytes.length){
        throw new IllegalArgumentException("die Byteanzahl passt nicht zur Datenbyteanzahl dieser Version mit dem gegebenen Fehlerkorrekturlevel");
		}
		int cci = (version.number() >= 10) ? 16 : 8;
		if (!readEncoding(bytes).equals(Encoding.BYTE) || readCharacterCount(bytes,cci) > bytes.length - minus){  //todo should we sub anything from bytes.length
			throw new QRDecodeException("Encoding is not BYTE");
		}
		String result = "";
		int index = (cci == 8) ? 1 : 2;
		int anzahlbytes = readCharacterCount(bytes,cci);
		byte[] temp = new byte[anzahlbytes];
		for (int i = 0; i < anzahlbytes; i++) {
			temp[i] = (byte) (((bytes[index] << 4) & 0xF0) |((bytes[index+1] >> 4) & 0xF));
			index++;
		}
		result = new String(temp);
		return result;
	}
}
