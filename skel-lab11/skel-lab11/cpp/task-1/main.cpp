#include <fstream>
#include <vector>
#include <algorithm>
#include <queue>
#include <cstring>
using namespace std;

#define MIN(a,b) ((a) < (b) ? (a) : (b))
#define INF 99999999

const int kNmax = 1005;

class Task {
 public:
	void solve() {
		read_input();
		print_output(get_result());
	}

 private:
	int n;
	int m;
	vector<int> adj[kNmax];
  	int C[kNmax][kNmax];
	bool visited[kNmax];
    int parent[kNmax];

	void read_input() {
		ifstream fin("in");
		fin >> n >> m;

		memset(C, 0, sizeof C);
		for (int i = 1, x, y, z; i <= m; i++) {
			fin >> x >> y >> z;
			adj[x].push_back(y);
			adj[y].push_back(x);
      C[x][y] += z;
		}
		fin.close();
	}

    inline bool BFS() {
        queue<int> my_queue;
        int front_of_Q;

		// fill things, I learned it at SD, staying 3 hours on a bug
		// problem?! I used fill(start, size, 0) :)))))))))))))))
        fill(visited, visited + sizeof(visited), 0);

		// push first to the queue
		int root = 1;
        my_queue.push(root);
        visited[root] = true;

		// if you can still front, do that. Dont go BACK! :D
        while (!my_queue.empty()) {
            front_of_Q = my_queue.front();
            my_queue.pop();

            if (front_of_Q != n) {
                for (auto i : adj[front_of_Q]) {
                    if (!visited[i] && C[front_of_Q][i]) {
                        visited[i] = true;
                        parent[i] = front_of_Q;
                        my_queue.push(i);
                    }
                }
            }
        }

        return visited[n];
    }

	int get_result() {
		/*
		TODO: Calculati fluxul maxim pe graful orientat dat.
		Sursa este nodul 1.
		Destinatia este nodul n.

		In adj este stocat graful neorientat obtinut dupa ce se elimina orientarea
		arcelor, iar in C sunt stocate capacitatile arcelor.
		De exemplu, un arc (x, y) de capacitate z va fi tinut astfel:
		C[x][y] = z, adj[x] contine y, adj[y] contine x.
		*/

		int max_flow = 0;
		int curent_flow;

        while ( BFS() ) {
            for (auto i : adj[n]) {
                if (visited[i]) {
					parent[n] = i;
                    curent_flow = 1<<30; // my kind of INF, did not used include limits

					int crtNode = n;
                    while (crtNode != 1) {
                        curent_flow = MIN(curent_flow, C[parent[crtNode]][crtNode]);
						crtNode = parent[crtNode];
                    }

                    if (curent_flow == 0) {
						continue; // if flow is 0 then it want not initialised yet, so go to next iter
					} else {
						int node = n;
						while (node != 1) {
                            C[parent[node]][node] = C[parent[node]][node] - curent_flow;
                            C[node][parent[node]] = C[node][parent[node]] + curent_flow;
							node = parent[node];
                        }

                        max_flow = max_flow + curent_flow;
                    }
                }
            }
        }

		return max_flow;
	}

	void print_output(int result) {
		ofstream fout("out");
		fout << result << '\n';
		fout.close();
	}
};

int main() {
	Task *task = new Task();
	task->solve();
	delete task;
	return 0;
}
