/*
Your Amazonian team is responsible for maintaining a monetary transaction service. The transactions are tracked in a
log file. A log file is provided as a string array where each entry represents a transaction to service. Each
transaction consists of:

1. sender_user_id, Unique identifier for the user that initiated the transaction.
   It consists of only digits with at most 9 digits.
2. recipient_user_id: Unique identifier for the user that is receiving the transaction.
   It consists of only digits with at most 9 digits.
3. amount_of_transaction: The amount of the transaction.
   It consists of only digits with at most 9 digits.

The values are separated by a space. For example, "sender_user_id recipient_user_id amount_of_transaction".
Users that perform an excessive amount of transactions might be abusing the service so you have been tasked to identify
the users that have a number of transactions over a threshold. The list of user ids should be ordered in ascending
numeric value.

Example:
logs = ["88 99 200", "88 99 300", "99 32 100", "12 12 15"]
threshold = 2

The transactions count for each user, regardless of role are:
ID       Transactions
---       --------------
99       3
88       2
12       1
32       1

There are two users with at least threshold = 2 transactions: 99 and 88.
In ascending order, the return array is ['88', '99'].
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TransactionLogs {

  public String[] processLogFile(String[] logs, int threshold) {
    HashMap<String, Integer> transactionCounts = new HashMap<>();
    for (String log : logs) {
      String[] tokens = log.split(" ");
      String uid1 = tokens[0];
      String uid2 = tokens[1];
      if (uid1.equals(uid2)) {
        if (transactionCounts.containsKey(uid1)) {
          transactionCounts.put(uid1, transactionCounts.get(uid1) + 1);
        }
        else {
          transactionCounts.put(uid1, 1);
        }
      }
      else {
        if (transactionCounts.containsKey(uid1)) {
          transactionCounts.put(uid1, transactionCounts.get(uid1) + 1);
        }
        if (!transactionCounts.containsKey(uid1)) {
          transactionCounts.put(uid1, 1);
        }
        if (transactionCounts.containsKey(uid2)) {
          transactionCounts.put(uid2, transactionCounts.get(uid2) + 1);
        }
        if (!transactionCounts.containsKey(uid2)) {
          transactionCounts.put(uid2, 1);
        }
      }
    }

    List<String> result = new ArrayList<>();
    for (String id : transactionCounts.keySet()) {
      if (transactionCounts.get(id) >= threshold) {
        result.add(id);
      }
    }

    String[] finalRes = new String[result.size()];
    finalRes = result.toArray(finalRes);
    return finalRes;
  }

  public static void main(String[] args) {
    TransactionLogs transactionLogs = new TransactionLogs();
    String[] result = transactionLogs.processLogFile(new String[]{"88 99 200", "88 99 300", "99 32 100",
                                                                  "12 12 15"}, 2);
    System.out.println("Expected = [88, 99] Actual = " + Arrays.toString(result));
  }
}
