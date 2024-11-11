package com.github.gun2.heavy.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrimeNumberUtil {

    /**
     * CPU 성능 테스트를 위한 무거운 작업을 수행하는 메서드.
     * 주어진 limit까지의 소수를 계산.
     *
     * @param limit 소수를 계산할 범위 (2부터 limit까지)
     * @return 찾은 소수의 개수
     */
    public static int getTotalPrimeNumber(int limit) {
        int primeCount = 0;

        // 2부터 limit까지의 모든 소수를 찾음
        for (int i = 2; i <= limit; i++) {
            if (isPrime(i)) {
                primeCount++;
            }
        }

        return primeCount;
    }

    /**
     * 주어진 숫자가 소수인지 판별하는 헬퍼 메서드.
     *
     * @param number 판별할 숫자
     * @return 소수이면 true, 아니면 false
     */
    private static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
