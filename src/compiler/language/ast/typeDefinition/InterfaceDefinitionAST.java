package compiler.language.ast.typeDefinition;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.AccessSpecifierAST;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.topLevel.TypeDefinitionAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;


/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class InterfaceDefinitionAST extends TypeDefinitionAST
{
  private AccessSpecifierAST access;
  private ModifierAST[] modifiers;
  private TypeArgumentAST[] typeArguments;
  private PointerTypeAST[] parentInterfaces;
  private MemberAST[] members;

  /**
   * Creates a new interface definition with all of the specified properties
   * @param access - the access specifier, or null for the default access specifier
   * @param modifiers - the list of modifiers for this interface
   * @param name - the name of this interface
   * @param typeArguments - the type arguments to this interface
   * @param parentInterfaces - the list of interfaces that this interface extends
   * @param members - the list of members of this interface
   * @param parseInfo - the parsing information
   */
  public InterfaceDefinitionAST(AccessSpecifierAST access, ModifierAST[] modifiers, NameAST name, TypeArgumentAST[] typeArguments, PointerTypeAST[] parentInterfaces, MemberAST[] members, ParseInfo parseInfo)
  {
    super(name, parseInfo);
    this.access = access;
    this.modifiers = modifiers;
    this.typeArguments = typeArguments;
    this.parentInterfaces = parentInterfaces;
    this.members = members;
  }

  /**
   * @return the access specifier
   */
  public AccessSpecifierAST getAccess()
  {
    return access;
  }

  /**
   * @return the modifiers
   */
  public ModifierAST[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the type arguments
   */
  public TypeArgumentAST[] getTypeArguments()
  {
    return typeArguments;
  }

  /**
   * @return the parent interfaces
   */
  public PointerTypeAST[] getInterfaces()
  {
    return parentInterfaces;
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
    buffer.append(getName());
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
    buffer.append("\n{");
    for (int i = 0; i < members.length; i++)
    {
      buffer.append("\n");
      String memberStr = members[i].toString();
      buffer.append(memberStr.replaceAll("(?m)^", "   "));
      buffer.append("\n   \n");
    }
    buffer.append("}");
    return buffer.toString();
  }
}
