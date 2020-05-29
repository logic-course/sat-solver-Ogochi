package ogochi.sat;

public class SatChecker {
  
  public boolean IsSatisfiable(Formula formula) {
    var cnf = new CnfConverter().Convert(formula);
    return false;
  }
}