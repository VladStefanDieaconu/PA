import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    static class Task {
        //        public static final String INPUT_FILE = "C:\\Users\\ursud\\Desktop\\An_II_Sem2\\PA\\skel-lab10\\skel-lab10\\tests\\task-2\\5.in";
//        public static final String OUTPUT_FILE = "C:\\Users\\ursud\\Desktop\\An_II_Sem2\\PA\\skel-lab10\\skel-lab10\\tests\\task-2\\out";
        public static final int NMAX = 200005;
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";

        int n;
        int m;

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

                for (int i = 1; i <= n; i++)
                    adj[i] = new ArrayList<>();
                for (int i = 1; i <= m; i++) {
                    int x, y, w;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    w = sc.nextInt();
                    adj[x].add(new Edge(y, w));
                    adj[y].add(new Edge(x, w));
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
                System.out.println(result);
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        class MyComparator implements Comparator<Edge> {

            // Overriding compare()method of Comparator
            // for descending order of cgpa
            public int compare(Edge s1, Edge s2) {
                return s1.cost - s2.cost;
            }
        }

        private int getResult() {
			/*
			TODO: Calculati costul minim al unui arbore de acoperire
			folosind algoritmul lui Prim.
			*/
            int costMinim = 0;
            ArrayList<Integer> d = new ArrayList<>();
            PriorityQueue<Edge> queue = new PriorityQueue<>(n, new MyComparator());
            ArrayList<Boolean> selected = new ArrayList<>();

            for (int i = 0; i <= n; i++) {
                d.add(Integer.MAX_VALUE);
                selected.add(false);
            }
            d.set(1, 0);
            queue.add(new Edge(1, 0));
            while (!queue.isEmpty()) {
                Edge u = queue.poll();
                selected.set(u.node, true);
                for (int i = 0; i < adj[u.node].size(); i++) {
                    Edge v = adj[u.node].get(i);
                    if (d.get(v.node) > v.cost && !selected.get(v.node)) {
                        d.set(v.node, v.cost);
                        queue.add(v);

                    }
                }
            }
            for (int i = 0; i < d.size(); i++) {
                if (d.get(i) != Integer.MAX_VALUE)
                    costMinim += d.get(i);
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
