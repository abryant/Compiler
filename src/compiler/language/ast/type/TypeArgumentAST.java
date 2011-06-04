package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;

/*
 * Created on 12 Jul 2010
 */

/**
 * The base class for all types of type argument, e.g. NormalTypeArgumentAST and WildcardTypeArgumentAST
 * @author Anthony Bryant
 */
public abstract class TypeArgumentAST
{

  private ParseInfo parseInfo;

  /**
   * Creates a new TypeArgumentAST with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public TypeArgumentAST(ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

}
