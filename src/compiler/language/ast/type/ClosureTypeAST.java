package compiler.language.ast.type;

import compiler.language.LexicalPhrase;


/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClosureTypeAST extends TypeAST
{

  private TypeParameterAST[] typeParameters;
  private TypeAST[] argumentTypes;
  private TypeAST[] resultTypes;
  private PointerTypeAST[] exceptionTypes;

  /**
   * Creates a new Closure type with the specified type parameters, argument types, result types, and exception types
   * @param typeParameters - the type parameters of this closure type
   * @param argumentTypes - the types of the arguments to this closure
   * @param resultTypes - the types of the results of this closure
   * @param exceptionTypes - the types of the exceptions thrown by this closure
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ClosureTypeAST(TypeParameterAST[] typeParameters, TypeAST[] argumentTypes, TypeAST[] resultTypes, PointerTypeAST[] exceptionTypes, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.typeParameters = typeParameters;
    this.argumentTypes = argumentTypes;
    this.resultTypes = resultTypes;
    this.exceptionTypes = exceptionTypes;
  }

  /**
   * @return the typeParameters
   */
  public TypeParameterAST[] getTypeParameters()
  {
    return typeParameters;
  }

  /**
   * @return the argument types
   */
  public TypeAST[] getArgumentTypes()
  {
    return argumentTypes;
  }

  /**
   * @return the result types
   */
  public TypeAST[] getResultTypes()
  {
    return resultTypes;
  }

  /**
   * @return the exception types
   */
  public PointerTypeAST[] getExceptionTypes()
  {
    return exceptionTypes;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("{");
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
    for (int i = 0; i < argumentTypes.length; i++)
    {
      buffer.append(argumentTypes[i]);
      if (i != argumentTypes.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(" -> ");
    for (int i = 0; i < resultTypes.length; i++)
    {
      buffer.append(resultTypes[i]);
      if (i != resultTypes.length - 1)
      {
        buffer.append(", ");
      }
    }
    if (exceptionTypes.length > 0)
    {
      buffer.append(" throws ");
      for (int i = 0; i < exceptionTypes.length; i++)
      {
        buffer.append(exceptionTypes[i]);
        if (i != exceptionTypes.length - 1)
        {
          buffer.append(", ");
        }
      }
    }
    buffer.append("}");
    return buffer.toString();
  }

}
