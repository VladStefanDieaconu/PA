import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Bonus2 {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";
        public static final int NMAX = 100005; // 10^5

        int n;
        int m;

        @SuppressWarnings("unchecked")
        ArrayList<Integer> adj[] = new ArrayList[NMAX];
        @SuppressWarnings("unchecked")
        ArrayList<Integer> in[] = new ArrayList[NMAX];

        private void readInput() {
            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                        INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();

                for (int i = 1; i <= n; i++) {
                    adj[i] = new ArrayList<>();
                    in[i] = new ArrayList<>();
                }
                for (int i = 1; i <= m; i++) {
                    int x, y;
                    x = sc.nextInt();
                    y = sc.nextInt();
                    adj[x].add(y);
                    in[y].add(x);
                }
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(ArrayList<Integer> result) {
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
                        OUTPUT_FILE)));
                for (int i = 0; i < result.size(); i++) {
                    pw.printf("%d ", result.get(i));
                    System.out.print(result.get(i) + " ");
                }
                pw.printf("\n");
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private ArrayList<Integer> topoSort() {
            ArrayList<Integer> topsort = new ArrayList<>();
            Stack<Integer> stack = new Stack<>();

            for (int u = 1; u <= n; u++) {
                if (in[u].isEmpty()) {
                    stack.add(u);
                }
            }
            while (!stack.empty()) {
                int u = stack.pop();
                topsort.add(u);
                for (int v : adj[u]) {
                    in[v].remove(new Integer(u));
                    if (in[v].isEmpty()) {
                        stack.push(v);
                    }
                }
            }
            return topsort;
        }

        private ArrayList<Integer> getResult() {
            ArrayList<Integer> topsort = new ArrayList<>();
            Stack myStack = new Stack();
            Vector<Integer> dist = new Vector<Integer>();

            // here we mark all verticles as not visited
            Vector<Boolean> visited = new Vector<Boolean>();

            for(int i = 0; i < n; ++i) {
                visited.add(false);
            }

            // now in topsort we have have a topological sorted graph
            for(int i = 0; i < n; ++i) {
                if(visited.get(i) == false) {
                    topsort = topoSort();
                }
            }

            for(int i = 0; i < n; ++i) {
                dist.add(NMAX);
            }

            int source = 0;

            dist.set(source, 0);

            // Process vertices in topological order
            while (myStack.empty() == false)
            {
                // Get the next vertex from topological order
                int u = (int)myStack.pop();

                // Update distances of all adjacent vertices
                if (dist[u] != )
                {
                    auto it = adj[u].iterator();
                    while (it.hasNext())
                    {
                        AdjListNode i= it.next();
                        if (dist[i.getV()] > dist[u] + i.getWeight())
                            dist[i.getV()] = dist[u] + i.getWeight();
                    }
                }
            }

            // Print the calculated shortest distances
            for (int i = 0; i < n; i++)
            {
                if (dist.get(i) == -100000)
                    System.out.print( "INF ");
                else
                    System.out.print( dist.get(i)  + " ");
            }






            return topsort;
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
