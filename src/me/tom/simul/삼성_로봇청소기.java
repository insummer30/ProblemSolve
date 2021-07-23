package me.tom.simul;

import java.util.Scanner;

/**
 * 문제링크: https://www.acmicpc.net/problem/14503
 */
public class 삼성_로봇청소기 {
    public static int N;
    public static int M;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 지도 크기 저장
        N = sc.nextInt();
        M = sc.nextInt();
        // 초기 위치 및 방향 저장
        int startX = sc.nextInt();
        int startY = sc.nextInt();
        int initDirection = sc.nextInt();

        // 지도 세팅
        int[][] map = new int[N][M];
        for (int x = 0 ; x < N ; x++) {
            for (int y = 0 ; y < M ; y++) {
                map[x][y] = sc.nextInt();
            }
        }

        RobotCleaner RobotCleaner = new RobotCleaner(startX, startY, initDirection, map);
        RobotCleaner.start();

        System.out.println(RobotCleaner.cleanCount);
    }

    public static class RobotCleaner {
        public int x;
        public int y;
        public int direction;   // 0:북, 1:동, 2:남, 3:서
        public int[][] map;
        public int cleanCount = 0;

        public RobotCleaner(int x, int y, int direction, int[][] map) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.map = map;
        }

        public void start() {
            boolean canMove = true;

            while (canMove) {
                // 1. 현재 위치 청소 (2는 청소했다는 표시)
                if (map[x][y] == 0) {
                    map[x][y] = 2;
                    cleanCount++;
                }

                // 2. 사방향 탐색 및 이동
                if (!rotateLeftAndMove(4)) {
                    // 4방향 다 못 움직이면 뒤로 이동해서 다시 콜
                    // 뒤에가 벽이면 더이상 움직이지 못함
                    switch (direction) {
                        // 0:북, 1:동, 2:남, 3:서
                        case 0:
                            if (checkBoundary(x + 1, y) && map[x + 1][y] != 1) {
                                x++;
                            } else {
                                canMove = false;
                            }
                            break;
                        case 1:
                            if (checkBoundary(x, y - 1) && map[x][y - 1] != 1) {
                                y--;
                            } else {
                                canMove = false;
                            }
                            break;
                        case 2:
                            if (checkBoundary(x - 1, y) && map[x - 1][y] != 1) {
                                x--;
                            } else {
                                canMove = false;
                            }
                            break;
                        case 3:
                            if (checkBoundary(x, y + 1) && map[x][y + 1] != 1) {
                                y++;
                            } else {
                                canMove = false;
                            }
                            break;
                    }
                }
            }
        }

        public boolean rotateLeftAndMove(int checkCount) {
            // 더이상 못 움직이면 끝
            if (checkCount == 0) {
                return false;
            }

            // rotate and move, 왼쪽으로 돌고 청소할 곳 있으면 움직이고, 없으면 체크 카운트 동날 때까지 탐색~
            switch (direction) {
                // 0:북, 1:동, 2:남, 3:서
                case 0: // 북
                    direction = 3; // 서쪽 보기
                    if (checkBoundary(x, y - 1) && map[x][y - 1] == 0) {
                        y--;
                        return true;
                    }
                    break;
                case 1: // 동
                    direction = 0; // 북쪽 보기
                    if (checkBoundary(x - 1, y) && map[x - 1][y] == 0) {
                        x--;
                        return true;
                    }
                    break;
                case 2: // 남
                    direction = 1; // 동쪽 보기
                    if (checkBoundary(x, y + 1) && map[x][y + 1] == 0) {
                        y++;
                        return true;
                    }
                    break;
                case 3: // 서
                    direction = 2; // 남쪽 보기
                    if (checkBoundary(x + 1, y) && map[x + 1][y] == 0) {
                        x++;
                        return true;
                    }
                    break;
            }
            // 탐색해서 이동 못했으면 체크카운트 줄이고 다시 재귀 콜
            return rotateLeftAndMove(checkCount - 1);
        }

        public boolean checkBoundary(int x, int y) {
            if (x < 0 || x >= N || y < 0 || y >= M) {
                return false;
            }
            return true;
        }
    }
}
