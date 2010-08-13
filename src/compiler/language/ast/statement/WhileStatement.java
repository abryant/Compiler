package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class WhileStatement extends Statement
{

  private Expression condition;
  private Block block;

  /**
   * @param condition - the condition of this while loop
   * @param block - the block that this while loop executes
   * @param parseInfo - the parsing information
   */
  public WhileStatement(Expression condition, Block block, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.condition = condition;
    this.block = block;
  }

  /**
   * @return the condition
   */
  public Expression getCondition()
  {
    return condition;
  }

  /**
   * @return the block
   */
  public Block getBlock()
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
