package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 16 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class NormalParameter extends Parameter
{

  private Expression expression;

  /**
   * Creates a new normal parameter with the specified expression
   * @param expression - the expression to create this normal parameter with
   * @param parseInfo - the parsing information
   */
  public NormalParameter(Expression expression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
  }

  /**
   * @return the expression
   */
  public Expression getExpression()
  {
    return expression;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return String.valueOf(expression);
  }
}
