package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Modifier;



/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberHeader
{

  private ParseInfo parseInfo;

  private AccessSpecifier accessSpecifier;
  private Modifier[] modifiers;

  /**
   * Creates a new MemberHeader with the specified access specifier and modifiers
   * @param accessSpecifier - the access specifier of this member header
   * @param modifiers - the modifiers of this member header
   * @param parseInfo - the parsing information
   */
  public MemberHeader(AccessSpecifier accessSpecifier, Modifier[] modifiers, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
  }

  /**
   * @return the access specifier
   */
  public AccessSpecifier getAccessSpecifier()
  {
    return accessSpecifier;
  }

  /**
   * @return the modifiers
   */
  public Modifier[] getModifiers()
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
