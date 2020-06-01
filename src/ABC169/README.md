# ABC 169 (rank 1497)

## What I learned (From WA)
1. If the number is larger than the long range, we can use division to check whether it is overflow. 
2. The precision of double is not enough, we can add a little double to make sure the narrow down cast have the correct value.
3. When check prime, make sure to check the whether the residue number is 1 or prime. (D WA*4)
4. Time of using HashMap and time of using Array.

---
**Multiplication (B & C)**
1. The long integer overflow and double precision.

**Div Game (D)**: find all the prime and count the number of different factor.
1. Preprocess to find out all the prime numbers in sqrt(N) time.
2. After check all the primes, need to check whether the residue number is 1 or prime.


**Count Median (E)**: Greedy & Sort
1. Sort the numbers by their lower bound and upper bound.
2. Since we can add each number by 1, so all the possible value in the range can be the possible median.

**Knapsack for All Subsets (F)**: Dp & Subset.
1. Make sure the definition of dp, copy the question statement. 
2. dp[i][j]: for the first ith items, the number of total subset that have sum equal j.
3. KEY: for each new value, all the subset have sum = j will be doubled, because of adding this new number. If j >= val, there is another way to make new subset have sum = j. 
    ```
    dp[i][j] = 2 * dp[i - 1][j];
    if (j >= val) dp[i][j] += dp[i - 1][j - val];
    ```

