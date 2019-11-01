package jpp.qrcode.encode;

import jpp.qrcode.DataPositions;
import jpp.qrcode.ReservedModulesMask;

public class DataInserter {
	public static void insert(boolean[][] target, ReservedModulesMask mask, byte[] data) {
		DataPositions dataPositions = new DataPositions(mask);
		for (int i = 0; i < data.length; i++) {
			int n = 7;
			do {
				target[dataPositions.i()][dataPositions.j()] = (data[i] & (1 << n)) != 0;
				n--;
			}while (dataPositions.next() && n >= 0);
		}

	}
}
