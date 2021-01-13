/*
There is a program for recording internal debts across teams, which can show all records of debts between the team
members. Given the team debt records ( in the format of the borrower name, lender name, and debt amount), return the
people with the smallest negative balance.

Notes:
-2 is smaller than -1.
If multiple people have the smallest negative balance, return the list in alphabetical order.
If nobody has a negative balance, return the list consisting of string "Nobody has a negative balance".
Write an algorithm to find who has the smallest negative balance.

Input:
The input to the function/method consists of three arguments:
numRecords, an integer representing the number of debt records.
debts, a list of triplet representing debtRecord consisting of a string borrower, a string lender, and an integer
amount, representing the debt record.

Output:
Return a list of strings representing an alphabetically ordered list of members with the smallest negative balance.
If no team member has a negative balance then return a list containing the string "Nobody has a negative balance".

Constraints:
numRecords ≤ 200,000
1 ≤ amount in debts ≤ 1000
1 ≤ length of borrower and lender in debts ≤ 20

Example:
borrower	lender	amount
William	  Jackson	  2
Jackson 	William	  2
Madison	  William	  5
Jackson	  Madison	  7
William	  Jackson	  4
William	  Madison	  4

The first, fifth, and sixth entries decrease William's balance because William is a borrower.
The second and third entries increase because William is a lender. So, William's balance is
(2+5) - (2+4+4) = 7 - 10 = -3.
Jackson is lender in first and fifth entries and a borrower in the second and fourth entries.
Thus, Jackson's balance is (2+4) - (2+7) = 6 - 9 = -3. Madison is a borrower in the third entry and a lender in the
fourth and sixth entries. Thus, Madison's balance is (7 + 4) - 5 = 11 - 6 = 5. Here William and Jackson both have the
balance of -3, which is the minimum amoung all members.
 */

import java.util.*;

public class AmazonDebtRecords {

  public static class debtRecord {
    String borrower = "";
    String lender = "";
    int amount = 0;
    debtRecord() {

    }
    debtRecord(String borrower, String lender, int amount) {
      this.borrower = borrower;
      this.lender = lender;
      this.amount = amount;
    }
  }

  List<String> minimumDebtMembers(List<debtRecord> records){
    HashMap<String, Integer> team = new HashMap<>();
    List<String> negativeBal = new ArrayList<>();
    for(debtRecord record: records) {
      String borrower = record.borrower;
      String lender = record.lender;
      int amount = record.amount;
      if(team.containsKey(borrower)) {
        team.put(borrower, team.get(borrower)-amount);
      }
      if(!team.containsKey(borrower)) {
        team.put(borrower, -1*amount);
      }
      if(team.containsKey(lender)) {
        team.put(lender, team.get(lender)+amount);
      }
      if(!team.containsKey(lender)) {
        team.put(lender, amount);
      }
    }

    //calculate min
    int min=0;
    for(String member: team.keySet()) {
      if(min > team.get(member)) {
        min = team.get(member);
      }
    }
    for(String member: team.keySet()) {
      if(team.get(member) < 0 && team.get(member) == min) {
        negativeBal.add(member);
      }
    }
    Collections.sort(negativeBal);
    if(negativeBal.isEmpty()) {
      negativeBal.add("Nobody has a negative balance");
    }
    return negativeBal;
  }

  public static void main(String[] args) {
    AmazonDebtRecords amazonDebtRecords = new AmazonDebtRecords();
    List<debtRecord> debtRecords = new ArrayList<>();
    debtRecords.add(new debtRecord("Alex", "Blake", 2));
    debtRecords.add(new debtRecord("Blake", "Alex", 2));
    debtRecords.add(new debtRecord("Casey", "Alex", 5));
    debtRecords.add(new debtRecord("Blake", "Casey", 7));
    debtRecords.add(new debtRecord("Alex", "Blake", 4));
    debtRecords.add(new debtRecord("Alex", "Casey", 4));
    List<String> debtMembers = amazonDebtRecords.minimumDebtMembers(debtRecords);
    System.out.println(Arrays.toString(debtMembers.toArray()));
  }
}
