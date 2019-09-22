import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int base;
		int exponent;
		int mod;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				base = sc.nextInt();
				exponent = sc.nextInt();
				mod = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(long result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int fastPow(int base, int exponent, int mod) {
			// TODO: Calculati (base^exponent) modulo mod in O(log exponent).
            int trebuieInm = 0;

			if(exponent == 0) {
			    return 1;
            } else if(exponent == 1) {
			    return base%mod;
            }

            if(base == 1) {
                return 1;
            }
            return computeFastPow(1, base, exponent/2, mod);


		}

        private int computeFastPow(int res, int base, int exponent, int mod) {
            int trebuieInm = 0;
		    if(exponent%2 == 1) {
                trebuieInm = -1;
            }
		    if(exponent > 1) {
                res = computeFastPow(res, base, exponent/2, mod);
            }

            if(trebuieInm == -1) {
                res = (res* (base%mod)) % mod;
            }

		    return (int) (((res) % mod) * (res % mod)) % mod);
        }


		public void solve() {
			readInput();
			long result = fastPow(base, exponent, mod);
			writeOutput(result);
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
