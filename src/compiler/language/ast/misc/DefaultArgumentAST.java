package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 16 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class DefaultArgumentAST extends ArgumentAST
{

  private NameAST name;
  private ExpressionAST expression;

  /**
   * Creates a new default argument with the specified name and expression.
   * @param name - the name of the default argument
   * @param expression - the expression for the default argument
   * @param parseInfo - the parsing information
   */
  public DefaultArgumentAST(NameAST name, ExpressionAST expression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.name = name;
    this.expression = expression;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "@" + name + " = " + expression;
  }
}
