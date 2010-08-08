package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;


/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EmptyStatement extends Statement
{

  /**
   * Creates a new EmptyStatement with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public EmptyStatement(ParseInfo parseInfo)
  {
    super(parseInfo);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return ";";
  }
}
