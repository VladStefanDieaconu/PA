// skel PA 2017

#include <iostream>
#include <vector>

#include "reversi.h"

inline int abs(int x)
{
    return x > 0 ? x : -x;
}

Move::Move(int player, int x = -1, int y = -1): player(player), x(x), y(y) {}

Reversi::Reversi()
{
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            data[i][j] = 0;
    data[N / 2 - 1][N / 2 - 1] = data[N / 2][N / 2] = 1;
    data[N / 2 - 1][N / 2] = data[N / 2][N / 2 - 1] = -1;
}


/**
 * Intoarce true daca jocul este intr-o stare finala
 */
    bool
Reversi::ended()
{
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
        {
            Reversi tmp(*this);
            if (tmp.apply_move(Move(1, i, j)))
                return false;
            tmp = *this;
            if (tmp.apply_move(Move(-1, i, j)))
                return false;
        }
    return true;
}

/**
 * Returns 1 if player won, 0 if draw and -1 if lost
 */
int
Reversi::winner(int player)
{
    if (!ended())
        return 0;
    int s = 0;
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
        {
            if (data[i][j] == player)
                s++;
            else if (data[i][j] == -player)
                s--;
        }
    return s > 0 ? 1 : s == 0 ? 0 : -1;
}

/**
 * Functia de evaluare a starii curente a jocului
 * Evaluarea se face din perspectiva jucatorului
 * aflat curent la mutare (player)
 */
    int
Reversi::eval(int player)
{
    /**
     * TODO Implementati o functie de evaluare
     * Aceasta trebuie sa intoarca:
     * Inf daca jocul este terminat in favoarea lui player
     * -Inf daca jocul este terminat in defavoarea lui player
     *
     * In celelalte cazuri ar trebui sa intoarca un scor cu atat
     * mai mare, cu cat player ar avea o sansa mai mare de castig
     */
    if (winner(player) == -1) {
        return -Inf;
    } else if (winner(player) == 1) {
        return Inf;
    }   
    int curr_player_points = 0;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (data[i][j] == player) {
                curr_player_points++;
            }
            else if (data[i][j] == -player) {
                curr_player_points--;
            }
        }
    }
    // Return the difference between player's balls and opponent's balls
    return curr_player_points;
}

/**
 * Aplica o mutare a jucatorului, modificand starea jocului
 * Plaseaza piesa jucatorului move.player in pozitia move.x, move.y
 * Mutarea move.x == -1, move.y == -1 semnifica ca jucatorul
 * paseaza randul la mutare
 * Returneaza true daca mutarea este valida
 */
    bool
Reversi::apply_move(const Move &move)
{
    if (move.x == -1 && move.y == -1)
        return true;

    if (data[move.x][move.y] != 0)
        return false;

    bool ok = false;

    for (int x_dir = -1; x_dir <= 1; x_dir++)
        for (int y_dir = -1; y_dir <= 1; y_dir++)
        {
            if (x_dir == 0 && y_dir == 0)
                continue;
            int i = move.x + x_dir, j = move.y + y_dir;
            for (; i >= 0 && j >= 0 && i < N && j < N && data[i][j] == -move.player; i += x_dir, j += y_dir);
            if (i >= 0 && j >= 0 && i < N && j < N && data[i][j] == move.player && (abs(move.x - i) > 1 || abs(move.y - j) > 1))
            {
                ok = true;
                for (i = move.x + x_dir, j = move.y + y_dir; i >= 0 && j >= 0 && i < N && j < N && data[i][j] == -move.player; i += x_dir, j += y_dir)
                    data[i][j] = move.player;
            }
        }

    if (!ok)
        return false;

    data[move.x][move.y] = move.player;

    return true;
}

/**
 * Afiseaza starea jocului
 */
    void
Reversi::print()
{
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j< N; j++)
        {
            if (data[i][j] == 0)
                std::cout << '.';
            else if (data[i][j] == 1)
                std::cout << 'O';
            else
                std::cout << 'X';
            std::cout << " ";
        }
        std::cout << std::endl;
    }
    std::cout << std::endl;
}

std::vector<Move>
Reversi::get_moves(int player) {
    std::vector<Move> ret;
    bool valid = false;
    int i = 0, k = 0;
    
    for (i = 0; i < N; i++) {
        for (k = 0; k < N; k++) {
            valid = false;
            for (int ii = 0; ii < N; ++ii ) {
                if (data[i][ii] == player) {
                    valid = true;
                    break;
                }
            }
            for (int jj = 0; jj < N; ++jj) {
                if (data[jj][k] == player) {
                    valid = true;
                    break;
                }
            }
        
            if (valid == true) {
                ret.push_back(Move(player, i, k));
            }
        }
    }
    return ret;
}

/**
 * Implementarea algoritmului minimax (negamax)
 * Intoarce o pereche <x, y> unde x este cel mai bun scor
 * care poate fi obtinut de jucatorul aflat la mutare,
 * iar y este mutarea propriu-zisa
 */
std::pair<int, Move>
minimax(Reversi init, int player, int depth)
{
    /**
     * TODO Implementati conditia de oprire
     */
    if (depth == 0 || init.ended()) {
        return std::pair<int, Move>(init.eval(player), Move(player));
    }

    int score = -Inf - 1;
    int max = -Inf - 1;
    Move valid_move(player);
    std::vector<Move> moves = init.get_moves(player);

    for (Move move : moves) {
        Reversi nim(init);

        if (!nim.apply_move(move)) {
            continue;
        }

        score = -minimax(nim, -player, depth - 1).first;
        if (score > max) {
            max = score;
            valid_move = move;
        }
    }
    
    return std::pair<int, Move>(max, valid_move);
}
    
    /**
     * TODO Determinati mutarile posibile
     * si determinati cel mai bun scor si cea mai buna mutare
     * folosind algoritmul minimax
     *
     * Fiti atenti ca daca player nu poate efectua nici o mutare, el
     * poate ceda randul la mutat prin mutarea conventionala <player, -1, -1>
     */
/**
 * Implementarea de negamax cu alpha-beta pruning
 * Intoarce o pereche <x, y> unde x este cel mai bun scor
 * care poate fi obtinut de jucatorul aflat la mutare,
 * iar y este mutarea propriu-zisa
 */
    std::pair<int, Move>
minimax_abeta(Reversi init, int player, int depth, int alfa, int beta)
{
    /* Conditia de oprire */
    if (depth == 0 || init.ended())
        return std::pair<int, Move>(init.eval(player), Move(player));

    int score = -Inf - 1;
    Move valid_move(player);
    std::vector<Move> moves = init.get_moves(player);

    for (Move move : moves) {
        Reversi nim(init);

        if (!nim.apply_move(move)) {
            continue;
        }

        score = -minimax_abeta(nim, -player, depth - 1, -alfa, -beta).first;
        if (score >= alfa) {
            alfa = score;
            valid_move = move;
        }
        if (alfa >= beta) {
            break;
        }
    }

    return std::pair<int, Move>(alfa, valid_move);
}

int main()
{
    Reversi rev;
    rev.print();

    /* Choose here if you want COMP vs HUMAN or COMP vs COMP */
    bool HUMAN_PLAYER = false;
    int player = 1;

    while (!rev.ended())
    {
        std::pair<int, Move> p(0, Move(player));
        if (player == 1)
        {
            p = minimax(rev, player, 3);
            //p = minimax_abeta(rev, player, 9, -Inf, Inf);

            std::cout << "Player " << player << " evaluates to " << p.first << std::endl;
            rev.apply_move(p.second);
        }
        else
        {
            if (!HUMAN_PLAYER)
            {
                p = minimax(rev, player, 6);
                //p = minimax_abeta(rev, player, 9, -Inf, Inf);

                std::cout << "Player " << player << " evaluates to " << p.first << std::endl;
                rev.apply_move(p.second);
            }
            else
            {
                bool valid = false;
                while (!valid)
                {
                    int x, y;
                    std::cout << "Insert position [0..N - 1], [0..N - 1] ";
                    std::cin >> x >> y;
                    valid = rev.apply_move(Move(player, x, y));
                }
            }
        }

        rev.print();
        player *= -1;
    }

    int w = rev.winner(1);
    if (w == 1)
        std::cout << "Player 1 WON!" << std::endl;
    else if (w == 0)
        std::cout << "Draw" << std::endl;
    else
        std::cout << "Player -1 WON!" << std::endl;
}
