// Copyright (C) 2019 Vlad-Stefan Dieaconu 321 CA
#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <algorithm>
#include <string>
#include <cctype>
#include <map>
#include <climits>

using namespace std;

#define NR_VEC 4  // avem 4 vecini, pt ca sunt 4 coordonate in care ma misc

// functie care verifica daca pozitia pe care vreua sa ma mut este valida
int miscare_valida(int N, int M, int matrice[][101], int lin, int col,
                   int vizitat[][101]) {
  return (lin >= 0) && (lin < N) &&  // verific ca linia nu a iesit din matrice
         (col >= 0) && (col < M) &&  // verific acelasi lucru pt coloana
         !vizitat[lin][col];         // verific ca elementul nu a deja vizitat
}

// functie care realizeaza un DSF, se plimba pe harta pentru a gasi portiunea
// de arie maxima care respecta constrangerile impuse
void DFS(int N, int M, int K, int &min_pres, int &max_pres, int &arie,
         int matrice[][101], int i, int j, int vizitat[][101]) {
  // ma plimb pe harta in cele 4 directii: N, S, E sau V
  static int vecini_lin[] = {-1, 0, 0, 1};
  static int vecini_col[] = {0, -1, 1, 0};

  // marchez aceasta celula ca fiind vizitata
  vizitat[i][j] = 1;

  // ma duc in toate directiile posibile: N, S, V sau E
  for (int k = 0; k < NR_VEC; ++k) {
    // verificam daca miscarea este valida
    if (miscare_valida(N, M, matrice, i + vecini_lin[k], j + vecini_col[k],
                       vizitat)) {
      // valori auxiliare folosite in verificare, daca mutarea e valida, voi
      // modifica dupa valorile efective ale min si max
      int min_nou = min_pres;
      int max_nou = max_pres;

      // verificam daca elementul este un minim sau un maxim, pt ca daca este
      // formula de verificare a validitatii trebuie sa tina cont si de el
      if (matrice[i + vecini_lin[k]][j + vecini_col[k]] < min_nou) {
        min_nou = matrice[i + vecini_lin[k]][j + vecini_col[k]];
      } else if (matrice[i + vecini_lin[k]][j + vecini_col[k]] > max_nou) {
        max_nou = matrice[i + vecini_lin[k]][j + vecini_col[k]];
      }

      // daca respecta constrangerile, atunci mergem mai departe
      if (max_nou - min_nou <= K) {
        min_pres = min_nou;  // updatez val minimului presupus
        max_pres = max_nou;  // updatez val maximului presupus
        arie++;              // un nou element adaugat la arie
        DFS(N, M, K, min_pres, max_pres, arie, matrice, i + vecini_lin[k],
            j + vecini_col[k], vizitat);  // trecem la urmatorul element
      }
    }
  }
}

// functie ce calculeaza aria cea mai mare care respecta conditiile impuse
int calculeaza_aria(int N, int M, int K, int matrice[][101]) {
  // initializam aria maxima ca fiind -INF
  int max_arie = INT_MIN;

  // pornim cate o parcurgere DFS din fiecare punct
  for (int i = 0; i < N; ++i) {
    for (int j = 0; j < M; ++j) {
      // pentru ca initial vom avea o celula, din care pornim DFS
      int arie = 1;

      // initializez matricea de vizitat pt fiecare cautare in parte
      int vizitat[101][101] = {0};  // valoarea lui N si M e maxim 101

      // minimum si maximul presupus sunt reprezentate de primul element, pt
      // ca initial doar elementul asta il am in aria mea
      int min_pres = matrice[i][j];
      int max_pres = matrice[i][j];

      // pornesc DFS-ul
      DFS(N, M, K, min_pres, max_pres, arie, matrice, i, j, vizitat);

      // verific daca aria returnata de DFS este mai mare ca maximul meu
      if (arie > max_arie) {
        // daca e mai mare, o consider pe asta ca fiind aria maxima
        max_arie = arie;
      }
    }
  }
  // returnez aria maxima care respecta conditiile impuse
  return max_arie;
}

int main() {
  // deschid fisierele din care citesc
  ifstream citire("p2.in");
  ofstream scriere("p2.out");

  // variabilele din enunt
  int N, M, K;
  int matrice[101][101];  // N si M sunt maxim 100, se specifica in enunt

  // citesc din fisier valorile lui N, M si K
  citire >> N >> M >> K;

  // citesc din fisier cate M numere pt fiecare linii (N linii in total)
  for (int i = 0; i < N; ++i) {
    for (int j = 0; j < M; ++j) {
      citire >> matrice[i][j];
    }
  }

  // am terminat cu cititul, inchid fisierul din care citesc
  citire.close();

  // calculez aria maxima
  scriere << calculeaza_aria(N, M, K, matrice);

  // inchid fisierul de scriere si returnez success
  scriere.close();
  return 0;
}
