package me.tom.dfs_bfs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class 삼성_구슬탈출2 {
    public static int N;
    public static int M;
    public static char[][] map;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();

        map = new char[N][M];
        Position initPosition = new Position();
        // 지도 채우기
        for (int i = 0 ; i < N ; i++) {
            String line = sc.next();
            for (int j = 0 ; j < M ; j++) {
                // R, B는 지도에 직접 쓰지 않고 위치만 기록한 후에 빈 공간으로 만들어둔다.
                if (line.charAt(j) == 'R') {
                    initPosition.setRed(new Coord(i, j));
                    map[i][j] = '.';
                    continue;
                } else if (line.charAt(j) == 'B') {
                    initPosition.setBlue(new Coord(i, j));
                    map[i][j] = '.';
                    continue;
                }
                // 지도 원본 데이터 채우기
                map[i][j] = line.charAt(j);
            }
        }

        int result = bfs(initPosition);
        System.out.println(result);
    }

    public static int bfs(Position position) {
        Queue<Position> q = new LinkedList<>();
        q.offer(position);

        while (!q.isEmpty()) {
            Position curr = q.poll();

            // 현재 move가 10이면 다음번 움직임은 필요가 없음
            if (curr.moveCount > 9) {
                continue;
            }

            // 4방향 움직이고 종료 여부 판정
            // 종료 되면 즉시 현재 moveCount를 반환, 파란공 빠지면 q에 넣지 않음
            Position left = curr.clone();
            left.moveLeft();
            if (left.isRedExit && !left.isBlueExit) {
                return left.moveCount; // 정답
            } else if (!left.isRedExit && !left.isBlueExit){
                // 둘 다 안나왔으면 더 탐색해야 함.
                q.offer(left);
            }

            Position right = curr.clone();
            right.moveRight();
            if (right.isRedExit && !right.isBlueExit) {
                return right.moveCount; // 정답
            } else if (!right.isRedExit && !right.isBlueExit){
                // 둘 다 안나왔으면 더 탐색해야 함.
                q.offer(right);
            }

            Position up = curr.clone();
            up.moveUp();
            if (up.isRedExit && !up.isBlueExit) {
                return up.moveCount; // 정답
            } else if (!up.isRedExit && !up.isBlueExit){
                // 둘 다 안나왔으면 더 탐색해야 함.
                q.offer(up);
            }

            Position down = curr.clone();
            down.moveDown();
            if (down.isRedExit && !down.isBlueExit) {
                return down.moveCount; // 정답
            } else if (!down.isRedExit && !down.isBlueExit){
                // 둘 다 안나왔으면 더 탐색해야 함.
                q.offer(down);
            }
        }

        return -1;
    }

    // 빨간공, 파란공의 움직임을 기록하는 클래스
    public static class Position {
        public Coord red;                   // 빨간공 좌표
        public Coord blue;                  // 파란공 좌표
        public int moveCount = 0;           // 이 상태까지 움직인 개수
        public boolean isRedExit = false;   // 빨간공 탈출 했는가?
        public boolean isBlueExit = false;  // 파란공 탈출 했는가?

        public void setRed(Coord red) {
            this.red = red;
        }

        public void setBlue(Coord blue) {
            this.blue = blue;
        }

        // 다른 움직임을 시도해보기 위해 현재 상태를 복제해서 줌.
        public Position clone() {
            Position newOne = new Position();
            newOne.setRed(new Coord(red.x, red.y));
            newOne.setBlue(new Coord(blue.x, blue.y));
            newOne.moveCount = moveCount;
            return newOne;
        }

        public void moveLeft() {
            moveCount++;
            // 초기 Y 위치 저장
            int initRedY = red.y;
            int initBlueY = blue.y;

            // 1. 빨간공 왼쪽 이동 (y-- 방향)
            boolean canMove = true;
            while (canMove) {
                int nextY = red.y - 1;
                if (nextY < 1 || nextY >= M - 1) {
                    canMove = false;
                    continue;
                }
                // 빈 공간이면 움직여
                if (map[red.x][nextY] == '.') {
                    red.y--;
                } else if (map[red.x][nextY] == '#') {
                    canMove = false;
                } else {
                    // 구멍인 경우
                    isRedExit = true;
                    canMove = false;
                }
            }

            // 2. 파란공 왼쪽 이동
            canMove = true;
            while (canMove) {
                int nextY = blue.y - 1;
                if (nextY < 1 || nextY >= M - 1) {
                    canMove = false;
                    continue;
                }
                // 빈 공간이면 움직여
                if (map[blue.x][nextY] == '.') {
                    blue.y--;
                } else if (map[blue.x][nextY] == '#') {
                    canMove = false;
                } else {
                    // 구멍인 경우
                    isBlueExit = true;
                    canMove = false;
                }
            }

            // 두 공이 같은 row에 있어서 겹친 경우
            if (red.y == blue.y && red.x == blue.x) {
                // 빨간게 왼쪽에 있었으면, 파란공을 오른쪽으로 한 칸 움직임.
                if (initRedY < initBlueY) {
                    blue.y++;
                } else {
                    red.y++;
                }
            }
        }

        public void moveRight() {
            moveCount++;
            // 초기 Y 위치 저장
            int initRedY = red.y;
            int initBlueY = blue.y;

            // 1. 빨간공 오른쪽 이동 (y++ 방향)
            boolean canMove = true;
            while (canMove) {
                int nextY = red.y + 1;
                if (nextY < 1 || nextY >= M - 1) {
                    canMove = false;
                    continue;
                }
                // 빈 공간이면 움직여
                if (map[red.x][nextY] == '.') {
                    red.y++;
                } else if (map[red.x][nextY] == '#') {
                    canMove = false;
                } else {
                    // 구멍인 경우
                    isRedExit = true;
                    canMove = false;
                }
            }

            // 2. 파란공 오른쪽 이동
            canMove = true;
            while (canMove) {
                int nextY = blue.y + 1;
                if (nextY < 1 || nextY >= M - 1) {
                    canMove = false;
                    continue;
                }
                // 빈 공간이면 움직여
                if (map[blue.x][nextY] == '.') {
                    blue.y++;
                } else if (map[blue.x][nextY] == '#') {
                    canMove = false;
                } else {
                    // 구멍인 경우
                    isBlueExit = true;
                    canMove = false;
                }
            }

            // 두 공이 같은 row에 있어서 겹친 경우
            if (red.y == blue.y && red.x == blue.x) {
                // 빨간게 왼쪽에 있었으면, 파란공을 오른쪽으로 한 칸 움직임.
                if (initRedY < initBlueY) {
                    red.y--;
                } else {
                    blue.y--;
                }
            }
        }

        public void moveUp() {
            moveCount++;
            // 초기 X 위치 저장
            int initRedX = red.x;
            int initBlueX = blue.x;

            // 1. 빨간공 위쪽 이동 (x-- 방향)
            boolean canMove = true;
            while (canMove) {
                int nextX = red.x - 1;
                if (nextX < 1 || nextX >= N - 1) {
                    canMove = false;
                    continue;
                }
                // 빈 공간이면 움직여
                if (map[nextX][red.y] == '.') {
                    red.x--;
                } else if (map[nextX][red.y] == '#') {
                    canMove = false;
                } else {
                    // 구멍인 경우
                    isRedExit = true;
                    canMove = false;
                }
            }

            // 2. 파란공 위쪽 이동
            canMove = true;
            while (canMove) {
                int nextX = blue.x - 1;
                if (nextX < 1 || nextX >= N - 1) {
                    canMove = false;
                    continue;
                }
                // 빈 공간이면 움직여
                if (map[nextX][blue.y] == '.') {
                    blue.x--;
                } else if (map[nextX][blue.y] == '#') {
                    canMove = false;
                } else {
                    // 구멍인 경우
                    isBlueExit = true;
                    canMove = false;
                }
            }

            // 두 공이 같은 colum에 있어서 겹친 경우
            if (red.x == blue.x && red.y == blue.y) {
                // 빨간게 왼쪽에 있었으면, 파란공을 오른쪽으로 한 칸 움직임.
                if (initRedX < initBlueX) {
                    blue.x++;
                } else {
                    red.x++;
                }
            }
        }

        public void moveDown() {
            moveCount++;
            // 초기 X 위치 저장
            int initRedX = red.x;
            int initBlueX = blue.x;

            // 1. 빨간공 아래쪽 이동 (x++ 방향)
            boolean canMove = true;
            while (canMove) {
                int nextX = red.x + 1;
                if (nextX < 1 || nextX >= N - 1) {
                    canMove = false;
                    continue;
                }
                // 빈 공간이면 움직여
                if (map[nextX][red.y] == '.') {
                    red.x++;
                } else if (map[nextX][red.y] == '#') {
                    canMove = false;
                } else {
                    // 구멍인 경우
                    isRedExit = true;
                    canMove = false;
                }
            }

            // 2. 파란공 아래쪽 이동
            canMove = true;
            while (canMove) {
                int nextX = blue.x + 1;
                if (nextX < 1 || nextX >= N - 1) {
                    canMove = false;
                    continue;
                }
                // 빈 공간이면 움직여
                if (map[nextX][blue.y] == '.') {
                    blue.x++;
                } else if (map[nextX][blue.y] == '#') {
                    canMove = false;
                } else {
                    // 구멍인 경우
                    isBlueExit = true;
                    canMove = false;
                }
            }

            // 두 공이 같은 colum에 있어서 겹친 경우
            if (red.x == blue.x && red.y == blue.y) {
                // 빨간게 왼쪽에 있었으면, 파란공을 오른쪽으로 한 칸 움직임.
                if (initRedX < initBlueX) {
                    red.x--;
                } else {
                    blue.x--;
                }
            }
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
