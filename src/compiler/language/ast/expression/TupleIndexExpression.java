package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.IntegerLiteral;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleIndexExpression extends Expression
{

  private Expression expression;
  private IntegerLiteral indexLiteral;

  /**
   * Creates a new TupleIndexExpression with the specified expression and index into the tuple that the expression returns
   * @param expression - the expression to index
   * @param indexLiteral - the index into the tuple, as an IntegerLiteral
   * @param parseInfo - the parsing information
   */
  public TupleIndexExpression(Expression expression, IntegerLiteral indexLiteral, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
    this.indexLiteral = indexLiteral;
  }

  /**
   * @return the expression
   */
  public Expression getExpression()
  {
    return expression;
  }

  /**
   * @return the indexLiteral
   */
  public IntegerLiteral getIndexLiteral()
  {
    return indexLiteral;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return expression + " ! " + indexLiteral;
  }

}
