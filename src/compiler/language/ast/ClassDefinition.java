package compiler.language.ast;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClassDefinition extends TypeDefinition
{
  private AccessSpecifier access;
  private Modifier[] modifiers;
  private Name name;
  private ReferenceType baseClass;
  private ReferenceType[] interfaces;
  private Member[] members;

  /**
   * Creates a new class with all of the specified properties
   * @param access - the access specifier, or null for the default access specifier
   * @param modifiers - the list of modifiers for this class
   * @param name - the name of this class
   * @param baseClass - the base class of this class
   * @param interfaces - the list of interfaces that this class implements
   * @param members - the list of members of this class
   */
  public ClassDefinition(AccessSpecifier access, Modifier[] modifiers, Name name, ReferenceType baseClass, ReferenceType[] interfaces, Member[] members)
  {
    this.access = access;
    this.modifiers = modifiers;
    this.name = name;
    this.baseClass = baseClass;
    this.interfaces = interfaces;
    this.members = members;
  }

  /**
   * @return the access specifier
   */
  public AccessSpecifier getAccess()
  {
    return access;
  }

  /**
   * @return the modifiers
   */
  public Modifier[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the base class
   */
  public ReferenceType getBaseClass()
  {
    return baseClass;
  }

  /**
   * @return the interfaces
   */
  public ReferenceType[] getInterfaces()
  {
    return interfaces;
  }

  /**
   * @return the members
   */
  public Member[] getMembers()
  {
    return members;
  }
}
