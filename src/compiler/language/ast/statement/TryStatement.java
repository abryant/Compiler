package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;

/*
 * Created on 14 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TryStatement extends Statement
{

  private Block tryBlock;
  private CatchClause[] catchClauses;
  private Block finallyBlock;

  /**
   * Creates a new TryStatement with the specified try block, catch clauses and finally block
   * @param tryBlock - the try block
   * @param catchClauses - the list of catch clauses
   * @param finallyBlock - the finally block, or null if no finally block was specified
   * @param parseInfo - the parsing information
   */
  public TryStatement(Block tryBlock, CatchClause[] catchClauses, Block finallyBlock, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.tryBlock = tryBlock;
    this.catchClauses = catchClauses;
    this.finallyBlock = finallyBlock;
  }

  /**
   * @return the tryBlock
   */
  public Block getTryBlock()
  {
    return tryBlock;
  }

  /**
   * @return the catchClauses
   */
  public CatchClause[] getCatchClauses()
  {
    return catchClauses;
  }

  /**
   * @return the finallyBlock
   */
  public Block getFinallyBlock()
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
    for (CatchClause catchClause : catchClauses)
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
