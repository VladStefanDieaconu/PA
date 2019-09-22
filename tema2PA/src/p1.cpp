// Copyright (C) 2019 Vlad-Stefan Dieaconu 321 CA
#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <algorithm>
#include <string>
#include <cctype>
#include <map>

using namespace std;

// predicat folosit pentru sortarea perechilor dupa al doilea membru
bool sorteaza_perechi(const pair<int, int> &a, const pair<int, int> &b) {
  return (a.second < b.second);
}

int main() {
  // deschid fisierele din care citesc
  ifstream citire("p1.in");
  ofstream scriere("p1.out");

  // variabilele din enunt
  int N;
  int aux;  // variabila auxiliara folosita la citire
  vector<pair<int, int>> vector_nod_distanta;
  map<int, vector<int>> distanta_la_noduri;

  // citesc datele (vectorul de distante) din fisier
  citire >> N;
  for (int i = 0; i < N; ++i) {
    citire >> aux;
    // memorez faptul ca nodul i se afla la distanta AUX de root
    vector_nod_distanta.push_back(make_pair(i, aux));
  }

  // inchid fisierul de citire
  citire.close();

  // sortez vectorul dupa al doilea element
  sort(vector_nod_distanta.begin(), vector_nod_distanta.end(),
       sorteaza_perechi);

  // folosit ca sa returnam fals, in caz ca avem mai multe root-uri
  int nr_of_roots = 0;

  for (int i = 0; i < N; ++i) {
    // verificam cati root avem
    if (vector_nod_distanta.at(i).second == 0) {
      nr_of_roots++;
    }

    // daca avem mai mult de 1 root, ma opresc
    if (nr_of_roots > 1) {
      scriere << -1;
      return 0;
    }

    // daca lipeste un nivel ma opresc
    if (i != (N - 1) &&
        vector_nod_distanta.at(i + 1).second !=
            vector_nod_distanta.at(i).second &&
        vector_nod_distanta.at(i + 1).second !=
            (vector_nod_distanta.at(i).second + 1)) {
      scriere << -1;
      return 0;
    }

    // verific daca exista deja cheia, daca nu o creez si adaug
    if (distanta_la_noduri.count(vector_nod_distanta.at(i).second) > 0) {
      distanta_la_noduri.at(vector_nod_distanta.at(i).second)
          .push_back(vector_nod_distanta.at(i).first);
    } else {
      vector<int> aux;
      aux.push_back(vector_nod_distanta.at(i).first);
      distanta_la_noduri.insert(
          pair<int, vector<int>>(vector_nod_distanta.at(i).second, aux));
    }
  }

  // distanta maxima in graf (adica adancimea maxima)
  int max_distanta =
      vector_nod_distanta.at(vector_nod_distanta.size() - 1).second;

  // numarul de muchii
  scriere << N - 1;

  // scriu capetele muchiilor
  for (int i = 1; i <= max_distanta; ++i) {
    vector<int> precedent = distanta_la_noduri.at(i - 1);
    vector<int> curent = distanta_la_noduri.at(i);
    for (int j = 0; j < (int)curent.size(); ++j) {
      // mai adaug 1 la scriere pt ca asa vrea checkerul
      // root-ul este 0 la citire, dar la afisare le identam de la 1
      scriere << endl
              << precedent.at(j % precedent.size()) + 1 << " "
              << curent.at(j) + 1;
    }
  }

  // inchid fisierul de scriere si returnez success
  scriere.close();
  return 0;
}