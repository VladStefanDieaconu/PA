#include <fstream>
#include <vector>
#include <algorithm>
using namespace std;

class Task {
 public:
	void solve() {
		read_input();
		print_output(get_result());
	}

 private:
	int n;

	void read_input() {
		ifstream fin("in");
		fin >> n;
		fin.close();
	}

	int verify(vector<int>& sol, int k) {
		for (int i = 1; i < k; i++) {
		 	if (sol[i] == sol[k] || abs(k - i) == abs(sol[k] - sol[i]) ) 	{
				return 0;
			}
		}
	return 1;
	}

  void bkt(vector<int> &sol_aux, int k, vector<int> &sol, int &ok) {
  	if (ok) {
  		return;
  	}
    if (k == n + 1) {
      if(ok == 0) {
        sol = sol_aux;
        ok = 1;
      }
    } else {
      for (int i = 1; i <= n; i++) {
        sol_aux[k] = i;
        if (verify(sol_aux, k) == 1)
          bkt(sol_aux, k+1, sol, ok);
      }
    }
  }
	vector<int> get_result() {
		vector<int> sol(n + 1, 0);
    	vector<int> sol_aux(n + 1, 0);
    	int ok = 0;
    	bkt(sol_aux, 1, sol, ok);
		return sol;
	}

	void print_output(vector<int> result) {
		ofstream fout("out");
		for (int i = 1; i <= n; i++) {
			fout << result[i] << (i != n ? ' ' : '\n');
		}
		fout.close();
	}
};

int main() {
	Task task;
	task.solve();
	return 0;
}