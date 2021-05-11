public class Sum10 {

	public static void main(String[] args) {
		int s = 0;
		int x[] = { 2, 4, 6, 8, 1, 3, 5, 7, 9, 0 };
		for (int k : x) {
			s = s + k;
		}
		System.out.println("s = " + s);
	}
}