package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * The abstract base class of all expressions that can also be used as statements (via ExpressionStatementAST).
 * @author Anthony Bryant
 */
public abstract class StatementExpressionAST extends ExpressionAST
{

  /**
   * Creates a new StatementExpressionAST with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public StatementExpressionAST(ParseInfo parseInfo)
  {
    super(parseInfo);
  }

}
