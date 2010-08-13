package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ElseIfClause
{

  private Expression condition;
  private Block block;

  private ParseInfo parseInfo;

  /**
   * Creates a new ElseIfClause with the specified condition and block
   * @param condition - the condition that must be satisfied for this else if clause to execute
   * @param block - the block that this else if clause executes
   * @param parseInfo - the parsing information
   */
  public ElseIfClause(Expression condition, Block block, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.condition = condition;
    this.block = block;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
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
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "else if " + condition + "\n" + block;
  }

}
