import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class Main {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        private final static int MOD = 1000000007;

        int n;
        char[] v;

        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                String s = sc.next().trim();
                s = " " + s;
                v = s.toCharArray();
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
            System.out.println(result);
        }

        private boolean isOperator(char c) {
            return c == '&' || c == '^' || c == '|';
        }

        private boolean isOperand(char c) {
            return c == 'T' || c == 'F';
        }

        private boolean evaluate(char operator, char operand1, char operand2) {
            if (operator == '|') {
                return operand1 == 'T' || operand2 == 'T';
            } else if (operator == '&') {
                if (operand1 == 'T' && operand2 == 'T')
                    return true;
                else
                    return false;
            } else {
                if (operand1 == operand2)
                    return false;
                else
                    return true;
            }
        }

        private int getResult() {
            // TODO: Calculati numarul de moduri in care se pot aseza
            // parantezele astfel incat rezultatul expresiei sa fie TRUE.
            // Numarul de moduri se va calcula modulo MOD (1000000007).
            int[] simboluri = new int[n];
            int[] op = new int[n];
            int n1 = 0;
            int n2 = 0;
            for (int i = 1; i <= n; i++) {
                if (isOperand(v[i])) {
                    simboluri[++n1] = v[i];
                } else if (isOperator(v[i])) {
                    op[++n2] = v[i];
                }
            }
            int[][] D_T = new int[n1 + 1][n1 + 1];
            int[][] D_F = new int[n1 + 1][n1 + 1];

            for (int i = 1; i <= n1; i++) {
                if (simboluri[i] == 'T')
                    ++D_T[i][i];
                else
                    ++D_F[i][i];


            }
            for (int gap = 2; gap <= n1; gap++) {
                int i = 1;
                int j = gap;
                while (j <= n1) {
                    D_T[i][j] = 0;
                    D_F[i][j] = 0;
                    for (int g = 0; g < gap; g++) {
                        int k = i + g;

                        if (op[k] == '&') {
                            D_T[i][j] = (int) (((long) D_T[i][j] % MOD) + ((long) ((long)(D_T[i][k] % MOD) * (long)((long)D_T[k + 1][j] % MOD))) % MOD);
                            D_F[i][j] = (int) (((long) D_F[i][j] % MOD) + ((long) ((long)(D_T[i][k] % MOD + (long)(D_F[i][k]) % MOD) % MOD)
                                    * (long)(((long)(D_T[k + 1][j]) % MOD + (long)((long)D_F[k + 1][j]) % MOD)) % MOD) % MOD -
                                    (long) ((long)D_T[i][k] % MOD * (long)((long)D_T[k + 1][j] % MOD)) % MOD) % MOD;
                        }
                        if (op[k] == '|') {
                            D_F[i][j] =  (int) (((long) D_F[i][j]%MOD +  ((long) ((((long)((long)D_F[i][k]) % MOD) * (long)((long)D_F[k + 1][j]) % MOD))) % MOD));
                            D_T[i][j] = (int) (((long)  D_T[i][j]%MOD + ((long) ((((long)((long)D_T[i][k]%MOD + D_F[i][k]%MOD)) % MOD) * (long)
                                    ((long)((long)D_T[k + 1][j]%MOD + (long)D_F[k + 1][j]%MOD) % MOD)) % MOD - (long) ((long)D_F[i][k]%MOD * (long)D_F[k + 1][j]%MOD) % MOD) % MOD));
                        }
                        if (op[k] == '^') {
                            D_T[i][j] = (int) (((long)  D_T[i][j]%MOD + ((long) ((long)D_F[i][k] % MOD * (long)D_T[k + 1][j] % MOD) % MOD +
                                    ((long) ((long)D_T[i][k]%MOD * (long)D_F[k + 1][j]%MOD) % MOD) % MOD) % MOD));
                            D_F[i][j] = (int) (((long) D_F[i][j]%MOD + ((long) ((long)D_T[i][k] % MOD * (long)D_T[k + 1][j] % MOD) % MOD +
                                    (long) ((long)D_F[i][k]%MOD * (long)D_F[k + 1][j]%MOD) % MOD) % MOD));
                        }
                    }
                    i++;
                    j++;
                }
            }
            return (D_T[1][n1] % MOD);
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
