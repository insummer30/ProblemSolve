package me.tom.simul;

import java.util.Scanner;

public class 삼성_경사로 {
    public static int N;    // 지도 크기
    public static int L;    // 경사로 길이

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        L = sc.nextInt();

        // 지도 받기
        int[][] map = new int[N][N];
        for (int x = 0 ; x < N ; x++) {
            for (int y = 0 ; y < N ; y++) {
                map[x][y] = sc.nextInt();
            }
        }

        int possibleWayCount = 0;

        // 서 -> 동 경로 확인
        for (int x = 0 ; x < N ; x++) {
            if (checkEastWay(map, x)) {
                possibleWayCount++;
            }
        }

        // 북 -> 남 경로 확인
        for (int y = 0 ; y < N ; y++) {
            if (checkSouthWay(map, y)) {
                possibleWayCount++;
            }
        }

        System.out.println(possibleWayCount);
    }

    // 동쪽으로 길 체크
    public static boolean checkEastWay(int[][] map, int x) {
        // 경사로를 놓는데 사용했는지 여부
        boolean[] used = new boolean[N];

        // 0부터 매번 다음 스텝으로 갈 수 있는지 확인해보기
        for (int y = 0 ; y < N - 1 ; y++) {
            int nextStepDiff = map[x][y + 1] - map[x][y];
            if (nextStepDiff == 0) {
                // 그냥 전진
                continue;
            } else if (nextStepDiff == 1) {
                // 오르막 길, 경사로 둘 수 있는지 확인
                if (!canPutSlopeBack(used, y)) {
                    return false;
                }
            } else if (nextStepDiff == -1) {
                // 내리막 길
                if (!canPutSlopeFront(used, y)) {
                    return false;
                }
            } else {
                // 못 가는 길
                return false;
            }
        }
        return true;
    }

    // 동쪽으로 길 체크
    public static boolean checkSouthWay(int[][] map, int y) {
        // 경사로를 놓는데 사용했는지 여부
        boolean[] used = new boolean[N];

        // 0부터 매번 다음 스텝으로 갈 수 있는지 확인해보기
        for (int x = 0 ; x < N - 1 ; x++) {
            int nextStepDiff = map[x + 1][y] - map[x][y];
            if (nextStepDiff == 0) {
                // 그냥 전진
                continue;
            } else if (nextStepDiff == 1) {
                // 오르막 길
                if (!canPutSlopeBack(used, x)) {
                    return false;
                }
            } else if (nextStepDiff == -1) {
                // 내리막 길
                if (!canPutSlopeFront(used, x)) {
                    return false;
                }
            } else {
                // 못 가는 길
                return false;
            }
        }
        return true;
    }

    // 현 위치 포함 L 길이만큼 경사로를 왔던 길 쪽에 놓을 수 있는지 확인
    public static boolean canPutSlopeBack(boolean[] used, int curr) {
        for (int i = 0 ; i < L ; i++) {
            int checkIdx = curr - i;
            if (checkIdx < 0 || checkIdx >= N) {
                return false;
            }
            if (used[checkIdx]) {
                return false;
            } else {
                used[checkIdx] = true;
            }
        }
        return true;
    }

    // 현 위치 + 1 부터 L 길이 만큼 경사로를 갈길 앞쪽에 높을 수 있는지 확인
    public static boolean canPutSlopeFront(boolean[] used, int curr) {
        for (int i = 0 ; i < L ; i++) {
            int checkIdx = curr + i + 1;
            if (checkIdx < 0 || checkIdx >= N) {
                return false;
            }
            if (used[checkIdx]) {
                return false;
            } else {
                used[checkIdx] = true;
            }
        }
        return true;
    }
}
