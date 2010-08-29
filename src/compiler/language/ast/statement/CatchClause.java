package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Modifier;
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

  private Modifier[] modifiers;
  private PointerType caughtType;
  private Name caughtName;
  private Block block;

  private ParseInfo parseInfo;

  /**
   * Creates a new CatchClause with the specified caught type and name, and the specified block to execute when that type is caught
   * @param modifiers - the modifiers to apply to the declared variable
   * @param caughtType - the caught type
   * @param caughtName - the name of the variable holding the caught object
   * @param block - the block to be executed when this type is caught
   * @param parseInfo - the parsing information
   */
  public CatchClause(Modifier[] modifiers, PointerType caughtType, Name caughtName, Block block, ParseInfo parseInfo)
  {
    this.modifiers = modifiers;
    this.parseInfo = parseInfo;
    this.caughtType = caughtType;
    this.caughtName = caughtName;
    this.block = block;
  }

  /**
   * @return the modifiers
   */
  public Modifier[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the caught type
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
    StringBuffer buffer = new StringBuffer();
    buffer.append("catch ");
    for (Modifier modifier : modifiers)
    {
      buffer.append(modifier);
      buffer.append(" ");
    }
    buffer.append(caughtType);
    buffer.append(" ");
    buffer.append(caughtName);
    buffer.append("\n");
    buffer.append(block);
    return buffer.toString();
  }
}
