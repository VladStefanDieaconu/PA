#include <fstream>
#include <vector>
#include <algorithm>
#include <iostream>
using namespace std;

class Task {
 public:
	void solve() {
		read_input();
		print_output(get_result());
	}

 private:
	int n, k;
    vector<vector<int> > all;

	void read_input() {
		ifstream fin("in");
		fin >> n >> k;
		fin.close();
	}

	bool sol (int K) {
	    return (k-1) == K;
	}

    bool valid(int K, vector<int> &aranjament) {
        bool ok = true;
        for(int i = 0; i < K; i++) {
            if(aranjament[K] == aranjament[i]) {
                ok = false;
                break;
            }
        }
        return ok;
    }

	void bkt(int K, vector<int> &aranjament) {
        for (int val = 1; val <= n; val++) {
            aranjament[K] = val;
            if (valid(K, aranjament)) {
                if (sol(K)) {
                    all.push_back(aranjament);
                } else {
                    bkt(K + 1, aranjament);
                }
            }
        }
	}

	vector<vector<int> > get_result() {
		/*
		TODO: Construiti toate aranjamentele de N luate cate K ale
		multimii {1, ..., N}.

		Pentru a adauga un nou aranjament:
			vector<int> aranjament;
			all.push_back(aranjament);
		*/

		vector<int> aranjament(k);
        bkt(0, aranjament);
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
