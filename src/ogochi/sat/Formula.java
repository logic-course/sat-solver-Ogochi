package ogochi.sat;

public class Formula {
  FormulaType type;
  String varName;
  Formula val1, val2;
  boolean negated = false;

  public Formula () {}

  public Formula (FormulaType type, String varName, Formula val1, Formula val2) {
    this.type = type;
    this.varName = varName;
    this.val1 = val1;
    this.val2 = val2;
  }

  public Formula (FormulaType type) {
    this.type = type;
  }

  public Formula (String varName) {
    this(FormulaType.Var, varName, null, null);
  }

  public Formula (String varName, boolean negated) {
    this(varName);
    this.negated = negated;
  }

  public Formula (FormulaType type, Formula val1) {
    this(type, null, val1, null);
  }

  public Formula (FormulaType type, Formula val1, Formula val2) {
    this(type, null, val1, val2);
  }

  public String toString() {
    return (negated ? "~" : "") + type.name() +
      (varName != null ? "'" + varName + "'" : "") + 
      (val1 != null ? " (" + (val1 == null ? "" : val1) + " , " + (val2 == null ? "" : val2) + " )" : "");
  }
}

