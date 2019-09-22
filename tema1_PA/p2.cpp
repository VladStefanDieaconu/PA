// Copyright (C) 2019 Vlad-Stefan Dieaconu 321 CA
#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <algorithm>
#include <string>
#include <cctype>
using namespace std;

int main () {
    // files to read and write to
    ifstream read_from("p2.in");
    ofstream write_to("p2.out");

    // how many numbers we have
    int N = 0, K = 0;
    int aux = 0;
    vector<int> list_of_nums;

    // read values of N and K (have the exact name as in the
    // homework papers)
    read_from >> N;
    read_from >> K;

    // the matrix for dinamic programming method
    int d_matrix[K + 1][N + 1];

    // initialize matrix, because this is not java and we need this :)
    for (int i = 0; i < K + 1; ++i) {
        for (int j = 0; j < N + 1; ++j) {
            d_matrix[i][j] = 0;
        }
    }

    // save value of N, maybe I need it later
    int len = N;

    // read all nums from file
    while (!read_from.eof() && (len != 0)) {
        read_from >> aux;
        --len;
        list_of_nums.push_back(aux);
    }

    // sort nums a[i] < a[i+1]
    sort(list_of_nums.begin(), list_of_nums.end(), greater<int>());

    // to know whos turn it is.
    int parity = 0;

	// get the first line of the dinamic programming matrix
	for (int i = 1; i < N + 1; ++i) {
		// take into consideration previous score
		d_matrix[0][i] = d_matrix[0][i - 1];
		// take into consideration whose turn is to play
		if (parity == 0) { // tuzgu
            d_matrix[0][i] += list_of_nums[i - 1];
            parity = 1; // swap parity
        } else { // ritza
            d_matrix[0][i] -= list_of_nums[i - 1];
            parity = 0; // swap parity
        }
    }

    // tuzgu's turn again
    parity = 0;

    // max element of list of nums, using * because f returns iterator
    int max_of_nums= *max_element(list_of_nums.begin(), list_of_nums.end());

    // compute the rest of the dinamic programming matrix
    for (int i = 1; i < K + 1; ++i) {
        for (int j = 1; j < N + 1; ++j) {
            if (i == j - 1) {
                d_matrix[i][j] = max_of_nums;
            } else if (parity == 0) { // tuzgu
                // whats the max score between the case when we substract i
                // elements from the first j elements and I choose the element
                // from the position j-1, and the case when we substract i-1
                // elems from the first j-1 elements.
                d_matrix[i][j] = max(d_matrix[i][j - 1] + list_of_nums[j-1],
                    d_matrix[i - 1][j - 1]);
                parity = 1;
            } else { // ritza
                // same thing, but because it's for ritza, we substract instead
                // of adding the elem at the position j-1
                d_matrix[i][j] = max(d_matrix[i][j - 1] - list_of_nums[j-1],
                    d_matrix[i - 1][j - 1]);
                parity = 0;
            }
            if (j < i + 1) {
                d_matrix[i][j]  = 0;
            }
        }
    }

    // write to file the answear
    write_to << d_matrix[K][N];
    write_to << "\n";

    // close files
    read_from.close();
    write_to.close();
    return 0;
}
