package ogochi.sat;

public class Var {
  String name;
  boolean isNegated;

  public Var(String name, boolean isNegated) {
    this.name = name;
    this.isNegated = isNegated;
  }

  public Var getNegatedVar() {
    return new Var(name, !isNegated);
  }

  @Override
  public String toString() {
    return (isNegated ? "~" : "") + "\"" + name + "\"";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isNegated ? 1231 : 1237);
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Var other = (Var) obj;
    if (isNegated != other.isNegated)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}