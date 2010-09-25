package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;

/*
 * Created on 12 Jul 2010
 */

/**
 * The base class for all types of type parameter, e.g. NormalTypeParameterAST and WildcardTypeParameterAST
 * @author Anthony Bryant
 */
public abstract class TypeParameterAST
{

  private ParseInfo parseInfo;

  /**
   * Creates a new TypeParameterAST with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public TypeParameterAST(ParseInfo parseInfo)
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
