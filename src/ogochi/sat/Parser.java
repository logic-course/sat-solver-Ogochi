package ogochi.sat;

public class Parser {
  private int parsingPos = 0;

  public Formula ParseInput(String input) {
    parsingPos = 0;

    var tokens = input.split(" ");
    
    var formula = new Formula();
    Parse(tokens, formula);

    return formula;
  }

  private void Parse(String[] tokens, Formula current) {
    var curr = tokens[parsingPos];
    while (curr.charAt(0) == ')') {
      parsingPos++;
      curr = tokens[parsingPos];
    }

    var f1 = new Formula();
    var f2 = new Formula();

    if (curr.charAt(0) == '(') {
      curr = curr.substring(1);
    }

    switch (curr) {
      case "T":
        current.type = FormulaType.T;
        parsingPos++;
        break;
      case "F":
        current.type = FormulaType.F;
        parsingPos++;
        break;
      case "Var":
        current.type = FormulaType.Var;
        parsingPos++;
        current.varName = tokens[parsingPos].split("\"")[1];
        parsingPos++;
        break;
      case "Not":
        current.type = FormulaType.Not;
        current.val1 = f1;
        parsingPos++;
        Parse(tokens, f1);
        break;
      case "And":
        current.type = FormulaType.And;
        current.val1 = f1;
        current.val2 = f2;
        parsingPos++;
        Parse(tokens, f1);
        Parse(tokens, f2);
        break;
      case "Or":
        current.type = FormulaType.Or;
        current.val1 = f1;
        current.val2 = f2;
        parsingPos++;
        Parse(tokens, f1);
        Parse(tokens, f2);
        break;
      case "Implies":
        current.type = FormulaType.Imp;
        current.val1 = f1;
        current.val2 = f2;
        parsingPos++;
        Parse(tokens, f1);
        Parse(tokens, f2);
        break;
      case "Iff":
        current.type = FormulaType.Iff;
        current.val1 = f1;
        current.val2 = f2;
        parsingPos++;
        Parse(tokens, f1);
        Parse(tokens, f2);
        break;
    }
  }
}