package ogochi.sat;

import java.util.HashSet;

public class CnfConverter {
  
  public CnfFormula Convert(Formula formula) {

    var negatedFormula = NegationForm(formula, false);
    var convertedFormula = ApplyDistributiveLaw(negatedFormula);
    var cnfFormula = new CnfFormula();
    ConvertedFormulaToCnfFormula(convertedFormula, cnfFormula, true);
    
    return cnfFormula;
  }

  private void ConvertedFormulaToCnfFormula(Formula convertedFormula, CnfFormula result, boolean isFirst) {
    if (convertedFormula == null) {
      return;
    }

    if (convertedFormula.type == FormulaType.Var) {
      var newVar = new Var(convertedFormula.varName, convertedFormula.negated);

      if (isFirst) {
        var entry = new HashSet<Var>();
        entry.add(newVar);

        result.subFormulas.add(entry);
      } else {
        result.subFormulas.get(result.subFormulas.size() - 1).add(newVar);
      }
      return;
    }

    if (convertedFormula.type == FormulaType.And) {
      ConvertedFormulaToCnfFormula(convertedFormula.val1, result, true);
      ConvertedFormulaToCnfFormula(convertedFormula.val2, result, true);
      return;
    }

    if (isFirst) {
      result.subFormulas.add(new HashSet<>());
    }

    ConvertedFormulaToCnfFormula(convertedFormula.val1, result, false);
    ConvertedFormulaToCnfFormula(convertedFormula.val2, result, false);
  }

  private Formula ApplyDistributiveLaw(Formula formula) {
    if (formula == null) {
      return null;
    }

    if (formula.type != FormulaType.Or ||
        (formula.val1.type != FormulaType.And && formula.val1.type != FormulaType.And)) {
        formula.val1 = ApplyDistributiveLaw(formula.val1);
        formula.val2 = ApplyDistributiveLaw(formula.val2);
        return formula;
    }

    if (formula.val2.type == FormulaType.And) {
      var f = formula.val1;
      formula.val1 = formula.val2;
      formula.val2 = f;
    }

    var newFormula = new Formula(
      FormulaType.And,
      new Formula(FormulaType.Or, formula.val2, formula.val1.val1),
      new Formula(FormulaType.Or, formula.val2, formula.val1.val2)
    );

    return ApplyDistributiveLaw(newFormula);
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