package jpp.qrcode.decode;

import jpp.qrcode.DataBlock;
import jpp.qrcode.ErrorCorrection;
import jpp.qrcode.ErrorCorrectionGroup;
import jpp.qrcode.ErrorCorrectionInformation;
import jpp.qrcode.reedsolomon.ReedSolomon;
import jpp.qrcode.reedsolomon.ReedSolomonException;

import java.util.Arrays;
import java.util.stream.Stream;

public class DataDestructurer {
	static int n;
	public static byte[] join(DataBlock[] blocks, ErrorCorrectionInformation errorCorrectionInformation) throws QRDecodeException {
		try {
			n = 0;
			for (DataBlock block: blocks){
				ReedSolomon.correct(block.dataBytes(),block.correctionBytes());
				n++;
			}
		}catch (Exception e){
			throw new QRDecodeException("Fehler beim Dekodieren von Block" + n);
		}
		byte[] res = new byte[errorCorrectionInformation.totalDataByteCount()];
		int index = 0;
		for (DataBlock block: blocks){
			for (byte info: block.dataBytes()){
				res[index] = info;
				index++;
			}
		}
		return res;
	}
	
	public static DataBlock[] deinterleave(byte[] data, ErrorCorrectionInformation ecInformation) {
	    if (data == null || data.length == 0){
	        throw new IllegalArgumentException("data in deinterleave is empty ");
        }
		DataBlock[] result = new DataBlock[ecInformation.totalBlockCount()];
		ErrorCorrectionGroup[] ecGroup = ecInformation.correctionGroups();
		int anzahlBloecke = ecInformation.totalBlockCount();
		int anzahlBlocke2 = 0;
		if (ecGroup.length > 1){
			anzahlBlocke2 = ecGroup[1].blockCount();
		}
		int anzahlData = ecInformation.totalDataByteCount();
		//Init databytes
		byte[][] dataBytes = new byte[anzahlBloecke][];
		int index = 0;
		for (int i = 0; i < ecGroup.length; i++) {
			for (int j = 0; j < ecGroup[i].blockCount(); j++) {
				dataBytes[index] = new byte[ecGroup[i].dataByteCount()];
				index++;
			}
		}
		//databytes
		index = 0;
		for (int i = 0; i < anzahlData - anzahlBlocke2; i += anzahlBloecke) {
			for (int j = 0; j < anzahlBloecke; j++) {
				dataBytes[j][index] = data[i+j];
			}
			index++;
		}

			//if more than Group
			if (ecGroup.length > 1){
				index = anzahlData - ecGroup[1].blockCount();
				for (int i = ecGroup[0].blockCount(); i < dataBytes.length; i++) {
					dataBytes[i][dataBytes[i].length - 1] = data[index];
					index++;
				}
			}

		//correctionBytes
		int anzahlCorrectionBytes = ecInformation.correctionBytesPerBlock();
		index = 0;
		byte[][] correctionBytes = new byte[anzahlBloecke][anzahlCorrectionBytes];
		for (int i = anzahlData ; i < data.length; i += anzahlBloecke) {
			for (int j = 0; j < anzahlBloecke; j++) {
				correctionBytes[j][index] = data[i+j];
			}
			index++;
		}
		for (int i = 0; i < anzahlBloecke; i++) {
			result[i] = new DataBlock(dataBytes[i],correctionBytes[i]);
		}
		return result;
//		int x = 0;
//		int index = 0;
//		for (int i = 0; i < ecGroup.length; i++) {
//			DataBlock[] temp = getblocks(data,x,ecInformation,ecGroup[i]);
//			for (int j = 0; j < temp.length; j++) {
//				result[index] = temp[j];
//				index++;
//			}
//			x = ecGroup[i].blockCount() * (ecGroup[i].dataByteCount() + ecInformation.correctionBytesPerBlock());
//		}
//				DataBlock[] resultExtra = new DataBlock[0];
//				//if there are two groups
//				if (ecGroup.length == 2){
//					int anzahlBloeckeExtra = ecGroup[1].blockCount();
//					int anzahlDataExtra = ecGroup[1].dataByteCount();
//					int anzahlCorrectionBytesExtra = ecInformation.correctionBytesPerBlock();
//					resultExtra = new DataBlock[anzahlBloeckeExtra];
//					//dataBytes
//					int indexExtra = 0;
//					byte[][] dataBytesExtra = new byte[anzahlBloeckeExtra][anzahlDataExtra];
//					for (int i = 0; i < anzahlDataExtra * anzahlBloeckeExtra; i += anzahlBloeckeExtra) {
//						for (int j = 0; j < anzahlBloeckeExtra; j++) {
//							dataBytesExtra[j][indexExtra] = data[i+j];
//						}
//						indexExtra++;
//					}
//
//					//correctionBytes
//					indexExtra = 0;
//					byte[][] correctionBytesExtra = new byte[anzahlBloeckeExtra][anzahlCorrectionBytesExtra];
//					for (int i = anzahlDataExtra  * anzahlBloeckeExtra; i < data.length; i += anzahlBloeckeExtra) {
//						for (int j = 0; j < anzahlBloeckeExtra; j++) {
//							correctionBytesExtra[j][indexExtra] = data[i+j];
//						}
//						indexExtra++;
//					}
//					for (int i = 0; i < anzahlBloeckeExtra; i++) {
//						resultExtra[i] = new DataBlock(dataBytesExtra[i],correctionBytesExtra[i]);
//					}
//				}
//		DataBlock[] lastresult = Stream.concat(Arrays.stream(result),Arrays.stream(resultExtra)).toArray(DataBlock[]::new);
//
	}
	
	public static byte[] destructure(byte[] data, ErrorCorrectionInformation ecBlocks){
		if (data.length != ecBlocks.totalByteCount()){
		    throw new IllegalArgumentException("anzhal der Bytes ungleich der Bytes in data");
        }
		DataBlock[] dataBlocks = deinterleave(data,ecBlocks);
		byte[] res = join(dataBlocks,ecBlocks);
		return res;
	}

//	private static DataBlock[] getblocks(byte[] data,int x, ErrorCorrectionInformation ecInformation,ErrorCorrectionGroup group){
//
//
//	}
}
