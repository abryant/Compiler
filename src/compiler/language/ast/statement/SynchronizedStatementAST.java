package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SynchronizedStatementAST extends StatementAST
{

  private ExpressionAST expression;
  private BlockAST block;

  /**
   * Creates a new SynchronizedStatementAST which synchronizes its block on the result of the specified expression.
   * @param expression - the expression which returns the object to synchronize on, or null if no expression was specified
   * @param block - the block to synchronize
   * @param parseInfo - the parsing information
   */
  public SynchronizedStatementAST(ExpressionAST expression, BlockAST block, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
    this.block = block;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @return the block
   */
  public BlockAST getBlock()
  {
    return block;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    if (expression == null)
    {
      return "synchronized\n" + block;
    }
    return "synchronized " + expression + "\n" + block;
  }
}
