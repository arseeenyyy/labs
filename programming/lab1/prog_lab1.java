public class proglab0 {
	public static int function1(int num, int[] sp) {
		int flag = 0; 
		for (int i = 0; i < sp.length; i ++) {
			if (num == sp[i])
				flag = 1;
		}
	return flag; 
	}
	public static void main(String[] args) {
		short[] c = new short[19];
		short sch1 = 0; 
		for (short i = 19; i >= 1; i --) {
			c[sch1] = i;  
			sch1 ++;
		}
		double[] x = new double[13]; 
		for (int j = 0; j <= 12; j ++ ) 
			x[j] = Math.random() * 4 - 2;
		int size1 = 19; 
		int size2 = 13; 
		int[] spis = {6, 9, 11, 12, 14, 16, 17, 18, 19};
		double[][] d = new double[size1][size2];
		for (int a = 0; a < size1; a ++ ) {
			for (int b = 0; b < size2; b ++ ) {
				if (c[a] == 15) 
					d[a][b] = Math.tan(Math.cos(Math.pow(x[b], 2 * x[b])));
				else if (function1(c[a], spis) == 1) 
					d[a][b] = Math.sin(Math.atan(Math.cos(x[b])));
				else
					d[a][b] = 4 / (Math.sin(Math.log(Math.pow(Math.tan(x[b]), 2))));
			}
		}
		for (int i = 0; i < 19; i ++) {
			for (int j = 0; j < 13; j ++) {
				System.out.printf("%.3f  ", d[i][j]);
			}
			System.out.println();
		}
	}
}

