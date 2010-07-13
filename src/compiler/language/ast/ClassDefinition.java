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
  private TypeArgument[] typeArguments;
  private PointerType baseClass;
  private PointerType[] interfaces;
  private Member[] members;

  /**
   * Creates a new class definition with all of the specified properties
   * @param access - the access specifier, or null for the default access specifier
   * @param modifiers - the list of modifiers for this class
   * @param name - the name of this class
   * @param typeArguments - the type arguments to this class
   * @param baseClass - the base class of this class
   * @param interfaces - the list of interfaces that this class implements
   * @param members - the list of members of this class
   */
  public ClassDefinition(AccessSpecifier access, Modifier[] modifiers, Name name, TypeArgument[] typeArguments, PointerType baseClass, PointerType[] interfaces, Member[] members)
  {
    this.access = access;
    this.modifiers = modifiers;
    this.name = name;
    this.typeArguments = typeArguments;
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
   * @return the type arguments
   */
  public TypeArgument[] getTypeArguments()
  {
    return typeArguments;
  }

  /**
   * @return the base class
   */
  public PointerType getBaseClass()
  {
    return baseClass;
  }

  /**
   * @return the interfaces
   */
  public PointerType[] getInterfaces()
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

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    if (access != null)
    {
      buffer.append(access);
      buffer.append(" ");
    }
    for (int i = 0; i < modifiers.length; i++)
    {
      buffer.append(modifiers[i]);
      buffer.append(" ");
    }
    buffer.append("class ");
    buffer.append(name);
    if (typeArguments.length > 0)
    {
      buffer.append("<");
      for (int i = 0; i < typeArguments.length; i++)
      {
        buffer.append(typeArguments[i]);
        if (i != typeArguments.length - 1)
        {
          buffer.append(", ");
        }
      }
      buffer.append(">");
    }
    buffer.append(" ");
    if (baseClass != null)
    {
      buffer.append("extends ");
      buffer.append(baseClass);
      buffer.append(" ");
    }
    if (interfaces.length > 0)
    {
      buffer.append("implements ");
      for (int i = 0; i < interfaces.length; i++)
      {
        buffer.append(interfaces[i]);
        if (i != interfaces.length - 1)
        {
          buffer.append(", ");
        }
      }
      buffer.append(" ");
    }
    buffer.append("\n{\n");
    for (int i = 0; i < members.length; i++)
    {
      String memberStr = members[i].toString();
      buffer.append(memberStr.replaceAll("(?m)^", "   "));
      buffer.append("\n   ");
    }
    buffer.append("\n}");
    return buffer.toString();
  }
}
