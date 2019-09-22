import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
	static class Homework implements Comparable<Homework> {
		public int deadline;
		public int score;

		public Homework() {
			deadline = 0;
			score = 0;
		}

		public int compareTo(Homework h) {
			if (h.deadline > this.deadline)
				return 1;
			else if (h.deadline < this.deadline)
				return -1;
			return 0;
		}
	}

	static class Comp implements Comparator<Homework> {
		public int compare(Homework h1, Homework h2) {
			if (h1.score < h2.score)
				return 1;
			else if (h1.score > h2.score)
				return -1;
			return 0;
		}
	}

	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n;
		Homework[] hws;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				hws = new Homework[n];
				for (int i = 0; i < n; i++) {
					hws[i] = new Homework();
					hws[i].deadline = sc.nextInt();
					hws[i].score = sc.nextInt();
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
			int score = 0;
			Arrays.sort(hws);
			Homework h = hws[0];
			int w = h.deadline;
			score = 0;
			int i = 0;
			PriorityQueue<Homework> q = new PriorityQueue<>(hws.length, new Comp());
			while (w >= 1) {
				while (i < n && w <= hws[i].deadline) {
					q.add(hws[i]);
					i++;
				}
				int s = Objects.requireNonNull(q.poll()).score;
				score += s;
				w--;
			}
			return score;
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
