package compiler.language.ast;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class InterfaceDefinition extends TypeDefinition
{
  private AccessSpecifier access;
  private Modifier[] modifiers;
  private Name name;
  private TypeArgument[] typeArguments;
  private PointerType[] parentInterfaces;
  private Member[] members;

  /**
   * Creates a new interface definition with all of the specified properties
   * @param access - the access specifier, or null for the default access specifier
   * @param modifiers - the list of modifiers for this interface
   * @param name - the name of this interface
   * @param typeArguments - the type arguments to this interface
   * @param parentInterfaces - the list of interfaces that this interface extends
   * @param members - the list of members of this interface
   */
  public InterfaceDefinition(AccessSpecifier access, Modifier[] modifiers, Name name, TypeArgument[] typeArguments, PointerType[] parentInterfaces, Member[] members)
  {
    this.access = access;
    this.modifiers = modifiers;
    this.name = name;
    this.typeArguments = typeArguments;
    this.parentInterfaces = parentInterfaces;
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
   * @return the parent interfaces
   */
  public PointerType[] getInterfaces()
  {
    return parentInterfaces;
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
    buffer.append("interface ");
    buffer.append(name);
    if (typeArguments != null)
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
    if (parentInterfaces != null)
    {
      buffer.append("extends ");
      for (int i = 0; i < parentInterfaces.length; i++)
      {
        buffer.append(parentInterfaces[i]);
        if (i != parentInterfaces.length - 1)
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
      buffer.append(memberStr.replaceAll("^", "   "));
      buffer.append("\n   ");
    }
    buffer.append("\n}");
    return buffer.toString();
  }
}
