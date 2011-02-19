package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public enum RelationalExpressionTypeAST
{
  LESS_THAN(" < "),
  GREATER_THAN(" > "),
  LESS_THAN_EQUAL(" <= "),
  GREATER_THAN_EQUAL(" >= ");

  private String operatorString;

  private RelationalExpressionTypeAST(String operatorString)
  {
    this.operatorString = operatorString;
  }

  /**
   * @return the string representation of the operator used in this RelationalExpressionTypeAST
   */
  public String getOperatorString()
  {
    return operatorString;
  }
}
