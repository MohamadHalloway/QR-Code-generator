package jpp.qrcode.encode;

import jpp.qrcode.ErrorCorrection;
import jpp.qrcode.Version;

import java.util.Arrays;

public final class DataEncoder {
	private static byte[] padding = {(byte)0b11101100, 0b00010001};
	public static DataEncoderResult encodeForCorrectionLevel(String str, ErrorCorrection level) {
		byte[] stringBytes = str.getBytes();
		int length = stringBytes.length;
		Version version = Version.forDataBytesCount(length+2,level);
		int cci = version.number() >= 10 ? 2 : 1;
		if (cci == 2){
			version = Version.forDataBytesCount(length+3,level);
		}
		int anzahlData = version.correctionInformationFor(level).totalDataByteCount();
		byte[] result = new byte[anzahlData];

		result[0] = (byte) (4 << 4); //BYTE Indicator
		//CCI setzen
		if (cci == 1){
			result[0] = (byte) (result[0] | ((length >> 4) & 0xF));
			result[1] = (byte) ((length & 0xF) << 4);
		}else {
			result[0] = (byte) (result[0] | ((length >> 12) & 0xF));
			result[1] = (byte) ((length >> 4)& 0xFF);
			result[2] = (byte) ((length << 4)& 0xF0);
		}

		int index = cci;
		for (int i = 0; i < length; i++) {
			result[index] = (byte) (result[index] | ((stringBytes[i] >> 4) & 0xF));
			result[index + 1] =  (byte) ((stringBytes[i] & 0xF) << 4);
			index++;
		}
		//The rest of the dataBytes
		int index2 = 0;
		for (int i = index + 1; i < anzahlData; i++) {
			result[i] = padding[index2 % 2];
			index2++;
		}
		return new DataEncoderResult(result,version);
	}

	public static void main(String[] args) {
		String test = "hallo Filip";
		DataEncoderResult res = encodeForCorrectionLevel(test,ErrorCorrection.LOW);
//		System.out.println(Arrays.toString(res.bytes()));
//		System.out.println(res.bytes.length);
	}
}
