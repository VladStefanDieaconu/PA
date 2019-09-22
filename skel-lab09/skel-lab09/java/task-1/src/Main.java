import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
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
                for (int i = 1; i <= n; i++) {
                    sb.append(result.get(i)).append(' ');
                }
                sb.append('\n');
                bw.write(sb.toString());
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private ArrayList<Integer> getResult() {
            ArrayList<Boolean> selected = new ArrayList<>();
            ArrayList<Integer> myArray = new ArrayList<>();

            for (int i = 0; i < n + 1; ++i) {
                myArray.add(Integer.MAX_VALUE);
                selected.add(false);
            }
            myArray.set(source, 0);
            PriorityQueue<Integer> priorityQ = new PriorityQueue<>(n);
            int aux;
            priorityQ.add(source);

            while (priorityQ.isEmpty() == false) {
                aux = priorityQ.poll();
                selected.set(aux, true);
                for (int i = 0; i < adj[aux].size(); ++i) {
                    int v = adj[aux].get(i).node;
                    int cost1 = adj[aux].get(i).cost;
                    int cost2 = myArray.get(v);
                    int cost3 = myArray.get(aux) + cost1;
                    if (!(cost2 < cost3)) {
                        myArray.set(v, cost3);
                        priorityQ.add(v);
                    }
                }
            }
            return myArray;
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
