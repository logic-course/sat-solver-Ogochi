package ogochi.sat;

public class Formula {
  FormulaType type;
  String varName;
  Formula val1, val2;

  public String toString() {
    return type.name() + (varName != null ? "'" + varName + "'" : "") + 
      (val1 != null ? " (" + (val1 == null ? "" : val1) + " , " + (val2 == null ? "" : val2) + " )" : "");
  }
}

