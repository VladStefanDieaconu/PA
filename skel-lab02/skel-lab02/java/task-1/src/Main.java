import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Obj {
		public int weight;
		public int price;

		public Obj() {
			weight = 0;
			price = 0;
		}
	};

	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n, w;
		Obj[] objs;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				w = sc.nextInt();
				objs = new Obj[n];
				for (int i = 0; i < n; i++) {
					objs[i] = new Obj();
					objs[i].weight = sc.nextInt();
					objs[i].price = sc.nextInt();
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(double result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%.4f\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private double getResult() {
			// TODO: Aflati profitul maxim care se poate obtine cu
			// obiectele date.
			double result = 0;
			Double[] raports = new Double[objs.length];
			for (int i = 0; i < objs.length; i++) {
				raports[i] = 1.0 * objs[i].price /  objs[i].weight;
			}
			int i, j;
			double aux;
			Obj aux1;
			for (i = 0; i < raports.length; i++) {
				for (j = i; j < raports.length; j++) {
					if (raports[i] < raports[j]) {
						aux1 = objs[i];
						objs[i] = objs[j];
						objs[j] = aux1;
						aux = raports[i];
						raports[i] = raports[j];
						raports[j] = aux;
					}
				}
			}
			i = 0;
			while (w > 0 && i < objs.length) {
				if (w >= objs[i].weight) {
					w = w - objs[i].weight;
					result = result + objs[i].price;
				} else if (w < objs[i].weight) {
					result = result + (double) w * raports[i];
					w = 0;
				}
				i++;
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
