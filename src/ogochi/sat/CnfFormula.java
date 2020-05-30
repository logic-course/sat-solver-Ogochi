package ogochi.sat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CnfFormula {
  List<Set<Var>> subFormulas = new ArrayList<>();

  @Override
  public String toString() {
    var sb = new StringBuilder();

    for (var subFormula : subFormulas) {
      var next = subFormula.stream().map(v -> v.toString()).collect(Collectors.joining(", "));
      sb.append(next + "\n");
    }

    return sb.toString();
  }
}