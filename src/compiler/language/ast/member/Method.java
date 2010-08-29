package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ArgumentList;
import compiler.language.ast.misc.Modifier;
import compiler.language.ast.statement.Block;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeArgument;


/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class Method extends Member
{

  private AccessSpecifier accessSpecifier;
  private Modifier[] modifiers;
  private TypeArgument[] typeArguments; // null if there are no type arguments
  private Type returnType;
  private Name name;
  private ArgumentList arguments;
  private PointerType[] thrownTypes;
  private Block body;  // null if no body

  /**
   * Creates a new method with the specified options.
   * @param accessSpecifier - the access specifier for the method
   * @param modifiers - the modifiers for the method
   * @param typeArguments - the type arguments for the method
   * @param returnType - the return type for the method, or null if the method does not return anything (void)
   * @param name - the name of the method
   * @param arguments - the arguments that the method takes
   * @param thrownTypes - the list of types that the method throws
   * @param body - the body of the method
   * @param parseInfo - the parsing information
   */
  public Method(AccessSpecifier accessSpecifier, Modifier[] modifiers, TypeArgument[] typeArguments, Type returnType, Name name, ArgumentList arguments, PointerType[] thrownTypes, Block body, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.typeArguments = typeArguments;
    this.returnType = returnType;
    this.name = name;
    this.arguments = arguments;
    this.thrownTypes = thrownTypes;
    this.body = body;
  }

  /**
   * @return the access specifier, or null if none was specified
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
   * @return the type arguments, or null if there are none
   */
  public TypeArgument[] getTypeArguments()
  {
    return typeArguments;
  }

  /**
   * @return the return type, or null if the return type is void
   */
  public Type getReturnType()
  {
    return returnType;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the arguments
   */
  public ArgumentList getArguments()
  {
    return arguments;
  }

  /**
   * @return the types that this method can throw
   */
  public PointerType[] getThrownTypes()
  {
    return thrownTypes;
  }

  /**
   * @return the body, or null if no body is specified
   */
  public Block getBody()
  {
    return body;
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
    for (Modifier mod : modifiers)
    {
      buffer.append(mod);
      buffer.append(" ");
    }
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
      buffer.append("> ");
    }
    buffer.append(returnType == null ? "void" : returnType);
    buffer.append(" ");
    buffer.append(name);
    buffer.append(arguments);
    if (thrownTypes.length > 0)
    {
      buffer.append("throws ");
      for (int i = 0; i < thrownTypes.length; i++)
      {
        buffer.append(thrownTypes[i]);
        if (i != thrownTypes.length - 1)
        {
          buffer.append(", ");
        }
      }
    }
    if (body == null)
    {
      buffer.append(";");
    }
    else
    {
      buffer.append("\n");
      buffer.append(body);
    }
    return buffer.toString();
  }
}
