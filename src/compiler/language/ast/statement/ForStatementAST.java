package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ForStatementAST extends StatementAST
{

  private StatementAST init;
  private ExpressionAST condition;
  private StatementAST update;
  private BlockAST block;


  /**
   * Creates a new ForStatementAST with the specified initializer, condition, update and block
   * @param init - the initializer statement
   * @param condition - the for loop condition
   * @param update - the update statement
   * @param block - the block of the loop
   * @param parseInfo - the parsing information
   */
  public ForStatementAST(StatementAST init, ExpressionAST condition, StatementAST update, BlockAST block, ParseInfo parseInfo)
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
  public StatementAST getInit()
  {
    return init;
  }

  /**
   * @return the condition
   */
  public ExpressionAST getCondition()
  {
    return condition;
  }

  /**
   * @return the update
   */
  public StatementAST getUpdate()
  {
    return update;
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
