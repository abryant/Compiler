package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;


/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifierAST
{

  private ParseInfo parseInfo;

  private ModifierTypeAST type;

  /**
   * Creates a new modifier of the specified type
   * @param type - the type of this modifier
   * @param parseInfo - the parsing information
   */
  public ModifierAST(ModifierTypeAST type, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.type = type;
  }

  /**
   * @return the type
   */
  public ModifierTypeAST getType()
  {
    return type;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return type.toString();
  }

}
