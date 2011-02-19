package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ModifierAST;



/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberHeaderAST
{

  private ParseInfo parseInfo;

  private AccessSpecifierAST accessSpecifier;
  private ModifierAST[] modifiers;

  /**
   * Creates a new MemberHeaderAST with the specified access specifier and modifiers
   * @param accessSpecifier - the access specifier of this member header
   * @param modifiers - the modifiers of this member header
   * @param parseInfo - the parsing information
   */
  public MemberHeaderAST(AccessSpecifierAST accessSpecifier, ModifierAST[] modifiers, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
  }

  /**
   * @return the access specifier
   */
  public AccessSpecifierAST getAccessSpecifier()
  {
    return accessSpecifier;
  }

  /**
   * @return the modifiers
   */
  public ModifierAST[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

}
