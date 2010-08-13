package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DoStatement extends Statement
{

  private Block block;
  private Expression condition;

  /**
   * @param block - the block to execute in this do loop
   * @param condition - the condition of this do loop
   * @param parseInfo - the parsing information
   */
  public DoStatement(Block block, Expression condition, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.block = block;
    this.condition = condition;
  }

  /**
   * @return the block
   */
  public Block getBlock()
  {
    return block;
  }

  /**
   * @return the condition
   */
  public Expression getCondition()
  {
    return condition;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "do\n" + block + "\nwhile " + condition + ";";
  }
}
