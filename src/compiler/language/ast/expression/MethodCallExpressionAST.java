package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ArgumentAST;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class MethodCallExpressionAST extends StatementExpressionAST
{

  private ExpressionAST expression = null;
  private ArgumentAST[] arguments;

  /**
   * Creates a new MethodCallExpressionAST which calls the specified expression with the specified arguments
   * @param expression - the expression which produces a method/closure to call
   * @param arguments - the arguments to the method
   * @param parseInfo - the parsing information
   */
  public MethodCallExpressionAST(ExpressionAST expression, ArgumentAST[] arguments, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
    this.arguments = arguments;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @return the arguments
   */
  public ArgumentAST[] getArguments()
  {
    return arguments;
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
    for (int i = 0; i < arguments.length; i++)
    {
      buffer.append(arguments[i]);
      if (i != arguments.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(")");
    return buffer.toString();
  }
}
