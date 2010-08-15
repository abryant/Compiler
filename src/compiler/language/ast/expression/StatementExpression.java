package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * The abstract base class of all expressions that can also be used as statements (via ExpressionStatement).
 * @author Anthony Bryant
 */
public abstract class StatementExpression extends Expression
{

  /**
   * Creates a new StatementExpression with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public StatementExpression(ParseInfo parseInfo)
  {
    super(parseInfo);
  }

}
