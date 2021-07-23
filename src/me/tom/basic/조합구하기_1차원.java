package me.tom.basic;

/**
 * 주어진 1차원 n 크기 배열에서 r 개 뽑기
 */
public class 조합구하기_1차원 {
    public static void main(String[] args) {
        int[] arr = arr = new int[5];
        int r = 2;

        pick(arr, r, 0);
    }

    public static void pick(int[] arr, int count, int start) {
        if (count == 0) {
            for (int i = 0 ; i < arr.length ; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }

        for (int i = start ; i < arr.length ; i++) {
            if (arr[i] == 0) {
                arr[i] = 1; // 현재 자리를 뽑은 후에 그 다음 자리부터 뽑는 경우
                pick(arr, count - 1, i + 1);
                arr[i] = 0; // 현재 자리를 뽑지 않고 넘기기 (그러면 다음 자리를 뽑는걸로 넘어간다.)
            }
        }
    }
}
