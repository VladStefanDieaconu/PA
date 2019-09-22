import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		public int n;
		public int k;
		public int[] a = new int[100];
		public int[] b = new int[100];


		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				k = sc.nextInt();
				for (int i = 0; i < n; ++i){
					a[i] = sc.nextInt();
				}

				for (int i = 0; i < n; ++i){
					b[i] = sc.nextInt();
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
			// TODO: Aflati punctajul maxim pe care il puteti obtine
			// planificand optim temele.
			int aux1;
			int result = 0;
			int cnt = 0;
			int[] aux = new int[100];
			for (int i = 0; i < n; ++i) {
				aux[i] = a[i] - b[i];
			}
			for (int i = 0; i < n; i++) {
				for (int j = i; j < n; j++) {
					if (aux[i] > aux[j]) {
						aux1 = aux[i];
						aux[i] = aux[j];
						aux[j] = aux1;

						aux1 = a[i];
						a[i] = a[j];
						a[j] = aux1;

						aux1 = b[i];
						b[i] = b[j];
						b[j] = aux1;
					}
				}
			}

			for (int  i = 0; i < n; i++) {
				System.out.println(aux[i]);

			}

			for (int i = 0; i < n; ++i) {

				if(aux[i] <= 0) {
					result = result + a[i];
					cnt++;
				} else {
					if (cnt < k) {
						result = result + a[i];
						cnt++;
					} else {
						break;
					}
				}
			}

			for(int i = cnt; i < n; ++i) {
				result+=b[i];
			}
			return result;


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
