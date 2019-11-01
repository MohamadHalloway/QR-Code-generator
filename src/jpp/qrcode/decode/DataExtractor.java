package jpp.qrcode.decode;
import jpp.qrcode.DataPositions;
import jpp.qrcode.ReservedModulesMask;

public class DataExtractor {
	public static byte[] extract(boolean[][] data, ReservedModulesMask mask, int byteCount) {
		if (mask.size() != data.length){
			throw new IllegalArgumentException("Mask and Matrix length are not equal");
		}
		byte[] result = new byte[byteCount];
		DataPositions dataPositions = new DataPositions(mask);
		byte index = 0;

		for (int i = 0; i < byteCount; i++) {
			byte bits = 0;
			int n = 7;
			do {
//                System.out.println("[i: " + dataPositions.i() + ",j: " + dataPositions.j() + "]");
                boolean test = data[dataPositions.i()][dataPositions.j()];
//					System.out.println(test);
                index = (byte) (test ? 1 : 0);
                bits = (byte) (bits | (index << n));
                n--;
           }while (dataPositions.next() && n >= 0);
			result[i] = bits;
		}
		return result;
 	}
}
