package ogochi.sat;

import java.util.Scanner;

public class SatSolver {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    var input = sc.nextLine();
    sc.close();

    var formula = new Parser().ParseInput(input);
    var result = new SatChecker().IsSatisfiable(formula);

    System.out.print(result ? 1 : 0);
  }
}
