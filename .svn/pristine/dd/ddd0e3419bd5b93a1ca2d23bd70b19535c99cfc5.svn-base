package jpp.qrcode.decode;

import jpp.qrcode.MaskApplier;
import jpp.qrcode.QRCode;
import jpp.qrcode.ReservedModulesMask;

public class Decoder {
	public static String decodeToString(QRCode qrCode) {

		//Kopie
		boolean[][] mask = new boolean[qrCode.data().length][qrCode.data().length];
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask.length; j++) {
				mask[i][j] = qrCode.data()[i][j];
			}
		}
		QRCode kopie = new QRCode(mask,qrCode.version(),qrCode.maskPattern(),qrCode.errorCorrection());

		ReservedModulesMask reservedModulesMask = ReservedModulesMask.forVersion(kopie.version());

		MaskApplier.applyTo(kopie.data(),kopie.maskPattern().maskFunction(),reservedModulesMask);

		byte[] extrahiert = DataExtractor.extract(kopie.data(),reservedModulesMask,kopie.version().totalByteCount());

		extrahiert = DataDestructurer.destructure(extrahiert,kopie.version().correctionInformationFor(kopie.errorCorrection()));

		String result = DataDecoder.decodeToString(extrahiert,kopie.version(),kopie.errorCorrection());
		return result;
	}
}
