package progress;
/*
Given k days and array P as the item sizes, find out the minimum total container size required to move all the items...

You want to move N items in k days (N >= k). You have to move at least one item per day. The items are listed in array
P, where P[i] is size of item i. You can move item i only if all items from 0 to [i - 1] are already moved. Every day
you need a container to pack the item and move it. The container size needed for day i is the maximum item size moved
on that day. Given k days and army P as the item sizes, find out the minimum total container size required to move
all the items.

input P = [10, 2, 20, 5, 15, 10, 1]
d = 3
output 31
day 1 - [10, 2, 20, 5, 15]. ContainerSize = 20
day 2 - [10]. ContainerSize = 10
day 3 - [1]. ContainerSize = 1
Total = 20 + 10 + 1 = 31

input P = [10, 2, 20, 5, 15, 10, 1] d = 5
output 43
day 1 - move [10]. ContainerSize = 10
day 2 - move [2]. ContainerSize = 2
day 3 - move [20, 5, 15]. ContainerSize = 20
day 4 - move [10]. ContainerSize = 10
day 5 - move [1]. ContainerSize = 1
Total= 10 + 2 + 20 + 10 + 1 = 43

input P = [5, 4, 2, 4, 3, 4, 5, 4] d = 4
output 16
day 1 - [5, 4], containerSize = 5
day 2 - [2], containerSize = 2
day 3 - [4, 3], containerSize = 4
day 4 - [4, 5, 4], containerSize = 5
Total= + 2 +4+5 = 16

input P = [22, 12, 1, 14, 17] d = 2
output 39
day 1 - [22, 12], containerSize = 22
day 2 - [1, 14, 17],
containerSize = 17
Total = 22 + 17 = 39
*/

public class MinimumTotalContainerSize {

  //Note
  //load all items within 'd' days
  //To load an 'i'th item all the prev items should have been loaded
  //load atleast one item everyday

  public int findMinTotalContainerSize(int[] items, int days) {
    int numberOfItems = items.length;
    if (days > numberOfItems) {
      return -1;
    }

    int[][] dp = new int[days][numberOfItems];

    // Initialize dp for single day
    dp[0][0] = items[0];
    //filling the first row i.e, first value of all columns with max item val
    for (int j = 1; j < numberOfItems; j++) {
      dp[0][j] = Math.max(dp[0][j - 1], items[j]);
    }
    // For each day
    for (int i = 1; i < days; i++) {
      // For each item
      for (int j = i; j < numberOfItems; j++) {
        // init day max of current item.
        int dayMax = items[j];
        // init min cost to previous day item j-1 + dayMax
        int minCost = dp[i-1][j-1] + dayMax;

        // loop over the items from back, updating dayMax and recalculating the minCost
        for (int k = j-1; k >= i; k--) {
          // update max of items j where k <= j < numberOfItems
          dayMax = Math.max(dayMax, items[k]);
          // update minCost of dayMax plus previous day of items j s.t. k < j - min of sum of maximums
          minCost = Math.min(minCost, dp[i-1][k-1] + dayMax);
        }
        // Set minCost of day i for all items up to j
        dp[i][j] = minCost;
      }
    }
    // return final minCost of all items at last day
    return dp[days-1][numberOfItems-1];
  }

  public static void main(String[] args) {
    int[] arr = new int[]{10, 2, 20, 5, 15, 10, 1};
    int d1 =3, d2=5;
    MinimumTotalContainerSize minimumTotalContainerSize = new MinimumTotalContainerSize();
    int result = minimumTotalContainerSize.findMinTotalContainerSize(arr, d1);
    System.out.println("Expected = 31, Actual = "+result);
    int result2 = minimumTotalContainerSize.findMinTotalContainerSize(arr, d2);
    System.out.println("Expected = 43, Actual = "+result2);
    int[] arr2 =  new int[]{5, 4, 2, 4, 3, 4, 5, 4};
    int d3 = 4;
    int result3 = minimumTotalContainerSize.findMinTotalContainerSize(arr2, d3);
    System.out.println("Expected = 16, Actual = "+result3);
    int[] arr3 = new int[]{22, 12, 1, 14, 17};
    int d4 = 2;
    int result4 = minimumTotalContainerSize.findMinTotalContainerSize(arr3, d4);
    System.out.println("Expected = 39, Actual = "+result4);
  }
}
