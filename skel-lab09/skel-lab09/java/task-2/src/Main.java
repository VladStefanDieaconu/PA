import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 50005;

        int n;
        int m;
        int source;

        public class Edge {
            public int node;
            public int cost;

            Edge(int _node, int _cost) {
                node = _node;
                cost = _cost;
            }
        }

        @SuppressWarnings("unchecked")
        ArrayList<Edge> adj[] = new ArrayList[NMAX];

        private void readInput() {
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                        INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();
                source = sc.nextInt();

                for (int i = 1; i <= n; i++)
                    adj[i] = new ArrayList<>();
                for (int i = 1; i <= m; i++) {
                    int x, y, w;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    w = sc.nextInt();
                    adj[x].add(new Edge(y, w));
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(ArrayList<Integer> result) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(
                        OUTPUT_FILE));
                StringBuilder sb = new StringBuilder();
                if (result.size() == 0) {
                    sb.append("Ciclu negativ!\n");
                } else {
                    for (int i = 1; i <= n; i++) {
                        sb.append(result.get(i)).append(' ');
                    }
                    sb.append('\n');
                }
                bw.write(sb.toString());
                System.out.println(sb.toString());
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private ArrayList<Integer> getResult() {
            Boolean auxBool;
            ArrayList<Integer> d = new ArrayList<>();
            for (int i = 0; i <= n; ++i) {
                d.add(Integer.MAX_VALUE);
            }
            d.set(source, 0);
            for (int i = 0; i < n; ++i) {
                auxBool = true;
                for (int j = 1; j <= n; ++j) {
                    for (Edge v : adj[j]) {
                        int costM = d.get(v.node);
                        int costV = d.get(j) + v.cost;
                        if (d.get(j) == Integer.MAX_VALUE) {
                            continue;
                        } else {
                            if(costM > costV) {
                                auxBool = false;
                                d.set(v.node, costV);
                            }
                        }
                    }
                }
                if (auxBool == true) {
                    break;
                } else {
                    continue;
                }
            }
            for (int j = 1; j < n; ++j) {
                for (Edge v : adj[j]) {
                    int costM = d.get(v.node);
                    int costV = d.get(j) + v.cost;
                    if (d.get(j) == Integer.MAX_VALUE) {
                        continue;
                    } else {
                            if(costM > costV) {
                                return new ArrayList<Integer>();
                            }
                        }
                    }
                }

            return d;
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
