package me.tom.dfs_bfs;

import java.util.Scanner;

public class 삼성_2048 {
    public static void main(String[] args) {
        // 입력
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        int startPan[][] = new int[N][N];
        for (int i = 0 ; i < N ; i++) {
            for (int j = 0 ; j < N ; j++) {
                startPan[i][j] = sc.nextInt();
            }
        }

        // bfs로 풀어보기
        // 5번을 다 돌고나서 그때 판의 max 값을 저장한다.
        Board board = new Board();
        board.pan = startPan;

        dfs(board, 0);

        System.out.println(maxOfMax);
    }

    public static int N;
    public static int maxOfMax = 0;

    static class Board {
        public int[][] pan;

        public Board clone() {
            Board newOne = new Board();
            int[][] temp = new int[N][N];
            for (int x = 0 ; x < N ; x++) {
                for (int y = 0 ; y < N ; y++) {
                    temp[x][y] = pan[x][y];
                }
            }
            newOne.pan = temp;
            return newOne;
        }

        // 시계 방향으로 90도 회전시키기.
        public void rotate() {
            int[][] temp = new int[N][N];

            for (int x = 0 ; x < N ; x++) {
                for (int y = 0 ; y < N ; y++) {
                    temp[y][N - 1 - x] = pan[x][y];
                }
            }
            pan = temp;
        }

        // 현재 판에서 가장 큰 값 구하기
        public int getMax() {
            int max = -1;
            for (int x = 0 ; x < N ; x++) {
                for (int y = 0 ; y < N ; y++) {
                    if (pan[x][y] > max) {
                        max = pan[x][y];
                    }
                }
            }
            return max;
        }

        // 판을 위쪽으로 더하기 (pan -> temp로 이동 시키며 더해준다.)
        public void up() {
            int[][] temp = new int[N][N];

            // x 방향으로 아래서부터 복사 해나감 y번 반복...
            for (int y = 0 ; y < N ; y++) {

                int curr = 0; // 복사할 자리 포인터
                boolean alreadyAdd = false; // 이미 더해졌는지 체크

                for (int x = 0 ; x < N ; x++) {
                    // 복사할 값이 0이면 포인터 그대로 있고 아무것도 하면 안됨.
                    if (pan[x][y] == 0) {
                        continue;
                    }

                    if (x == 0) {
                        temp[x][y] = pan[x][y];
                        continue;
                    }

                    if (temp[curr][y] == pan[x][y] && !alreadyAdd) {
                        temp[curr][y] *= 2;
                        alreadyAdd = true;
                    } else {
                        if (temp[curr][y] == 0) {
                            temp[curr][y] = pan[x][y];
                        } else {
                            temp[++curr][y] = pan[x][y];
                        }
                        alreadyAdd = false;
                    }
                }
            }
            // temp는 지역변수니까 꼭 복사를 해줘야 한다.
            for (int x = 0 ; x < N ; x++) {
                for (int y = 0 ; y < N ; y++) {
                    pan[x][y] = temp[x][y];
                }
            }
        }

        public void printCurrent() {
            for (int x = 0 ; x < N ; x++) {
                for (int y = 0 ; y < N ; y++) {
                    System.out.print(pan[x][y] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void dfs(Board board, int count) {
        if (count == 5) {
            if (maxOfMax < board.getMax()) {
                maxOfMax = board.getMax();
            }
            return;
        }

        // 여기 중요!!! 원본 복사해서 한번 up 하고 dfs 태우고, 다른 방향으로 바꾸기 위해 up 적용전 원본으로 rotate 해야 함
        for (int dir = 0 ; dir < 4; dir++) {
            Board next = board.clone();
            next.up();
            dfs(next, count + 1);
            board.rotate();
        }
    }
}