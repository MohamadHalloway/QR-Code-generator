package jpp.qrcode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DataPositions {
	private ReservedModulesMask mask;
	private int i;
	private int j;
	private boolean vorher;
	private Iterator<Integer> addresses;
	//	private int max;



	public DataPositions(ReservedModulesMask mask) {
		this.mask = mask;
		i = mask.size() - 1;
		vorher = true;
		boolean up = true;
		LinkedList<Integer> dataAddresses = new LinkedList();
		for (int j = mask.size() - 1; j >= 0; j -= 2) {			//saving all addresses of data Modules
			if (j == 6) {
				j++;
				continue;
			}
			//jede For-Schleife entspricht einer Spaltenüberprüfung
			while(true) {
				if (!mask.isReserved(i, j)) {
//					System.out.println(i + "," + j);
					dataAddresses.add(code(i,j));
				}
				if (!mask.isReserved(i,j-1)){
//					System.out.println(i + "," + (j-1));
					dataAddresses.add(code(i,j-1));
				}
				if (up){
					i--;
				}
				if (!up){
					i++;
				}
				if ((i < 0)){
					i = 0;
					up = !up;
					break;
				}
				if (i > mask.size() - 1){
					i = mask.size() - 1;
					up = !up;
					break;
				}

			}
		}
		addresses = dataAddresses.iterator();
		addresses.next();
		i = mask.size() - 1;
		j = mask.size() - 1;

	}

	public int i() {
		return i;
	}

	public int j() {
		return j;
	}

	public boolean next() {
		if (!vorher){
			return false;
		}
		int[] ij = decode(addresses.next());

		i = ij[0];
		j = ij[1];

		if (!addresses.hasNext()){

			vorher = false;
		}

		//if there still more addresses
		return true;
	}

	private int code(int i, int j){
		int result = 0;
		result = (i << 8) | j;
		return  result;
	}

	private int[] decode(int x){		// result = {i,j}
		int[] result = {0,0};
		result[0] = (x >> 8) & 0xFF;
		result[1] = x & 0xFF;
		return result;
	}
}
