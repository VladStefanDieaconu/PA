import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 200005;

        int n;
        int m;
        int[] parent;
        int[] size;

        public class Edge implements Comparable<Edge> {
            public int node1;
            public int node2;
            public int cost;

            Edge(int _node1, int _node2, int _cost) {
                node1 = _node1;
                node2 = _node2;
                cost = _cost;
            }

            public int compareTo(Edge edge) {
                return this.cost - edge.cost;
            }
        }

        @SuppressWarnings("unchecked")
        ArrayList<Edge> edges = new ArrayList<>();

        private void readInput() {
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                        INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();

                for (int i = 1; i <= m; i++) {
                    int x, y, w;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    w = sc.nextInt();
                    edges.add(new Edge(x, y, w));
                }
                parent = new int[n + 1];
                size = new int[n + 1];
                for (int i = 1; i <= n; i++) {
                    parent[i] = i;
                    size[i] = 1;
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
//				System.out.println(result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private int findRoot(int node) {
            if (node == parent[node]) {
                return node;
            }
            return parent[node] = findRoot(parent[node]);
        }

        private void mergeForests(int root1, int root2) {
            if (size[root1] <= size[root2]) {
                size[root2] += size[root1];
                parent[root1] = root2;
            } else {
                size[root1] += size[root2];
                parent[root2] = root1;
            }
        }

        private int getResult() {
			/*
			TODO: Calculati costul minim al unui arbore de acoperire
			folosind algoritmul lui Kruskal.
			*/
            int costMinim = 0;
            Collections.sort(edges);
            for (Edge edge : edges) {
                if (findRoot(edge.node1) != findRoot(edge.node2)) {
                    mergeForests(findRoot(edge.node1), findRoot(edge.node2));
                    costMinim += edge.cost;
                }
            }
            return costMinim;
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
