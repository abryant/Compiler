package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public enum RelationalExpressionType
{
  LESS_THAN(" < "),
  GREATER_THAN(" > "),
  LESS_THAN_EQUAL(" <= "),
  GREATER_THAN_EQUAL(" >= ");

  private String operatorString;

  private RelationalExpressionType(String operatorString)
  {
    this.operatorString = operatorString;
  }

  /**
   * @return the string representation of the operator used in this RelationalExpressionType
   */
  public String getOperatorString()
  {
    return operatorString;
  }
}
