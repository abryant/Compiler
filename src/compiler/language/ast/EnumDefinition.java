package compiler.language.ast;

/*
 * Created on 16 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumDefinition extends TypeDefinition
{

  private AccessSpecifier accessSpecifier;
  private Modifier[] modifiers;
  private Name name;
  private PointerType baseClass;
  private PointerType[] interfaces;
  private EnumConstant[] constants;
  private Member[] members;

  /**
   * Creates a new Enum Definition with the specified attributes.
   * @param accessSpecifier - the access specifier
   * @param modifiers - the modifiers
   * @param name - the name
   * @param baseClass - the base class
   * @param interfaces - the list of implemented interfaces
   * @param constants - the enum constants
   * @param members - the members
   */
  public EnumDefinition(AccessSpecifier accessSpecifier, Modifier[] modifiers, Name name, PointerType baseClass, PointerType[] interfaces, EnumConstant[] constants, Member[] members)
  {
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.name = name;
    this.baseClass = baseClass;
    this.interfaces = interfaces;
    this.constants = constants;
    this.members = members;
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
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the base class
   */
  public PointerType getBaseClass()
  {
    return baseClass;
  }

  /**
   * @return the implemented interfaces
   */
  public PointerType[] getInterfaces()
  {
    return interfaces;
  }

  /**
   * @return the constants
   */
  public EnumConstant[] getConstants()
  {
    return constants;
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
    if (accessSpecifier != null)
    {
      buffer.append(accessSpecifier);
      buffer.append(" ");
    }
    for (int i = 0; i < modifiers.length; i++)
    {
      buffer.append(modifiers[i]);
      buffer.append(" ");
    }
    buffer.append("class ");
    buffer.append(name);
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
    if (constants.length == 0)
    {
      buffer.append("   ");
    }
    for (int i = 0; i < constants.length; i++)
    {
      buffer.append("   ");
      buffer.append(constants[i]);
      if (i != constants.length - 1)
      {
        buffer.append(",\n");
      }
    }
    buffer.append(";\n");
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
