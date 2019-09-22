import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n, m;
		int[] dist;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				m = sc.nextInt();
				dist = new int[n];
				for (int i = 0; i < n; i++) {
					dist[i] = sc.nextInt();
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {
			// TODO: Aflati numarul minim de opriri necesare pentru
			// a ajunge la destinatie.
			int i = 0;
			int count = 0;
			int kmParcusi = dist[0];
			if (dist[0] >= m) count++;
			while (i < n - 2) {
				kmParcusi += (dist[i + 1] - dist[i]);
				if (kmParcusi >= m) {
					count++;
					if (kmParcusi == m) {
						kmParcusi = 0;
					} else {
						kmParcusi = dist[i + 1] - dist[i];
					}
				}
				i++;
			}
			return count;
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
