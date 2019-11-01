package jpp.qrcode;

import java.util.Arrays;

public class ReservedModulesMask {

	private boolean[][] mask;
	public ReservedModulesMask(boolean[][] mask) {
		this.mask = mask;
	}
	
	public boolean isReserved(int i, int j) {
		return mask[i][j];
	}
	
	public int size() {
		return mask.length;
	}
	
	public static ReservedModulesMask forVersion(Version version) {
		int size = (version.number() * 4) + 17;
		boolean[][] matrix = new boolean[size][size];
		set_Orientations(matrix);
		set_Separators(matrix);
		set_version_blocks(matrix);
		set_Timing(matrix);
		set_Alignments(matrix,version);
		set_format(matrix);
		matrix[size - 8][8] = true;  //dark module

		ReservedModulesMask result = new ReservedModulesMask(matrix);
		return result;
	}

	@Override
	public String toString() {
		return "ReservedModulesMask length{ " + size() + " }";
	}

	private static void set_Orientations(boolean[][] data) {
		int index = 0;
		//obenLinks
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				data[i][j] = true;
				index++;
			}
		}
		index = 0;
		//obenRechts
		for (int i = 0; i < 7; i++) {
			for (int j = data.length - 7; j < data.length; j++) {
				data[i][j] = true;
				index++;
			}
		}

		index = 0;
		//untenLinks
		for (int i = data.length - 7; i < data.length; i++) {
			for (int j = 0; j < 7; j++) {
				data[i][j] = true;
				index++;
			}
		}

	}

	private static void set_Separators(boolean[][] data) {

		//obenLinks
		for (int i = 0; i <= 7; i++) {
			data[i][7] = true;
		}
		for (int j = 0; j < 7; j++) {
			data[7][j] = true;
		}


		//obenRechts
		for (int i = 0; i < 7; i++) {
			data[i][data.length - 8] = true;
		}
		for (int j = data.length - 8; j < data.length; j++) {
			data[7][j] = true;
		}

		//untenLinks
		for (int i = data.length - 8; i < data.length; i++) {
			data[i][7] = true;
		}
		for (int j = 0; j < 7; j++) {
			data[data.length - 8][j] = true;

		}
	}

	private static void set_version_blocks(boolean[][] data) {  //todo gehe davon aus dass es geht (Testen war schwierig zu implementieren)
		if (!(data.length < ((7 * 4) + 17))) {  //if Version < 7
		int n = 1;  //Block's number
		while(n < 3){
			for (int i = 0; i <= 5; i++) {
				for (int j = data.length - 11; j < data.length - 8; j++) {
					switch (n){
						case 1:
							data[i][j] = true;
							break;
						case 2:
							data[j][i] = true;
							break;
					}
				}
			}
			n++;
			}
		}
	}


	private static void set_Timing(boolean[][] data){
		boolean timing = true; //starts always in Black

		//Vertical Timing
		for (int i = 8; i < data.length - 8; i++) {
			data[i][6] = true;
		}

		//Horizonal Timing
		for (int j = 8; j < data.length - 8; j++) {
			data[6][j] = true;
		}
	}

	private static void set_Alignments(boolean[][] data, Version version){
		int[] alligments = version.alignmentPositions();
		for (int i: alligments){
			for (int j: alligments){
				if ((i == 6 && j == 6) || (i == 6 && j == alligments[alligments.length -1]) || (i == alligments[alligments.length-1] && j == 6)){
					continue;
				}
				set_Alignment_Adress(data,i,j);
			}
		}
	}

	private static void set_Alignment_Adress(boolean[][] data,int y,int x) {
		boolean result = true;
		int new_i = y - 2;
		int new_j = x - 2;
		int index = 0;
		for (int i = new_i; i < y + 3; i++) {
			for (int j = new_j; j < x + 3; j++) {
				data[i][j] = true;
				index++;
			}
		}
	}

	public static void set_format(boolean[][] data) {

		//Format obenLinks
		boolean[] format1 = new boolean[15];
		for (int i = 0; i < 9; i++) {
			if (i == 6){
				continue;
			}
			data[i][8] = true;

		}
		for (int j = 7; j >= 0; j--) {
			if (j == 6){
				continue;
			}
			data[8][j] = true;
		}

		//Format verteilt
		boolean[] format2 = new boolean[15];
		for (int j = data.length - 1; j >= data.length - 8 ; j--) {
			data[8][j] = true;
		}
		for (int i = data.length - 7; i <= data.length - 1; i++) {
			data[i][8] = true;
		}
	}

}
