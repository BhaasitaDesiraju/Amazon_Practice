package progress;

import java.util.*;

public class FiveStarSellers {
  private static int fiveStarReviews(int[][] productRatings, int ratingsThreshold) {
  int additionalReviews = 0;

  class CompareDoubles implements Comparator<Double[]> {
    @Override
    public int compare(Double[] value1, Double[] value2) {
      return value1[0].compareTo(value2[0]);
    }
  }

  Queue<Double[]> priorityQueue = new PriorityQueue<>(new CompareDoubles());
  double grandTotal = 0;
    for (int i = 0; i < productRatings.length; i++) {
    int[] productRating = productRatings[i];
    Double percentage = oldThresholdPercent(productRating);
    Double incrementalPercentage = newThresholdPercent(productRating);
    grandTotal += percentage;
    priorityQueue.add(new Double[]{(percentage - incrementalPercentage), (double) i});
  }
  double grandPercentage = (grandTotal / productRatings.length) * 100;
    while (grandPercentage < ratingsThreshold) {
    Double[] least = priorityQueue.poll();
      int[] arr = new int[0];
      if (least != null) {
        arr = productRatings[least[1].intValue()];
      }
      grandTotal += newThresholdPercent(arr) - oldThresholdPercent(arr);
    grandPercentage = (grandTotal / productRatings.length) * 100;
    arr[0]++;
    arr[1]++;
      if (least != null) {
        priorityQueue.add(new Double[]{oldThresholdPercent(arr) - newThresholdPercent(arr), least[1]});
      }
      additionalReviews++;
  }
    return additionalReviews;
}

  private static double oldThresholdPercent(int[] productRating) {
    return ((double) productRating[0] / (double) productRating[1]);
  }

  private static double newThresholdPercent(int[] productRating) {
    return ((double) productRating[0] + 1) / ((double) productRating[1] + 1);
  }

  public static void main(String[] args) {
    int[][] products = {{4, 4}, {1, 2}, {3, 6}};
    System.out.println(fiveStarReviews(products, 77));
  }
}

//import java.util.ArrayList;
//import java.util.Random;
//
//public class progress.FiveStarSellers {
//  public int fiveStarReviews(int[][] productRatings, int ratingsThreshold) {
//    ArrayList<ArrayList<Integer>> notSoPerfectRatings = new ArrayList<>();
//    double rating = 0.0;
//    int additionalReviews = 0;
//    for (int[] productRating : productRatings) {
//      rating = rating + ((double) productRating[0] / (double) productRating[1]);
//      if (productRating[0] != productRating[1]) {
//        ArrayList<Integer> pRatings = new ArrayList<>();
//        pRatings.add(productRating[0]);
//        pRatings.add(productRating[1]);
//        notSoPerfectRatings.add(pRatings);
//      }
//    }
//    double ratingPercent = (rating * 100) / productRatings.length;
//    if (ratingPercent >= ratingsThreshold) {
//      return 0;
//    }
//    else {
//      while (ratingPercent < ratingsThreshold) {
//        ArrayList<Integer> improveRatings = notSoPerfectRatings.get(0);
//        rating = rating  - (((double) improveRatings.get(0)) / ((double) improveRatings.get(1))) + (((double) improveRatings.get(0) + 1) / ((double) improveRatings.get(1)+1));
//        additionalReviews++;
//        ratingPercent = (rating * 100) / productRatings.length;
//      }
//    }
//    return additionalReviews;
//  }
//
//  private ArrayList<Integer> getNotSoPerfectRatings(ArrayList<ArrayList<Integer>> notSoPerfectRatings) {
//    Random random = new Random();
//    random.
//    ArrayList<Integer> notSoPerfectRating = new ArrayList<>();
//    return notSoPerfectRating;
//  }
//
//  public static void main(String[] args) {
//    progress.FiveStarSellers fiveStarSellers = new progress.FiveStarSellers();
//    int reviews = fiveStarSellers.fiveStarReviews(new int[][]{new int[]{4, 4}, new int[]{1, 2}, new int[]{3, 6}},
//                                                  77);
//    System.out.println("Expected = 3, Actual = "+reviews);
//  }
//}

