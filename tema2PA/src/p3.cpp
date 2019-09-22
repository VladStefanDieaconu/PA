#include <fstream>
#include <vector>
#include <algorithm>
#include <queue>
#include <utility>
#include <set>
#include <iostream>
#include <iterator>
#include <string>
#include <cctype>
#include <map>

using namespace std;

const int kNmax = 100;

class Muchie {
  public:
  int cost;
  int tip;
};

int main() {
  // deschid fisierele din care citesc
  ifstream citire("p3.in");
  ofstream scriere("p3.out");

  // variabilele din enunt
  int N, M, T;
  citire >> N >> M >> T;

  // array de vectori in care retin muchiile
  vector<pair<int, Muchie>> muchii[kNmax];

  // matricea in care retin penalitatile
  int penalitati[kNmax][kNmax];

  for(int i = 0; i < M; ++i) {
    // variabile auxiliare folosite doar la citire
    Muchie muchie_aux;
    int nod1;
    int nod2;

    // citesc datele
    citire >> nod1 >> nod2 >> muchie_aux.cost >> muchie_aux.tip;

    // pun in vector datele, cu mentiunea ca fiind muchii in ambele parti,
    // adaug de 2 ori, cate o muchie care porneste din fiecare din cele 2 noduri
    muchii[nod1].push_back(make_pair(nod2, muchie_aux));
    muchii[nod2].push_back(make_pair(nod1, muchie_aux));
  }

  // citesc matricea de penalitati
  for(int i = 0; i < T; ++i) {
    for(int j = 0; j < T; ++j) {
      citire >> penalitati[i][j];
    }
  }

  int crtNode;
  vector<int> d(n + 1, -1);
  auto cmp = [&d](int x, int y) { return d[x] > d[y]; };
  priority_queue<int, vector<int>, decltype(cmp)> pq(cmp);
  d[source] = 0;
  pq.emplace(source);

  for (; !pq.empty(); pq.pop()) {
    crtNode = pq.top();

    for (const auto& nextNode : adj[crtNode]) {
      if (d[nextNode.first] == -1 ||
          d[nextNode.first] > nextNode.second + d[crtNode]) {
        d[nextNode.first] = nextNode.second + d[crtNode];
        pq.emplace(nextNode.first);
      }
    }
  }



  return 0;
}