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
}
