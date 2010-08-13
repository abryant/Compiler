package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CatchClause
{

  private PointerType caughtType;
  private Name caughtName;
  private Block block;

  private ParseInfo parseInfo;

  /**
   * Creates a new CatchClause with the specified caught type and name, and the specified block to execute when that type is caught
   * @param caughtType - the caught type
   * @param caughtName - the name of the variable holding the caught object
   * @param block - the block to be executed when this type is caught
   * @param parseInfo - the parsing information
   */
  public CatchClause(PointerType caughtType, Name caughtName, Block block, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.caughtType = caughtType;
    this.caughtName = caughtName;
    this.block = block;
  }

  /**
   * @return the caughtType
   */
  public PointerType getCaughtType()
  {
    return caughtType;
  }

  /**
   * @return the caughtName
   */
  public Name getCaughtName()
  {
    return caughtName;
  }

  /**
   * @return the block
   */
  public Block getBlock()
  {
    return block;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "catch " + caughtType + " " + caughtName + "\n" + block;
  }
}
