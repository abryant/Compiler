package compiler.language.ast;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberHeader
{
  private AccessSpecifier accessSpecifier;
  private Modifier[] modifiers;

  /**
   * Creates a new MemberHeader with the specified access specifier and modifiers
   * @param accessSpecifier - the access specifier of this member header
   * @param modifiers - the modifiers of this member header
   */
  public MemberHeader(AccessSpecifier accessSpecifier, Modifier[] modifiers)
  {
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

}
