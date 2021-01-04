/*
A Warehouse manager needs to create a shipment to fill a truck. All the products in the warehouse are in boxes of the
same size. Each product is packed in some number of units per box.

Given the number of boxes, the truck can hold, write an algorithm to determine the maximum number of units of any mix
of products that can be shipped.

Input
The input consists of five arguments:
1. num: an integer representing the number of products
2. boxes: a list of integers representing the number of available boxes for products
3. unitSize: an integer representing size of unitsPerBox
4. unitsPerBox: a list of integers representing the number of units packed in each box
5. truckSize: an integer representing the number of boxes the truck can carry.

Output
Return an integer representing the maximum units that can be carried by truck.

Constraints
1. 1 <= |boxes| <= 10^5
2. |boxes| == |unitsPerBox|
3. 1 <= boxes[i] <= 10^7
4. 1 <= i <= |boxes|
5. 1 <= unitsParBox[i] <= 10^5
6. 1 <= j <= |unitsPerBox|
7. 1 <= truckSize <= 10 ^ 8

Examples
Example 1:
Input:
num = 3
boxes = [1, 2, 3]
unitSize = 3
unitsPerBox = [3, 2, 1]
truckSize = 3

Output: 7
Explanation:
Product 0: because boxes[0] = 1, we know there is 1 box in product 0. And because unitsPerBox[0] = 3,
we know there is 1 box with 3 units in product 0.

Product 1: 2 boxes with 2 units each

Product 2: 3 boxes with 1 unit each

Finally, we have packed products like a list: [3, 2, 2, 1, 1, 1]
The truckSize is 3, so we pick the top 3 from the above list, which is [3, 2, 2], and return the sum 7.
The maximum number of units that can be shipped = 3 + 2 + 2 = 7 units
 */

import java.util.ArrayList;
import java.util.Collections;

public class FillTheTruck {
  public int fillTheTruck(int num, int[] boxes, int unitSize, int[] unitsPerBox, long truckSize) {
    ArrayList<Integer> containers = new ArrayList<>();
    for(int i=0; i<num; i++) {
      for(int j=0; j<boxes[i]; j++) {
        containers.add(unitsPerBox[i]);
      }
    }
    containers.sort(Collections.reverseOrder());
    int result = 0, containerNum = 0;
    for(int i=0; i<truckSize; i++) {
      if(containerNum<containers.size()) {
        result = result+containers.get(containerNum);
        containerNum++;
      } else {
        return result;
      }
    }
    return result;
  }

  public static void main(String[] args) {
    FillTheTruck fillTheTruck = new FillTheTruck();
    int test1 = fillTheTruck.fillTheTruck(3, new int[]{1, 2, 3}, 3, new int[]{3, 2, 1}, 3);
    System.out.println("Expected = 7, Actual = "+test1);
    int test2 = fillTheTruck.fillTheTruck(3, new int[]{3, 1, 2}, 3, new int[]{1, 2, 3}, 4);
    System.out.println("Expected = 9, Actual = "+test2);
  }
}
