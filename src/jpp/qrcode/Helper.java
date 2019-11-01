package jpp.qrcode;

public class Helper {
    public static int[][] booleanArray2integerArray(boolean[][] data){
        int[][] result = new int[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length ; j++) {
                if (data[i][j]){
                    result[i][j] = 1;
                }
                else {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }
    public static String[][] booleanArray2stringArray(boolean[][] data){
        String[][] result = new String[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length ; j++) {
                if (data[i][j]){
                    result[i][j] = "1";
                }
                else {
                    result[i][j] = "0";
                }
            }
        }
        return result;
    }

    public static boolean[][] integerArray2booleanArray(int[][] data){
        boolean[][] result = new boolean[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length ; j++) {
                if (data[i][j] == 1){
                    result[i][j] = true;
                }
                else {
                    result[i][j] = false;
                }
            }
        }
        return result;
    }

    public static int booleanArray2Integer(boolean[] data){    //LSB is in 0-Index und MSB is in (n-1)-Index
        int result = 0;
        //boolean to int
//        int[] integerArray = new int[data.length];
//        for (int i = 0; i < data.length; i++) {
//            if (data[i]){
//                integerArray[i] = 1;
//            }
//        }
        int x = 0;
        //intArray to int
        for (int i = 0; i < data.length; i++) {
            x = 0;
            if (data[i]){
                x = 1;
            }
            result += (Math.pow(2,i)) * x;
        }
        return  result;
    }

    public static String intQRCodeZeichnen(int[][] data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                result.append(" " + data[i][j]);

            }
            if (i == data.length - 1) {
                break;
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static String byteInBinary(byte[] information){
        StringBuilder temp = new StringBuilder();
        int index = 0;
        for (byte info: information){
            for (int i = 7; i >= 0; i--) {
                index = (byte) ((1 << i) & info);
                if (index == 0){
                    temp.append(0);
                }
                else {
                    temp.append(1);
                }
            }
            temp.append(" ");
        }
        return temp.toString();
    }
}
