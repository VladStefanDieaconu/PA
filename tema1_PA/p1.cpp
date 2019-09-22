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
    ifstream read_from("p1.in");
    ofstream write_to("p1.out");

    // sum of each player
    int tuzgu = 0;
    int rita = 0;

    // how many numbers we have
    int len = 0;
    int aux = 0;
    vector<int> list_of_nums;

    read_from >> len;

    // read all nums
    while (!read_from.eof() && (len != 0)) {
        read_from >> aux;
        --len;
        list_of_nums.push_back(aux);
    }

    // sort list
    sort(list_of_nums.begin(), list_of_nums.end());

    // to know whos turn it is.
    int parity = 0;

    // get best sum for each one of them
    for (int i = list_of_nums.size() - 1; i >= 0; --i) {
        if (parity == 0) {
            tuzgu += list_of_nums[i];
            parity = 1;
        } else {
            rita += list_of_nums[i];
            parity = 0;
        }
    }

    // write output we need
    write_to << tuzgu - rita;
    write_to << "\n";

    // close output files
    read_from.close();
    write_to.close();
    return 0;
}
