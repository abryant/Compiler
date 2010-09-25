package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class WhileStatementAST extends StatementAST
{

  private ExpressionAST condition;
  private BlockAST block;

  /**
   * @param condition - the condition of this while loop
   * @param block - the block that this while loop executes
   * @param parseInfo - the parsing information
   */
  public WhileStatementAST(ExpressionAST condition, BlockAST block, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.condition = condition;
    this.block = block;
  }

  /**
   * @return the condition
   */
  public ExpressionAST getCondition()
  {
    return condition;
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
    return "while " + condition + "\n" + block;
  }

}
