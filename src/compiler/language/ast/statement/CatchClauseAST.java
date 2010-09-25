package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CatchClauseAST
{

  private ModifierAST[] modifiers;
  private PointerTypeAST caughtType;
  private NameAST caughtName;
  private BlockAST block;

  private ParseInfo parseInfo;

  /**
   * Creates a new CatchClauseAST with the specified caught type and name, and the specified block to execute when that type is caught
   * @param modifiers - the modifiers to apply to the declared variable
   * @param caughtType - the caught type
   * @param caughtName - the name of the variable holding the caught object
   * @param block - the block to be executed when this type is caught
   * @param parseInfo - the parsing information
   */
  public CatchClauseAST(ModifierAST[] modifiers, PointerTypeAST caughtType, NameAST caughtName, BlockAST block, ParseInfo parseInfo)
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
  public ModifierAST[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the caught type
   */
  public PointerTypeAST getCaughtType()
  {
    return caughtType;
  }

  /**
   * @return the caughtName
   */
  public NameAST getCaughtName()
  {
    return caughtName;
  }

  /**
   * @return the block
   */
  public BlockAST getBlock()
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
    for (ModifierAST modifier : modifiers)
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
