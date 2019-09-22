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
  void bkt(vector<vector<int> > &all, vector<int> &sol) {
    if (sol.size() == n)
      all.push_back(sol);
    else {
      all.push_back(sol);
      int i;
      if (sol.size() == 0)
        i = 1;
      else
        i = sol.back() + 1;
      for (; i <= n; i++) {
        sol.push_back(i);
        bkt(all, sol);
        sol.pop_back();
      }
    }

  }
	vector<vector<int> > get_result() {
		vector<vector<int> > all;
    vector<int> sol;
    bkt(all, sol);
		/*
		TODO: Construiti toate submultimile multimii {1, ..., N}.

		Pentru a adauga o noua submultime:
			vector<int> submultime;
			all.push_back(submultime);
		*/

		return all;
	}

	void print_output(vector<vector<int> > result) {
		ofstream fout("out");
		fout << result.size() << '\n';
		for (int i = 0; i < (int)result.size(); i++) {
			for (int j = 0; j < (int)result[i].size(); j++) {
				fout << result[i][j] <<
					(j + 1 != result[i].size() ? ' ' : '\n');
			}
		}
		fout.close();
	}
};

int main() {
	Task task;
	task.solve();
	return 0;
}