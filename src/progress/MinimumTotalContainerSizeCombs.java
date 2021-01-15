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

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class MinimumTotalContainerSizeCombs {

  private int cursor = 0;

  private int numRows = 0;

  private Set<String> combinations = new LinkedHashSet<>();

  public boolean shift(int[] row, int targetCol, int total, int numBuckets) {
    if (targetCol < 0) {
      throw new IllegalArgumentException("Target col must be >= 0");
    }

    if (numBuckets == 1) {
      // only 1 bucket - everything goes here.
      row[targetCol] = total;
      return updateCombinations(row);
    }
    if (targetCol == 0 || row[targetCol] == 1) {
      // done with this row.
      return updateCombinations(row);
    }
    // ex. for total = 7 and numBuckets = 4 with targetCol = 3
    // start with 1 in each of n-1 (3) cells and put the rest in
    // the target col.  This leaves total - numBuckets - 1 (7 - (4 - 1)) = 4
    // things in the target col.
    row[targetCol] = total - (numBuckets - 1);
    // the rest go into the remaining colums.
    int toDistribute = total - row[targetCol];
    return shift(row, targetCol - 1, toDistribute, (numBuckets - 1));
  }

  private boolean updateCombinations(int[] row) {
    String combo = Arrays.toString(row);
    if(combinations.add(combo)) {
      numRows++;
      return true;
    }
    return false;
  }

  private static long factorial(int n) {
    if (n > 20) {
      throw new UnsupportedOperationException("n is too big");
    }
    long retval = 1;
    for (int i = 2; i <= n; i++) {
      retval *= i;
    }
//    System.out.println(n + " factorial is " + retval);
    return retval;
  }

  // works for n < 20
  private static long combination(int n, int r) {
    if (r > n || n <= 0 || r <= 0) {
      throw new IllegalArgumentException("r must be smaller than n and both n and r must be positive ints");
    }
    long result = 1;
    for (int i = r + 1; i <= n; i++) {
      result = result * i;
    }
    long nMinusRFactorial = factorial(n - r);
    result = result / nMinusRFactorial;
//    System.out.println("C(" + n + ", " + r + ") = " + result);
    return result;
  }

  public static void main(String[] args) {
    int[] arr1 = new int[]{10, 2, 20, 5, 15, 10, 1};
    // initial value
    int numBuckets = 5;
    int expected = 43;
    calculateMinCost(arr1, numBuckets, expected);
    numBuckets = 3;
    expected = 31;
    calculateMinCost(arr1, numBuckets, expected);
    int[] arr2 =  new int[]{5, 4, 2, 4, 3, 4, 5, 4};
    numBuckets = 4;
    expected = 16;
    calculateMinCost(arr2, numBuckets, expected);

    int[] arr3 = new int[]{22, 12, 1, 14, 17};
    numBuckets = 2;
    expected = 39;
    calculateMinCost(arr3, numBuckets, expected);
  }

  private static void calculateMinCost(int[] arr, int numBuckets, int expected) {
    int numCombos = (int) combination(arr.length, numBuckets);
    int[][] combinationMatrix = new int[numCombos][numBuckets];
    int[] buckets;
    MinimumTotalContainerSizeCombs minimumTotalContainerSize = new MinimumTotalContainerSizeCombs();
    // initialize.
    minimumTotalContainerSize.cursor = 0;
    minimumTotalContainerSize.numRows = 0;
    minimumTotalContainerSize.shift(combinationMatrix[0], numBuckets - 1, arr.length, numBuckets);
//    System.out.println("Buckets (startRow: " + minimumTotalContainerSize.combinations.size() + ")\t:" +
//                       Arrays.toString(buckets));
    int targetCol;
    int lastColVal;
    // 1, 1, 1, 4
    // now take 1 from the last col.
    int currentCursor = minimumTotalContainerSize.cursor + 1;
    int currentRow = minimumTotalContainerSize.cursor;
    int [] cost = new int[numCombos];
    cost[0] = computeCost(arr, combinationMatrix[0]);
    long start = System.nanoTime();
    while(currentRow < minimumTotalContainerSize.numRows) {
      buckets = combinationMatrix[currentRow];
      targetCol = numBuckets - 1;
      while(targetCol > 0) {
        lastColVal = buckets[targetCol];
        while (lastColVal > 1) {
          int[] combo = combinationMatrix[currentCursor++];
          // 1, 1, 2, 3 -> divide 7-(3+1) = 3 between 1, 1
          // take 1 out of the lastcol.
          // copy the values to the right of the target col. (numBuckets = 4, targetCol = 2 => copy 1)
          // copy targetCol + 1 -> numBuckets - (targetCol+1)
          int numItemsToCopy = numBuckets - (targetCol + 1);
          if (numItemsToCopy > 0) {
            // 4, 3 => numItemsToCopy = 4 - 4 = 0, 4, 2 => numItemsToCopy = 4 - 3 = 1
            // startPos = numBuckets - numItemsToCopy. 4, 2 => 4 - 1 = 3
            int startPos = numBuckets - numItemsToCopy;
            System.arraycopy(buckets, startPos, combo, startPos, numItemsToCopy);
          }
          combo[targetCol] = lastColVal - 1;
          int newCount = getItemCount(combo, arr.length, targetCol);
          if (minimumTotalContainerSize.shift(combo, targetCol - 1, newCount, targetCol)) {
//            System.out.println("Buckets (startRow: " + minimumTotalContainerSize.combinations.size() + ")\t:" +
//                               Arrays.toString(combo));
            // compute the cost of this combination.
            cost[currentCursor-1] = computeCost(arr, combo);
          }
          else {
            // didn't get a unique combo.  Move cursor back and reset it back.
            currentCursor--;
            Arrays.fill(combo, 0);
          }
          lastColVal--;
        }
        targetCol--;
      }
      currentRow++;
    }
    long end = System.nanoTime();
    System.out.println("Calculated Minimum Cost:" + getMinimumCost(cost, currentRow) + ", Expected:" + expected +
                       "; Time: " + (end - start));
  }

  private static int getMinimumCost(int[] cost, int count) {
    int retval = Integer.MAX_VALUE;
    for(int i = 0; i < count; i++) {
      int value = cost[i];
      if (value < retval) {
        retval = value;
      }
    }
    return retval;
  }

  private static int computeCost(int[] arr, int[] combo) {
    // Compute the Max value in each partitions.
    int [] cost = new int[combo.length];
    int startCol = 0;
    for(int i = 0; i < combo.length; i++) {
      cost[i] = getCostForPartition(arr, startCol, combo[i]);
      startCol += combo[i];
    }
    // get total cost
    int retval = 0;
    for (int value : cost) {
      retval += value;
    }
    return retval;
  }

  private static int getCostForPartition(int[] arr, int startCol, int itemCount) {
    int retval = Integer.MIN_VALUE;
    for(int i = startCol; i < startCol+itemCount; i++) {
      if(arr[i] > retval) {
        retval = arr[i];
      }
    }
    return retval;
  }

  private static int getItemCount(int [] array, int total, int targetCol) {
    int itemsAccountedFor = 0;
    for(int i = targetCol; i < array.length; i++) {
      itemsAccountedFor += array[i];
    }
    return total - itemsAccountedFor;
  }

}
