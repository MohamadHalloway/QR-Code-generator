package jpp.qrcode.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextReader {
	public static boolean[][] read(InputStream in) throws IOException {
		Scanner sc = new Scanner(in).useDelimiter("\\A");
		String matrix = sc.hasNext() ? sc.next() : "";
		String[] array = matrix.split("\n");
		List<List<Boolean>> lists = new ArrayList<>();
		int index = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i].isEmpty() || array[i].charAt(0) == '#'){
				continue;
			}
			lists.add(new ArrayList<>());
			for (char character: array[i].toCharArray()) {
				if (character == ' '){
				}
				else if (character == '1'){
					lists.get(index).add(true);
				}
				else if (character == '0'){
					lists.get(index).add(false);
				} else if (character == '\r') {
					break;
				} else {
					throw new IOException("Invalid character");
				}
			}
			index++;
		}
		if (lists.isEmpty()){
			throw new IOException("InputStream is empty");
		}

		//remove all empty lists
		List<List<Boolean>> remove = new ArrayList<>();
		for (List<Boolean> list: lists){
			if (list.isEmpty()){
				remove.add(list);
			}
		}
		for (List<Boolean> list : remove){
			lists.remove(list);
		}
		boolean[][] result = new boolean[lists.size()][];
		for (int i = 0; i < lists.size(); i++) {
			boolean[] temp = new boolean[lists.get(i).size()];
			for (int j = 0; j < temp.length; j++) {
				temp[j] = lists.get(i).get(j);
			}
			result[i] = temp;
		}
		return result;
	}
}
