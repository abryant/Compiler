package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class NilLiteralExpressionAST extends ExpressionAST
{

  /**
   * Creates a new NilLiteralExpressionAST
   * @param parseInfo - the parsing information
   */
  public NilLiteralExpressionAST(ParseInfo parseInfo)
  {
    super(parseInfo);
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "nil";
  }
}
