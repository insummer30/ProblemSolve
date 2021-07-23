package me.tom.dfs_bfs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 문제링크: https://www.acmicpc.net/problem/14502
 */
public class 삼성_연구소 {
    public static int N;
    public static int M;
    public static int max;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 지도 크기 입력
        N = sc.nextInt();
        M = sc.nextInt();

        // 지도 입력
        int[][] map = new int[N][M];
        for (int x = 0 ; x < N ; x++) {
            for (int y = 0 ; y < M ; y++) {
                map[x][y] = sc.nextInt();
            }
        }

        pick(map, 3, 0, 0);

        System.out.println(max);
    }

    public static void pick(int[][] map, int count, int startX, int startY) {
        if (count == 0) {
            // 3개를 모두 선택한 경우 바이러스 퍼뜨리고, 남은 빈칸 수 세서 max 값 갱신하기
            spreadBFS(map);

            // --- for debug S
//            for (int x = 0 ; x < N ; x++) {
//                for (int y = 0 ; y < M ; y++) {
//                    System.out.print(map[x][y] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println("-----------------------");
            // --- for debug E
            return;
        }

        // 조합 경우의 수 구하기 (백트래킹)
        for (int x = startX ; x < N ; x++) {
            for (int y = startY ; y < M ; y++) {
                if (map[x][y] == 0) {
                    map[x][y] = 1;      // 현위치 벽 세우기
                    pick(map, count - 1, x, y);
                    map[x][y] = 0;      // 벽 안세우고 다음꺼 선택하기
                }
            }
            startY = 0;
        }
    }

    public static void spreadBFS(int[][] originMap) {
        Queue<Coord> q = new LinkedList<>();

        // 퍼뜨리기 전에 지도 복사하기
        int[][] copyMap = new int[N][M];
        for (int x = 0 ; x < N ; x++) {
            for (int y = 0 ; y < M ; y++) {
                copyMap[x][y] = originMap[x][y];

                // 만약 바이러스 있는 곳이면 미리 큐에 넣어준다.
                if (originMap[x][y] == 2) {
                    q.offer(new Coord(x, y));
                }
            }
        }

        // 4방향 벡터
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Coord curr = q.poll();

            copyMap[curr.x][curr.y] = 2; // 바이러스, 즉 방문처리...

            // 4방향 방문하기
            for (int dir = 0 ; dir < 4 ; dir++) {
                int nextX = curr.x + dx[dir];
                int nextY = curr.y + dy[dir];

                // 지도 밖으로 나가면 무시
                if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= M) {
                    continue;
                }
                // 퍼질 수 있는 빈공간 이면 큐에 넣어줌.
                if (copyMap[nextX][nextY] == 0) {
                    q.offer(new Coord(nextX, nextY));
                }
            }
        }

        // 다 퍼뜨렸으면 빈공간의 수를 세자.
        int emptySpaceCnt = 0;
        for (int x = 0 ; x < N ; x++) {
            for (int y = 0 ; y < M ; y++) {
                if (copyMap[x][y] == 0) {
                    emptySpaceCnt++;
                }
            }
        }

        // 최대값 갱신
        if (max < emptySpaceCnt) {
            max = emptySpaceCnt;
        }
    }

    public static class Coord {
        public int x;
        public int y;
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
