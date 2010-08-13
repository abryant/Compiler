package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Argument;
import compiler.language.ast.statement.Block;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeArgument;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClosureCreationExpression extends Expression
{

  private TypeArgument[] typeArguments;
  private Argument[] arguments;
  private Type[] returnTypes;
  private PointerType[] exceptionTypes;
  private Block block;

  /**
   * Creates a new ClosureCreationExpression with the specified type arguments, return type, arguments, thrown exception types and block
   * @param typeArguments - the type arguments of the closure
   * @param arguments - the arguments to the closure
   * @param returnTypes - the return types of the closure
   * @param exceptionTypes - the types of exceptions that this closure can throw
   * @param block - the block containing the closure's statements
   * @param parseInfo - the parsing information
   */
  public ClosureCreationExpression(TypeArgument[] typeArguments, Argument[] arguments, Type[] returnTypes, PointerType[] exceptionTypes, Block block, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.typeArguments = typeArguments;
    this.arguments = arguments;
    this.returnTypes = returnTypes;
    this.exceptionTypes = exceptionTypes;
    this.block = block;
  }

  /**
   * @return the typeArguments
   */
  public TypeArgument[] getTypeArguments()
  {
    return typeArguments;
  }

  /**
   * @return the arguments
   */
  public Argument[] getArguments()
  {
    return arguments;
  }

  /**
   * @return the return types
   */
  public Type[] getReturnTypes()
  {
    return returnTypes;
  }

  /**
   * @return the exceptionTypes
   */
  public PointerType[] getExceptionTypes()
  {
    return exceptionTypes;
  }

  /**
   * @return the block
   */
  public Block getBlock()
  {
    return block;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("closure");
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
    buffer.append("(");
    if (arguments.length == 0)
    {
      buffer.append("void");
    }
    else
    {
      for (int i = 0; i < arguments.length; i++)
      {
        buffer.append(arguments[i]);
        if (i != arguments.length - 1)
        {
          buffer.append(", ");
        }
      }
    }
    buffer.append(" -> ");
    for (int i = 0; i < returnTypes.length; i++)
    {
      buffer.append(returnTypes[i]);
      if (i != returnTypes.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(")");
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
    buffer.append("\n");
    buffer.append(block);
    return buffer.toString();
  }

}
