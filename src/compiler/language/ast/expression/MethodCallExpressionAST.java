package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ParameterAST;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class MethodCallExpressionAST extends StatementExpressionAST
{

  private ExpressionAST expression = null;
  private ParameterAST[] parameters;

  /**
   * Creates a new MethodCallExpressionAST which calls the specified expression with the specified parameters
   * @param expression - the expression which produces a method/closure to call
   * @param parameters - the parameters to the method
   * @param parseInfo - the parsing information
   */
  public MethodCallExpressionAST(ExpressionAST expression, ParameterAST[] parameters, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
    this.parameters = parameters;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @return the parameters
   */
  public ParameterAST[] getParameters()
  {
    return parameters;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append(expression);
    buffer.append("(");
    for (int i = 0; i < parameters.length; i++)
    {
      buffer.append(parameters[i]);
      if (i != parameters.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(")");
    return buffer.toString();
  }
}
