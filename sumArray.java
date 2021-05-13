package Hello;
public class sumArray {
    public static void main(String args[]) {
  	int n = 0;
        int array[][] = {
        	{ 1, 11, 12, 6, 5, 0 },
                { 10, 2, 7, 4, 15, 0 },
                { 9, 8, 3, 13, 14, 0 }
	};
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 5; j++) {
                n += array[i][j];
            }
            array[i][5] = n;   
            n =0;
        }
        for(int i = 0; i < 3; i++) {
            System.out.printf("%dÇàÀÇ ÇÕ: %d\n", i + 1, array[i][5]);
        }
    }
}
