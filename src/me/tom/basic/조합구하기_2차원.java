package me.tom.basic;

/**
 *  n X m 2차원 배열에서 r개 뽑기
 */
public class 조합구하기_2차원 {
    // 지도 사이즈
    public static int n = 2;
    public static int m = 3;

    public static void main(String[] args) {
        int[][] arr = new int[n][m];
        int r = 2;  // 뽑을 수

        pick(arr, r, 0, 0);
    }

    /*
        재귀적으로 r 개를 선택하는 함수
     */
    public static void pick(int[][] arr, int r, int startX, int startY) {
        // r개 선택이 완료 되었으면 종료하고 현재 선택된거 출력해주기
        if (r == 0) {
            for (int x = 0 ; x < n ; x++) {
                for (int y = 0 ; y < m ; y++) {
                    System.out.print(arr[x][y] + " ");
                }
                System.out.println();
            }
            System.out.println("----------------");
            return;
        }

        for (int x = startX ; x < n ; x++) {
            for (int y = startY ; y < m ; y++) {
                if (arr[x][y] == 0) {
                    arr[x][y] = 1;              // 선택하는 경우
                    pick(arr, r -1, x, y);   // 1개 줄여서 나머지꺼를 현재 커서 이후로 선택하기
                    arr[x][y] = 0;              // 선택을 안하는 경우(백트래킹), 다음걸로 지나감
                }
            }
            startY = 0; // 다음 Y를 선택했더라도 x가 증가하면 Y를 0부터 검사해야 하기 때문.
        }
    }
}
