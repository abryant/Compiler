package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ElseIfClauseAST
{

  private ExpressionAST condition;
  private BlockAST block;

  private ParseInfo parseInfo;

  /**
   * Creates a new ElseIfClauseAST with the specified condition and block
   * @param condition - the condition that must be satisfied for this else if clause to execute
   * @param block - the block that this else if clause executes
   * @param parseInfo - the parsing information
   */
  public ElseIfClauseAST(ExpressionAST condition, BlockAST block, ParseInfo parseInfo)
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
  public ExpressionAST getExpression()
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
    return "else if " + condition + "\n" + block;
  }

}
