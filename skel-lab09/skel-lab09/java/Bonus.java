import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Bonus {
    static class Task {
        public static final String INPUT_FILE = "D:\\desktop\\skel-lab09\\skel-lab09\\tests\\Bonus\\1.in";
        public static final String OUTPUT_FILE = "D:\\desktop\\skel-lab09\\skel-lab09\\tests\\Bonus\\1.out";
        public static final int NMAX = 50005;

        int n;
        int m;
        int source;
        int dest;

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
                dest = sc.nextInt();

                for (int i = 1; i <= n; i++) {
                    adj[i] = new ArrayList<>();
                }
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
                for (int i = result.size() - 1; i >= 0; i--) {
                    sb.append(result.get(i)).append(' ');
                }
                sb.append('\n');
                bw.write(sb.toString());
                System.out.println(sb.toString());
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private ArrayList<Integer> getResult() {
            // TODO: Gasiti distantele minime de la nodul source la celelalte noduri
            // folosind Dijkstra pe graful orientat cu n noduri, m arce stocat in adj.
            //	d[node] = costul minim / lungimea minima a unui drum de la source la
            //	nodul node;
            //	d[source] = 0;
            //	d[node] = -1, daca nu se poate ajunge de la source la node.
            // Atentie:
            // O muchie este tinuta ca o pereche (nod adiacent, cost muchie):
            //	adj[x].get(i).node = nodul adiacent lui x,
            //	adj[x].get(i).cost = costul.

            ArrayList<Integer> d = new ArrayList<>();
            PriorityQueue<Integer> queue = new PriorityQueue<>(n);
            ArrayList<Boolean> selected = new ArrayList<>();
            ArrayList<Integer> parent = new ArrayList<>();

            for (int i = 0; i <= n; i++) {
                d.add(Integer.MAX_VALUE);
                selected.add(false);
                parent.add(-1);
            }
            d.set(source, 0);
            queue.add(source);
            while (!queue.isEmpty()) {
                int u = queue.poll();
                selected.set(u, true);
                for (int i = 0; i < adj[u].size(); i++) {
                    int v = adj[u].get(i).node;
                    int costV = adj[u].get(i).cost;
                    if (d.get(v) > d.get(u) + costV) {
                        d.set(v, d.get(u) + costV);
                        queue.add(v);
                        parent.set(v, u);
                    }
                }
            }
            ArrayList<Integer> drum = new ArrayList<>();
            drum.add(dest);
            int u = parent.get(dest);
            while (u != -1) {
                drum.add(u);
                u = parent.get(u);
            }

            return drum;
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
