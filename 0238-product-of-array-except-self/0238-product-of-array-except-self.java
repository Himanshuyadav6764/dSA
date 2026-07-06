class Solution {
    public int[] productExceptSelf(int[] nums) {
       int n = nums.length;

int[] prefix = new int[n];
int[] suffix = new int[n];
int[] answer = new int[n];

// Prefix
prefix[0] = 1;
for (int i = 1; i < n; i++) {
    prefix[i] = prefix[i - 1] * nums[i - 1];
}

// Suffix
suffix[n - 1] = 1;
for (int i = n - 2; i >= 0; i--) {
    suffix[i] = suffix[i + 1] * nums[i + 1];
}

// Answer
for (int i = 0; i < n; i++) {
    answer[i] = prefix[i] * suffix[i];
}

return answer; 
    }
}