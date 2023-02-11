package game;

import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 * @author Konstantin Tsitsin (iopuiuyhgo@gmail.com)
 */
public class Main {
    public static void swap(final Player[] players) {
        final Player tmp = players[0];
        players[0] = players[1];
        players[1] = tmp;
    }

    public static int function(int x) {
        int y = (int) ((9 * Math.pow(x, 5) - 120 * Math.pow(x, 4) + 535 * Math.pow(x, 3) - 900 * Math.pow(x, 2) + 316 * x + 480) / 80);
        return y;
    }

    public static void main(String[] args) {
        int result;
        Scanner in = new Scanner(System.in);
        int m, n, k;
        int nwins;
        int wincount1 = 0, wincount2 = 0;
        int drawcount = 0;
        while (true) {
            try {
                m = in.nextInt();
                n = in.nextInt();
                k = in.nextInt();
                nwins = in.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }
        Player[] players = new Player[4];
        players[0] = new RandomPlayer(m, n);
        players[1] = new SequentialPlayer(m, n);
        players[2] = new RandomPlayer(m, n);
        players[3] = new SequentialPlayer(m, n);

        final Game game = new Game(false, players[0], players[1]);
        MnkBoard board = new MnkBoard(m, n, k);
        int[][] tourtabl = new int[players.length][players.length];
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players.length; j++) {
                tourtabl[i][j] = -1;
            }
        }


//        //препятствия (36-39)
//        m=11;
//        n=11;
//        for (int i = 0; i < 11; i++) {
//            board.doL(i, i);
//            board.doL(i, 10-i);
//        }


        //турнир (36-39)
//        int points;
//        for (int i = 0; i < players.length; i++) {
////            System.out.print("p"+i+" ");
//            for (int j = 0; j < players.length; j++) {
//                if (i == j) {
////                    System.out.print("# ");
//                } else {
//                    if (tourtabl[j][i]!=-1) {
//                        points = function(tourtabl[j][i]);
//                        tourtabl[i][j] = points;
////                        System.out.print(points + " ");
//                        continue;
//                    }
//                    points=0;
//
//                    game.newGame(false, players[i], players[j]);
//                    result = game.play(board);
//                    if (result == 1) {
//                        points+=2;
//                    } else if (result == 0) {
//                        points++;
//                    }
//                    board.cleanBoard();
//                    game.newGame(false, players[j], players[i]);
//                    result = game.play(board);
//                    if (result == 1) {
//                        points+=2;
//                    } else if (result == 0) {
//                        points++;
//                    }
//                    board.cleanBoard();
//                    tourtabl[i][j] = points;
////                    System.out.print(points + " ");
//                }
//            }
////            System.out.println();
//        }
//        for (int i = 0; i < players.length; i++) {
////            System.out.print("p"+i+" ");
//            for (int j = 0; j < players.length; j++) {
//                if (i == j) {
//                    System.out.print("# ");
//                } else {
//                    System.out.print(tourtabl[i][j] + " ");
//                }
//            }
//            System.out.println();
//        }


//        матчи (32-35)
//        while (wincount1 < nwins && wincount2 < nwins) {
//
//            result = game.play(board);
//            if (result == 1) {
//                wincount1++;
//            } else if (result == 2) {
//                wincount2++;
//            } else {
//                drawcount++;
//            }
//            int tmp = wincount1;
//            wincount1 = wincount2;
//            wincount2 = tmp;
//            swap(players);
//            board.cleanBoard();
//            game.newGame(false, players[0], players[1]);
//        }
//        if ((wincount1+wincount2+drawcount)%2==1) {
//            int tmp = wincount1;
//            wincount1 = wincount2;
//            wincount2 = tmp;
//        }
//        System.out.println("Player 1 wins: " + wincount1);
//        System.out.println("Player 2 wins: " + wincount2);
    }
}
