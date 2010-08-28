package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ForStatement extends Statement
{

  private Statement init;
  private Expression condition;
  private Statement update;
  private Block block;


  /**
   * Creates a new ForStatement with the specified initializer, condition, update and block
   * @param init - the initializer statement
   * @param condition - the for loop condition
   * @param update - the update statement
   * @param block - the block of the loop
   * @param parseInfo - the parsing information
   */
  public ForStatement(Statement init, Expression condition, Statement update, Block block, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.init = init;
    this.condition = condition;
    this.update = update;
    this.block = block;
  }

  /**
   * @return the init
   */
  public Statement getInit()
  {
    return init;
  }

  /**
   * @return the condition
   */
  public Expression getCondition()
  {
    return condition;
  }

  /**
   * @return the update
   */
  public Statement getUpdate()
  {
    return update;
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
    StringBuffer buffer = new StringBuffer();
    buffer.append("for ");
    if (init != null)
    {
      String initString = init.toString();
      if (initString.endsWith(";"))
      {
        initString = initString.substring(0, initString.length() - 1);
      }
      buffer.append(initString);
    }
    buffer.append(";");
    if (condition != null)
    {
      buffer.append(" ");
      buffer.append(condition);
    }
    buffer.append(";");
    if (update != null)
    {
      String updateString = update.toString();
      if (updateString.endsWith(";"))
      {
        updateString = updateString.substring(0, updateString.length() - 1);
      }
      buffer.append(" ");
      buffer.append(updateString);
    }
    buffer.append("\n");
    buffer.append(block);
    return buffer.toString();
  }
}
