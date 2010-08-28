package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class IfStatement extends Statement
{

  private Expression condition;
  private Block block;
  private ElseIfClause[] elseIfClauses;
  private Block elseBlock;

  /**
   * Creates a new IfStatement with the specified condition, block, else if clauses, and else block
   * @param condition - the condition that must be true for the first block to be executed
   * @param block - the block to execute if the condition is true
   * @param elseIfClauses - the else if clauses to try before the else block
   * @param elseBlock - the else block to execute if none of the other blocks are executed
   * @param parseInfo - the parsing information
   */
  public IfStatement(Expression condition, Block block, ElseIfClause[] elseIfClauses, Block elseBlock, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.condition = condition;
    this.block = block;
    this.elseIfClauses = elseIfClauses;
    this.elseBlock = elseBlock;
  }

  /**
   * @return the condition
   */
  public Expression getExpression()
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
   * @return the elseIfClauses
   */
  public ElseIfClause[] getElseIfClauses()
  {
    return elseIfClauses;
  }

  /**
   * @return the elseBlock
   */
  public Block getElseBlock()
  {
    return elseBlock;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("if ");
    buffer.append(condition);
    buffer.append("\n");
    buffer.append(block);
    for (int i = 0; i < elseIfClauses.length; i++)
    {
      buffer.append("\n");
      buffer.append(elseIfClauses[i]);
    }
    if (elseBlock != null)
    {
      buffer.append("\nelse\n");
      buffer.append(elseBlock);
    }
    return buffer.toString();
  }
}
