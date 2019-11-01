package jpp.qrcode;

public class MaskApplier {
	public static void applyTo(boolean[][] data, MaskFunction mask, ReservedModulesMask reservedModulesMask) {
		if(data.length != reservedModulesMask.size()){
			throw new IllegalArgumentException(" die Größe von matrix passt nicht zur Größe von reserved!");
		}
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				if (mask.mask(i,j) && !reservedModulesMask.isReserved(i,j)){
					data[i][j] = !data[i][j];
				}
			}
		}
	}
}
