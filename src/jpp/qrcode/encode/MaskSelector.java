package jpp.qrcode.encode;

import jpp.qrcode.*;

import javax.swing.text.MaskFormatter;
import java.util.Arrays;

public class MaskSelector {
	public static void placeFormatInformation(boolean[][] data, int formatInformation){
			int index = 0;
			//Format obenLinks
			boolean[] format1 = new boolean[15];
			for (int i = 0; i < 9; i++) {
				if (i == 6){
					continue;
				}
				data[i][8] = (formatInformation & (1 << index)) != 0;
				index++;
			}
			for (int j = 7; j >= 0; j--) {
				if (j == 6){
					continue;
				}
				data[8][j] = (formatInformation & (1 << index)) != 0;
				index++;
			}

			index = 0;
			//Format verteilt
			boolean[] format2 = new boolean[15];
			for (int j = data.length - 1; j >= data.length - 8 ; j--) {
				data[8][j] = (formatInformation & (1 << index)) != 0;
				index++;
			}
			for (int i = data.length - 7; i <= data.length - 1; i++) {
				data[i][8] = (formatInformation & (1 << index)) != 0;
				index++;
			}
	}

	public static int calculatePenaltySameColored(boolean[][] data) {
		int result = 0;
		boolean last = true;
		int n = 0;

		//Zeilen
		for (int i = 0; i < data.length; i++) {
			n = 1;
			last = data[i][0];
			for (int j = 1; j < data.length; j++) {
				if (data[i][j] == last){
					n++;
					if (j != data.length-1){
						continue;
					}
				}
				if (n >= 5){
					result += 3 + (n - 5);
				}
				last = data[i][j];
				n = 1;

			}
		}
		//Spalten
		for (int j = 0; j < data.length; j++) {
			n = 1;
			last = data[0][j];
			for (int i = 1; i < data.length; i++) {
				if (data[i][j] == last){
					n++;
					if (i != data.length-1){
						continue;
					}
				}
				if (n >= 5){
					result += 3 + (n - 5);
				}
				last = data[i][j];
				n = 1;
			}
		}



		return result;
	}
	
	public static int calculatePenalty2x2(boolean[][] arr) {
	    int result = 0;
        for (int i = 0; i <= arr.length - 2; i++) {
            for (int j = 0; j <= arr.length - 2; j++) {
                if ((arr[i][j] == arr[i+1][j]) && (arr[i+1][j] ==  arr[i][j+1]) && (arr[i][j+1] == arr[i+1][j+1])){
                    result += 3;
                }
            }
        }
        return result;
	}
	
	public static int calculatePenaltyBlackWhite(boolean[][] array) {
		int black = 0;
		int all = array.length * array.length;
		for (boolean[] booleans : array) {
			for (int j = 0; j < array.length; j++) {
				if (booleans[j]) {
					black++;
				}
			}
		}

		int penalty = 10;
		int res = 0;
		for (int k = 0; black * 20 < (9 - k) * all || black * 20 > (11 + k) * all; k++) {
			res += penalty;
		}

		return res;
	}
	
	public static int calculatePenaltyPattern(boolean[][] arr) {
		int result = 0;
		boolean gefunden,white;
		boolean[] mask = {true,false,true,true,true,false,true};
		int length = arr.length;
		for (boolean[] booleans : arr) {
			for (int j = 0; j < length - 6; j++) {
				gefunden = true;
				white = false;
				for (int k = 0; k < 7; k++) {
					if (booleans[j + k] != mask[k]) {
						gefunden = false;
						break;
					}
				}
				//when found Pattern
				if (gefunden) {
					if (j > 3 && !booleans[j - 1] && !booleans[j - 2] && !booleans[j - 3] && !booleans[j - 4]) {
						white = true;
					}
					if (j <= length - 11 && !booleans[j + 7] && !booleans[j + 8] && !booleans[j + 9] && !booleans[j + 10]) {
						white = true;
					}
					if (white) {
						result += 40;
					}
				}

			}
		}

		for (int j = 0; j < length; j++) {
			for (int i = 0; i < length - 6; i++) {
				gefunden = true;
				white = false;
				for (int k = 0; k < 7; k++) {
					if (arr[i+k][j] != mask[k]){
						gefunden = false;
						break;
					}
				}
				//when found Pattern
				if (gefunden){
					if (i > 3 && !(arr[i-1][j] || arr[i-2][j] || arr[i-3][j] || arr[i-4][j])){
						white = true;
					}
					if (i <= length - 11 && !(arr[i+7][j] || arr[i+8][j] || arr[i+9][j] || arr[i+10][j])){
						white = true;
					}
					if (white){
						result += 40;
					}
				}

			}
		}
		return result;
	}

	public static int calculatePenaltyFor(boolean[][] data) {
		return calculatePenaltySameColored(data) + calculatePenalty2x2(data) + calculatePenaltyBlackWhite(data) + calculatePenaltyPattern(data);

	}
	
	public static MaskPattern maskWithBestMask(boolean[][] data, ErrorCorrection correction, ReservedModulesMask modulesMask) {
		if(data.length != modulesMask.size()){
			throw new IllegalArgumentException("die ReservedModulesMask passt nicht zur Größe der Daten");
		}
		int min = Integer.MAX_VALUE;
		MaskPattern result = null;
		MaskPattern[] alle = MaskPattern.values();
		FormatInformation besteInfo = null;
		for (MaskPattern aktuell : alle) {
			//Bestimmen Sie die Format-Information für diese Maske und das Fehlerkorrekturlevel.
			FormatInformation formatInformation = FormatInformation.get(correction, aktuell);
			//Wenden Sie die Maske auf die Matrix an (MaskApplier).
			MaskApplier.applyTo(data, aktuell.maskFunction(), modulesMask);
			//Fügen Sie die Format-Information ein.
			placeFormatInformation(data, formatInformation.formatInfo());
			//Berechnen Sie die Strafpunktzahl.
			int strafePunkte = calculatePenaltyFor(data);
			if (strafePunkte < min) {
				min = strafePunkte;
				result = aktuell;
				besteInfo = formatInformation;
			}
			//Entmaskieren Sie die Matrix wieder.
			MaskApplier.applyTo(data, aktuell.maskFunction(), modulesMask);  //demaskieren
		}
		MaskApplier.applyTo(data,result.maskFunction(),modulesMask);
		placeFormatInformation(data,besteInfo.formatInfo());
		return result;
	}
}
