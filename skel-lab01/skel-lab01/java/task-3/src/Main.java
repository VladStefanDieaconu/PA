import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in1.txt";
		public final static String OUTPUT_FILE = "out1.txt";

		int n, x, y;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				x = sc.nextInt();
				y = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int answer) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", answer);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getAnswer(int n, int x, int y) {
			// TODO: Calculati valoarea de pe pozitia (x, y) din matricea de dimensiune
			// 2^N * 2^N.
			int minlin = 1;
			int maxlin = 1 << n;
			int mincol = 1;
			int maxcol = 1 << n;
			int l = 1;
			int r = (1 << n) * (1 << n);
			// int u = 1;
			// int d = n;
			int offset;
			while (true ) {
				if(r == l + 3) {
					if(x % 2 == 1 && y % 2 == 1) {
						return l;
					}
					if(x % 2 == 1 && y % 2 == 0) {
						return l + 2;
					}
					if(x % 2 == 0 && y % 2 == 0) {
						return l + 3;
					} else {
						return l + 1;
					}
				}
				if(x <= maxlin / 2 && y <= maxcol / 2) {
					r = (l + (r - l  + 1) / 4) - 1;
					maxlin /= 2;
					maxcol /= 2;
					continue;
				}
				if(x > maxlin / 2 && y <= mincol / 2) {
					offset = (r - l + 1) / 4;
					l += offset;
					r = l + 2 * offset - 1;
					mincol = maxcol / 2;
					maxlin /= 2;
					continue;
				}
				if(x <= maxlin / 2 && y > mincol / 2) {
					offset = (r - l + 1) / 4;
					l += 2 * offset;
					r = l + 3 * offset - 1;
					minlin = maxlin / 2;
					maxcol /= 2;
					continue;
				}
				if(x > maxlin / 2 && y > mincol / 2) {
					l += 3 * (r - l + 1) / 4;
					minlin = maxlin / 2;
					mincol = maxcol / 2;
					continue;
				}
			}
		}

		public void solve() {
			readInput();
			writeOutput(getAnswer(n, x, y));
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}