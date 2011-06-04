package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.ParameterListAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeParameterAST;


/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MethodAST extends MemberAST
{

  private AccessSpecifierAST accessSpecifier;
  private ModifierAST[] modifiers;
  private TypeParameterAST[] typeParameters; // null if there are no type parameters
  private TypeAST returnType;
  private NameAST name;
  private ParameterListAST parameters;
  private PointerTypeAST[] thrownTypes;
  private BlockAST body;  // null if no body

  /**
   * Creates a new method with the specified options.
   * @param accessSpecifier - the access specifier for the method
   * @param modifiers - the modifiers for the method
   * @param typeParameters - the type parameters for the method
   * @param returnType - the return type for the method, or null if the method does not return anything (void)
   * @param name - the name of the method
   * @param parameters - the parameters that the method takes
   * @param thrownTypes - the list of types that the method throws
   * @param body - the body of the method
   * @param parseInfo - the parsing information
   */
  public MethodAST(AccessSpecifierAST accessSpecifier, ModifierAST[] modifiers, TypeParameterAST[] typeParameters, TypeAST returnType, NameAST name, ParameterListAST parameters, PointerTypeAST[] thrownTypes, BlockAST body, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.typeParameters = typeParameters;
    this.returnType = returnType;
    this.name = name;
    this.parameters = parameters;
    this.thrownTypes = thrownTypes;
    this.body = body;
  }

  /**
   * @return the access specifier, or null if none was specified
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
   * @return the type parameters, or null if there are none
   */
  public TypeParameterAST[] getTypeParameters()
  {
    return typeParameters;
  }

  /**
   * @return the return type, or null if the return type is void
   */
  public TypeAST getReturnType()
  {
    return returnType;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the parameters
   */
  public ParameterListAST getParameters()
  {
    return parameters;
  }

  /**
   * @return the types that this method can throw
   */
  public PointerTypeAST[] getThrownTypes()
  {
    return thrownTypes;
  }

  /**
   * @return the body, or null if no body is specified
   */
  public BlockAST getBody()
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
    for (ModifierAST mod : modifiers)
    {
      buffer.append(mod);
      buffer.append(" ");
    }
    if (typeParameters.length > 0)
    {
      buffer.append("<");
      for (int i = 0; i < typeParameters.length; i++)
      {
        buffer.append(typeParameters[i]);
        if (i != typeParameters.length - 1)
        {
          buffer.append(", ");
        }
      }
      buffer.append("> ");
    }
    buffer.append(returnType == null ? "void" : returnType);
    buffer.append(" ");
    buffer.append(name);
    buffer.append(parameters);
    if (thrownTypes.length > 0)
    {
      buffer.append(" throws ");
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
