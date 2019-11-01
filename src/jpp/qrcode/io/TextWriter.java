package jpp.qrcode.io;

import jpp.qrcode.Helper;
import jpp.qrcode.QRCode;
import jpp.qrcode.QRCodeException;
import jpp.qrcode.Version;
import jpp.qrcode.decode.Decoder;
import jpp.qrcode.decode.QRDecodeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TextWriter {
	public static void write(OutputStream stream, boolean[][] data) throws IOException {
		if (data.length != 0){

			try{

			StringBuilder stringBuilder = new StringBuilder();
			QRCode qrCode = QRCode.createValidatedFromBooleans(data);
			stringBuilder.append("# " + qrCode.version().toString() + "\n");
			stringBuilder.append("# ErrorCorrection " + qrCode.errorCorrection() + "\n");
			stringBuilder.append("# MaskPattern " + qrCode.maskPattern() + "\n");
			stringBuilder.append("# Content: \"" + Decoder.decodeToString(qrCode) +"\"\n");
			stringBuilder.append(matrixTextWriter(qrCode.data()) + "\n");
			stringBuilder.append(Helper.intQRCodeZeichnen(Helper.booleanArray2integerArray(data)));
			String result = stringBuilder.toString();
			stream.write(result.getBytes());
		}
		catch(QRCodeException e){
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(Helper.intQRCodeZeichnen(Helper.booleanArray2integerArray(data)));
			stream.write(stringBuilder.toString().getBytes());
		}

	}
	}

	private static String matrixTextWriter(boolean[][] matrix) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < matrix.length; i++) {
			result.append("# ");
			for (int j = 0; j < matrix[i].length ; j++) {
				if (matrix[i][j]){
					result.append((char) 0x2588 + "" + (char) 0x2588);
				}
				else {
					result.append((char) 0x2591 + "" + (char) 0x2591);
				}
			}
			if (i == matrix.length - 1){
				break;
			}
			result.append("\n");
		}
		return result.toString();
	}
}
