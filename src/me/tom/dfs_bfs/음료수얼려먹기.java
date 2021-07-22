package me.tom.dfs_bfs;

import java.util.Scanner;

/**
 * [문제]
 * 얼음틀이 주어졌을 때 얼릴 수 있는 얼음 덩어리의 최대 개수는?
 *
 * [입력]
 * 4 5      n, m (얼음틀의 크기)
 * 00110    얼음틀 모양 (1은 막혀있는거 0은 물을 넣을 수 있는 곳)
 * 00011
 * 11111
 * 00000
 *
 * [예시 답]
 * = 3
 */
public class 음료수얼려먹기 {
    public static void main(String[] args) {
        // Input 입력
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] nodes = new int[n][m];
        for (int i = 0 ; i < n ; i++) {
            String line = sc.next();
            for (int j = 0 ; j < m ; j++) {
                nodes[i][j] = line.charAt(j) - '0';
            }
        }

        // BFS
        int count = 0;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                count += dfs(i, j, nodes, n, m);
            }
        }
    }

    private static int dfs(int x, int y, int[][] nodes, int n, int m) {
        // 범위를 넘어가면 종료
        if (x < 0 || x >= n || y < 0 || y >= m) {
            return 0;
        }
        // 방문한 적이 있으면
        if (nodes[x][y] == 1) {
            return 0;
        }
        // 방문 처리
        nodes[x][y] = 1;

        // 연결된 것들 방문 하기
        int[] dx = { 1, -1, 0, 0 };
        int[] dy = { 0, 0, 1, -1 };
        for (int d = 0 ; d < dx.length ; d++) {
            int nextX = x + dx[d];
            int nextY = y + dy[d];
            dfs(nextX, nextY, nodes, n, m);
        }

        return 1;
    }
}
