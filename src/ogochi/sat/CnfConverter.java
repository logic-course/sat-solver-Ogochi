package ogochi.sat;

public class CnfConverter {
  
  public Formula Convert(Formula formula) {

    System.out.println(NegationForm(formula, false));
    return formula;
  }

  private Formula NegationForm(Formula formula, boolean shallNegate) {
    if (formula.type == FormulaType.Not) {
        return NegationForm(formula.val1, !shallNegate);
    }

    if (shallNegate) {
      switch (formula.type) {
        case And:
        return new Formula(FormulaType.Or, NegationForm(formula.val1, true), NegationForm(formula.val2, true));
        case F:
          return new Formula(FormulaType.T);
        case Iff:
          return NegationForm(
            new Formula(
              FormulaType.And,
              new Formula(FormulaType.Imp, formula.val1, formula.val2),
              new Formula(FormulaType.Imp, formula.val2, formula.val1)),
            true);
        case Imp:
          return new Formula(FormulaType.Or, NegationForm(formula.val1, true), NegationForm(formula.val2, false));
        case Or:
          return new Formula(FormulaType.And, NegationForm(formula.val1, true), NegationForm(formula.val2, true));
        case T:
          return new Formula(FormulaType.F);
        case Var:
          return new Formula(formula.varName, true);
      }
    }

    switch (formula.type) {
      case And:
        formula.val1 = NegationForm(formula.val1, false);
        formula.val2 = NegationForm(formula.val2, false);
        break;
      case Iff:
        formula.val1 = NegationForm(formula.val1, false);
        formula.val2 = NegationForm(formula.val2, false);
        break;
      case Imp:
        formula.val1 = NegationForm(formula.val1, false);
        formula.val2 = NegationForm(formula.val2, false);
        break;
      case Not:
        formula.val1 = NegationForm(formula.val1, false);
        break;
      case Or:
        formula.val1 = NegationForm(formula.val1, false);
        formula.val2 = NegationForm(formula.val2, false);
        break;
      default:
        break;
    }

    return formula;
  }
}