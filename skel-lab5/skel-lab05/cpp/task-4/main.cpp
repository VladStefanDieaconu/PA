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
	int n, k;
	string caractere;
	vector<int> freq;

	void read_input() {
		ifstream fin("in");
		fin >> n >> k;
		fin >> caractere;
		caractere = " " + caractere; // Adaugare element fictiv -
									 // indexare de la 1.
		freq.push_back(-1); // Adaugare element fictiv - indexare de la 1.
		for (int i = 1, f; i <= n; i++) {
			fin >> f;
			freq.push_back(f);
		}
		fin.close();
	}
  void bkt(	vector<vector<char> > &all, vector<char> &sol, int remain, int consec) {
    if (consec > k)
      return;
    if (remain == 0) {
      all.push_back(sol);
      return;
    } else {
      for (int i = 1; i <= n; i++) {
        if (freq[i] > 0) {
          freq[i]--;
          if (sol.size() != 0) {
            if (caractere[i] == sol.back()) {
              sol.push_back(caractere[i]);
              bkt(all, sol, remain - 1, consec + 1);
            } else {
              sol.push_back(caractere[i]);
              bkt(all, sol, remain - 1, 1);
            }
        } else {
          sol.push_back(caractere[i]);
          bkt(all, sol, remain - 1, 1);
        }
          sol.pop_back();
          freq[i]++;
        }
      }
    }
  }
	vector<vector<char> > get_result() {
		vector<vector<char> > all;
    vector<char> sol;
    int nr = 0;
    for (int i = 1; i <=n; i++)
      nr += freq[i];
    bkt(all, sol, nr, 0);
		/*
		TODO: Construiti toate sirurile cu caracterele in stringul caractere
		(indexat de la 1 la n), si frecventele in vectorul freq (indexat de la
		1 la n), stiind ca nu pot fi mai mult de K aparitii consecutive ale
		aceluiasi caracter.

		Pentru a adauga un nou sir:
			vector<char> sir;
			all.push_back(sir);
		*/

		return all;
	}

	void print_output(vector<vector<char> > result) {
		ofstream fout("out");
		fout << result.size() << '\n';
		for (int i = 0; i < (int)result.size(); i++) {
			for (int j = 0; j < (int)result[i].size(); j++) {
				fout << result[i][j];
			}
			fout << '\n';
		}
		fout.close();
	}
};

int main() {
	Task task;
	task.solve();
	return 0;
}