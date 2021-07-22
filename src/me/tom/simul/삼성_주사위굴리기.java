package me.tom.simul;

import java.util.Scanner;

/**
 * Created on 2021/07/22
 *
 * @author tom.j
 */
public class 삼성_주사위굴리기 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 지도 크기
        int N = sc.nextInt(); int M = sc.nextInt();
        int[][] map = new int[N][M];

        // 주사위 시작점
        int startX = sc.nextInt();
        int startY = sc.nextInt();
        Dice dice = new Dice(startX, startY, map, N, M);

        // 명령어 개수
        int cmdNum = sc.nextInt();
        int[] cmdArr = new int[cmdNum];

        // 지도 데이터 받기
        for (int i = 0 ; i < N ; i++) {
            for (int j = 0 ; j < M ; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        // 명령어 받기
        for (int i = 0 ; i < cmdNum ; i++) {
            cmdArr[i] = sc.nextInt();
        }

        // 명령어 수행하면서 출력하기
        // 1:동쪽, 2:서쪽, 3:북쪽, 4:남쪽
        for (int i = 0 ; i < cmdNum ; i++) {
            dice.move(cmdArr[i]);
        }
    }

    static class Dice {
        int[][] map;
        int N, M;
        public int x;
        public int y;
        public int[] surface = new int[6];
        /*   인덱스와 주사위 전개도
                  4
                0 1 2
                  5
                  3         */

        public Dice(int x, int y, int[][] map, int N, int M) {
            this.x = x;
            this.y = y;
            this.map = map;
            this.N = N;
            this.M = M;
        }

        // 지도 안에 존재하는지 확인
        private boolean checkBoundary(int x, int y) {
            if (x >= 0 && x < N && y >= 0 && y < M) {
                return true;
            }
            return false;
        }

        // 맵에 0 있으면 주사위 바닥 수가 복사 되고, 0이 아니면 주사위 바닥에 복사한 후 맵은 0으로 변경
        private void changeBottom() {
            if (map[x][y] == 0) {
                map[x][y] = surface[3];
            } else {
                surface[3] = map[x][y];
                map[x][y] = 0;
            }
        }

        public void move(int direct) {
            switch (direct) {
                case 1: moveEast(); break;
                case 2: moveWest(); break;
                case 3: moveNorth(); break;
                case 4: moveSouth(); break;
            }
        }

        // 0 -> 1, 1 -> 2, 2 -> 3, 3 -> 0
        public void moveEast() {
            if(checkBoundary(x, y + 1)) {
                y++;
                int tmp = surface[0];
                surface[0] = surface[3];
                surface[3] = surface[2];
                surface[2] = surface[1];
                surface[1] = tmp;
                changeBottom();
                System.out.println(surface[1]);
            }
        }

        // 0 -> 3, 3 -> 2, 2 -> 1, 1 -> 0
        public void moveWest() {
            if(checkBoundary(x, y - 1)) {
                y--;
                int tmp = surface[0];
                surface[0] = surface[1];
                surface[1] = surface[2];
                surface[2] = surface[3];
                surface[3] = tmp;
                changeBottom();
                System.out.println(surface[1]);
            }
        }

        // 1 -> 4, 5 -> 1, 3 -> 5, 4 -> 3
        public void moveNorth() {
            if(checkBoundary(x - 1, y)) {
                x--;
                int tmp = surface[3];
                surface[3] = surface[4];
                surface[4] = surface[1];
                surface[1] = surface[5];
                surface[5] = tmp;
                changeBottom();
                System.out.println(surface[1]);
            }
        }

        // 4 -> 1, 1 -> 5, 5 -> 3, 3 -> 4
        public void moveSouth() {
            if(checkBoundary(x + 1, y)) {
                x++;
                int tmp = surface[4];
                surface[4] = surface[3];
                surface[3] = surface[5];
                surface[5] = surface[1];
                surface[1] = tmp;
                changeBottom();
                System.out.println(surface[1]);
            }
        }
    }
}
