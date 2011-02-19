package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;

/*
 * Created on 14 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TryStatementAST extends StatementAST
{

  private BlockAST tryBlock;
  private CatchClauseAST[] catchClauses;
  private BlockAST finallyBlock;

  /**
   * Creates a new TryStatementAST with the specified try block, catch clauses and finally block
   * @param tryBlock - the try block
   * @param catchClauses - the list of catch clauses
   * @param finallyBlock - the finally block, or null if no finally block was specified
   * @param parseInfo - the parsing information
   */
  public TryStatementAST(BlockAST tryBlock, CatchClauseAST[] catchClauses, BlockAST finallyBlock, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.tryBlock = tryBlock;
    this.catchClauses = catchClauses;
    this.finallyBlock = finallyBlock;
  }

  /**
   * @return the tryBlock
   */
  public BlockAST getTryBlock()
  {
    return tryBlock;
  }

  /**
   * @return the catchClauses
   */
  public CatchClauseAST[] getCatchClauses()
  {
    return catchClauses;
  }

  /**
   * @return the finallyBlock
   */
  public BlockAST getFinallyBlock()
  {
    return finallyBlock;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("try\n");
    buffer.append(tryBlock);
    for (CatchClauseAST catchClause : catchClauses)
    {
      buffer.append("\n");
      buffer.append(catchClause);
    }
    if (finallyBlock != null)
    {
      buffer.append("\nfinally\n");
      buffer.append(finallyBlock);
    }
    return buffer.toString();
  }
}
