package me.tom.dfs_bfs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * [문제]
 * (1,1) 위치에서 시작하여 (N,M)의 출구로 한 번에 한칸씩 이동하는데 괴물이 있는 곳은 0, 없는 곳은 1로 표시 되어 있다.
 * 미로는 반드시 탈출할 수 있는 형태로 주어진다.
 * 이때, 탈출하기 위해 움직여야 하는 최소 칸의 개수를 구하세요.
 * 칸을 셀 때는 시작 칸과 마지막 칸을 모두 포함해서 계산해야 한다.
 * (시작 칸과 탈출 칸은 항상 1이다)
 * <p>
 * [예시]
 * 5 6
 * 101010
 * 111111
 * 000001
 * 111111
 * 111111
 * <p>
 * [예시 답]
 * = 10
 */
public class 미로탈출 {
    public static void main(String[] args) {
        // 입력 받기
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] graph = new int[n][m];

        for (int i = 0; i < n; i++) {
            String line = sc.next();
            for (int j = 0; j < m; j++) {
                graph[i][j] = line.charAt(j) - '0';
            }
        }

        System.out.println("Started...");

        int ret = bfs(0, 0, graph, n, m);
        System.out.println("result = " + ret);
    }

    private static int bfs(int x, int y, int[][] graph, int n, int m) {
        Queue<Position> q = new LinkedList<>();
        Position p = new Position(x, y);

        q.offer(p);

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Position cp = q.poll();

            // 4방향에 대해 이동 가능한지 검사하고 이동 가능한 경우 현재 값에서 1을 더해준 것으로 세팅하고 큐에 넣음
            for (int i = 0; i < 4; i++) {
                // 현재 위치에서 이동
                int nextX = cp.x + dx[i];
                int nextY = cp.y + dy[i];

                if (nextX >= 0 && nextX < n && nextY >= 0 && nextY < m // 지도 범위 내
                        && !(nextX == 0 && nextY == 0)  // 시작점은 제외
                        && graph[nextX][nextY] == 1     // 아직 방문하지 않은 길
                ) {
                    graph[nextX][nextY] = graph[cp.x][cp.y] + 1;
                    q.offer(new Position(nextX, nextY));
                }
            }
        }

        return graph[n - 1][m - 1];
    }

    private static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
