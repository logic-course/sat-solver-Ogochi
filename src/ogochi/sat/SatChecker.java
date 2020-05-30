package ogochi.sat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SatChecker {
  
  public boolean IsSatisfiable(Formula formula) {
    var cnf = new CnfConverter().Convert(formula);
    return DPLL(cnf);
  }

  private boolean DPLL(CnfFormula formula) {
    if (formula.subFormulas.isEmpty()) {
      return true;
    }

    var isOnlyLiterals = true;
    var unitFormulas = new HashSet<Var>();

    var iter = formula.subFormulas.iterator();
    while (iter.hasNext()) {
      var curr = iter.next();

      if (curr.isEmpty()) {
        return false;
      }

      if (curr.size() == 1) {
        unitFormulas.add(curr.stream().findFirst().get());
        iter.remove();
      } else {
        isOnlyLiterals = false;
      }
    }

    if (isOnlyLiterals) {
      return true;
    }

    if (!unitFormulas.isEmpty()) {
      var isStillPossible = removeAssigned(formula, unitFormulas);
      if (!isStillPossible) {
        return false;
      }
    }

    return SplitFormula(formula);
  }

  private boolean SplitFormula(CnfFormula formula) {
    var selected = formula.subFormulas.stream().flatMap(s -> s.stream()).findAny();
    if (!selected.isPresent()) {
      return DPLL(formula);
    }
    
    var copy1 = formula.clone();
    var isStillPossible1 = removeAssigned(copy1, new HashSet<Var>(Arrays.asList(selected.get())));

    var copy2 = formula.clone();
    var isStillPossible2 = removeAssigned(copy2, new HashSet<Var>(Arrays.asList(selected.get().getNegatedVar())));

    return (isStillPossible1 && DPLL(copy1)) || (isStillPossible2 && DPLL(copy2));
  }

  private boolean removeAssigned(CnfFormula formula, Set<Var> assigned) {
    var containing = new HashSet<Set<Var>>();

    var iter = formula.subFormulas.iterator();
    while (iter.hasNext()) {
      var subFormula = iter.next();

      if (subFormula.isEmpty()) {
        continue;
      }
      
      var removed = false;
      for (var variable : subFormula) {
        if (assigned.contains(variable)) {
          iter.remove();
          removed = true;
          break;
        }
      }

      if (!removed) {
        for (var variable : subFormula) {
          if (assigned.contains(variable.getNegatedVar())) {
            containing.add(subFormula);
          }
        }
      }
    }

    var containingArr = containing.stream().collect(Collectors.toList());
    var negAssigned = assigned.stream().map(v -> v.getNegatedVar()).collect(Collectors.toSet());
    for (int i = 0; i < containingArr.size(); i++) {
      containingArr.get(i).removeAll(negAssigned);
    }

    return true;
  }
}