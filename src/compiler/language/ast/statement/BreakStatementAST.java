package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.IntegerLiteralAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BreakStatementAST extends StatementAST
{

  private IntegerLiteralAST breakLevels;

  /**
   * Creates a new BreakStatementAST which breaks out of the specified number of levels of nested loops.
   * @param breakLevels - the integer literal representing the number of levels to break out of, or null if no number of levels was specified
   * @param parseInfo - the parsing information
   */
  public BreakStatementAST(IntegerLiteralAST breakLevels, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.breakLevels = breakLevels;
  }

  /**
   * @return the number of levels to break out of, or null if it was not specified
   */
  public IntegerLiteralAST getBreakLevels()
  {
    return breakLevels;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    if (breakLevels == null)
    {
      return "break;";
    }
    return "break(" + breakLevels + ");";
  }
}
