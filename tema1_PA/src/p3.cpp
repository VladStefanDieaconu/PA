// Copyright (C) 2019 Vlad-Stefan Dieaconu 321 CA
#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <algorithm>
#include <string>
#include <cctype>
using namespace std;

class Scenario {
    int N;
    long long** d_matrix = NULL;

 public:
    Scenario() {
        N = 0;
        d_matrix = NULL;
    }

    Scenario(int len) {
        N = len;
        d_matrix = new long long *[N];
        for (int i = 0; i < N; ++i) {
            d_matrix[i] = new long long [N];
        }
    }

    ~Scenario() {
        if (d_matrix != NULL) {
            for (int i = 0; i < N; ++i) {
                delete[] d_matrix[i];
            }
                delete [] d_matrix;
        }
    }

    int find_best(vector<long long> list_of_nums) {
        // basic cases: what to do when you have only 1 or 2 nmbrs
        if (this->N == 1) {
            return list_of_nums[0];
        } else if (this->N == 2) {
            return max(list_of_nums[0], list_of_nums[1]);
        } else {
            // compute the dynamic programming matrix to solve the problem
            for (int it = 0; it < this->N; ++it) {
                for (int i = 0, j = it; j < this->N; ++i, ++j) {
                    long long start = list_of_nums[i];
                    long long end = list_of_nums[j];
                    // condition to be sure we dont get a seg fault
                    if (i + 2 <= j) {
                        // if I choose the element from the start of the array
                        // at the next move I will choose the minimum element
                        // from the elements left, because second player turn
                        // it will come and he will choose the maximum elem
                        // he can, becuase both players play in the most
                        // optimal way. 
                        start += min(d_matrix[i + 2][j],
                            d_matrix[i + 1][j - 1]);

                        // same logic, this is the case when I choose the elem
                        // from the end of the array
                        end += min(d_matrix[i + 1][j - 1],
                            d_matrix[i][j - 2]);
                    }
                    // at the end, I choose the best scenario for me, between
                    // the cases when I took the first or the last element.
                    d_matrix[i][j] = max(start, end);
                }
            }
            long long result = d_matrix[0][this->N - 1];
            return result;
        }
    }
};

int main () {
    // place to keep track of the sums of each player
    long long tuzgu_sum = 0;
    long long ritza_sum = 0;

    // files to read and write to
    ifstream read_from ("p3.in");
    ofstream write_to ("p3.out");

    // how many numbers we have
    int N = 0;
    long long aux; // user for reading
    long long  sum_of_list = 0; // used to calculate the sum of all numbers
    vector<long long > list_of_nums;

    // read value of N
    read_from >> N;

    // save value of N
    int len = N; // used for reading

    // create our obj, it will hold our matrix as member
    Scenario *scenario = new Scenario(N);

    // read all nums from file
    while (!read_from.eof() && (len != 0)) {
        read_from >> aux;
        sum_of_list += aux;
        --len;
        list_of_nums.push_back(aux);
    }

    // find the best case scenario
    long long best_scenario = scenario->find_best(list_of_nums);

    // tuzgu goes first, so best scenario for tuzgu
    tuzgu_sum = best_scenario;

    // ritza is second, so he's left with the elements that were not
    // choose by Tuzgu, who got the best case scenario
    ritza_sum = sum_of_list - tuzgu_sum;

    // write result to file
    write_to << tuzgu_sum - ritza_sum;
    write_to << "\n";

    // close files
    read_from.close();
    write_to.close();
    return 0;
}
