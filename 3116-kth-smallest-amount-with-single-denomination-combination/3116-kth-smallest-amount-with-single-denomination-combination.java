import java.util.*;

class Solution {

    public long findKthSmallest(int[] coins, int k) {
        Arrays.sort(coins);

        // Remove redundant denominations
        // Example: if coins = [2, 4], 4 is unnecessary
        List<Long> list = new ArrayList<>();

        for (int coin : coins) {
            boolean redundant = false;

            for (long x : list) {
                if (coin % x == 0) {
                    redundant = true;
                    break;
                }
            }

            if (!redundant) {
                list.add((long) coin);
            }
        }

        long low = 1;
        long high = list.get(0) * k;

        while (low < high) {
            long mid = low + (high - low) / 2;

            if (count(mid, list) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private long count(long x, List<Long> coins) {
        return dfs(0, x, coins, 1, 0);
    }

    private long dfs(int index, long x, List<Long> coins,
                     long currentLCM, int count) {

        long result = 0;

        for (int i = index; i < coins.size(); i++) {

            long coin = coins.get(i);

            long g = gcd(currentLCM, coin);

            // Avoid overflow
            if (currentLCM > x / (coin / g)) {
                continue;
            }

            long newLCM = currentLCM / g * coin;

            if (newLCM > x) {
                continue;
            }

            long multiples = x / newLCM;

            // Inclusion-Exclusion
            if (count % 2 == 0) {
                result += multiples;
            } else {
                result -= multiples;
            }

            result += dfs(
                i + 1,
                x,
                coins,
                newLCM,
                count + 1
            );
        }

        return result;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}