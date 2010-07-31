package compiler.language.ast.statement;


/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EmptyStatement extends Statement
{
  // this class stores nothing, but represents an empty statement

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return ";";
  }
}
