package compiler.language.ast.typeDefinition;

import compiler.language.LexicalPhrase;
import compiler.language.ast.member.AccessSpecifierAST;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.topLevel.TypeDefinitionAST;
import compiler.language.ast.type.PointerTypeAST;

/*
 * Created on 16 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumDefinitionAST extends TypeDefinitionAST
{

  private AccessSpecifierAST accessSpecifier;
  private ModifierAST[] modifiers;
  private PointerTypeAST baseClass;
  private PointerTypeAST[] interfaces;
  private EnumConstantAST[] constants;
  private MemberAST[] members;

  /**
   * Creates a new Enum Definition with the specified attributes.
   * @param accessSpecifier - the access specifier
   * @param modifiers - the modifiers
   * @param name - the name
   * @param baseClass - the base class
   * @param interfaces - the list of implemented interfaces
   * @param constants - the enum constants
   * @param members - the members
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public EnumDefinitionAST(AccessSpecifierAST accessSpecifier, ModifierAST[] modifiers, NameAST name, PointerTypeAST baseClass, PointerTypeAST[] interfaces, EnumConstantAST[] constants, MemberAST[] members, LexicalPhrase lexicalPhrase)
  {
    super(name, lexicalPhrase);
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.baseClass = baseClass;
    this.interfaces = interfaces;
    this.constants = constants;
    this.members = members;
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
   * @return the base class
   */
  public PointerTypeAST getBaseClass()
  {
    return baseClass;
  }

  /**
   * @return the implemented interfaces
   */
  public PointerTypeAST[] getInterfaces()
  {
    return interfaces;
  }

  /**
   * @return the constants
   */
  public EnumConstantAST[] getConstants()
  {
    return constants;
  }

  /**
   * @return the members
   */
  public MemberAST[] getMembers()
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
    buffer.append("enum ");
    buffer.append(getName());
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
    for (int i = 0; i < constants.length; i++)
    {
      String constantStr = constants[i].toString();
      buffer.append(constantStr.replaceAll("(?m)^", "   "));
      if (i != constants.length - 1)
      {
        buffer.append(",\n");
      }
    }
    if (constants.length == 0)
    {
      buffer.append("   ");
    }
    buffer.append(";");
    if (members.length > 0)
    {
      buffer.append("\n   \n");
    }
    for (int i = 0; i < members.length; i++)
    {
      String memberStr = members[i].toString();
      buffer.append(memberStr.replaceAll("(?m)^", "   "));
      buffer.append("\n   \n");
    }
    buffer.append("}");
    return buffer.toString();
  }
}
