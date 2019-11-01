package jpp.qrcode.encode;

import jpp.qrcode.*;

public class Encoder {
	public static QRCode createFromString(String msg, ErrorCorrection correction) {
		//Kodiert die Daten (DataEncoder und DataStructurer)
		DataEncoderResult dataEncoderResult = DataEncoder.encodeForCorrectionLevel(msg,correction);
		Version version = dataEncoderResult.version();
		byte[] data = DataStructurer.structure(dataEncoderResult.bytes(),version.correctionInformationFor(correction));
		//erstellt einen leeren QR-Code für die benötigte Version
		boolean[][] matrix = PatternPlacer.createBlankForVersion(version);
		ReservedModulesMask reservedModulesMask = ReservedModulesMask.forVersion(version);
		//fügt die kodierten Daten unmaskiert ein
		DataInserter.insert(matrix,reservedModulesMask,data);
		//bestimmt die beste Maske
		MaskPattern maskPattern = MaskSelector.maskWithBestMask(matrix,correction,reservedModulesMask);
		//erstellt aus allen gesammelten Daten einen QRCode
		QRCode qrCode = new QRCode(matrix,version,maskPattern,correction);
		return qrCode;
	}
}
