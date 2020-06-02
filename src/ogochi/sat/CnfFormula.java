package ogochi.sat;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CnfFormula implements Cloneable {
  List<Set<Var>> subFormulas = new LinkedList<>();

  @Override
  public String toString() {
    var sb = new StringBuilder();

    for (var subFormula : subFormulas) {
      var next = subFormula.stream().map(v -> v.toString()).collect(Collectors.joining(", "));
      sb.append(next + "\n");
    }

    return sb.toString();
  }

  public CnfFormula clone() {
    var result = new CnfFormula();
    for (var subFormula : subFormulas) {
      var newSubformula = new HashSet<Var>();
      newSubformula.addAll(subFormula
        .stream()
        .map(v -> new Var(v.name, v.isNegated))
        .collect(Collectors.toSet()));

      result.subFormulas.add(newSubformula);
    }

    return result;
  }

  public Set<Var> GetLiterals() {
    return subFormulas.stream().flatMap(s -> s.stream()).collect(Collectors.toSet());
  }
}