package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ArgumentAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeArgumentAST;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClosureCreationExpressionAST extends ExpressionAST
{

  private TypeArgumentAST[] typeArguments;
  private ArgumentAST[] arguments;
  private TypeAST[] returnTypes;
  private PointerTypeAST[] exceptionTypes;
  private BlockAST block;

  /**
   * Creates a new ClosureCreationExpressionAST with the specified type arguments, return type, arguments, thrown exception types and block
   * @param typeArguments - the type arguments of the closure
   * @param arguments - the arguments to the closure
   * @param returnTypes - the return types of the closure
   * @param exceptionTypes - the types of exceptions that this closure can throw
   * @param block - the block containing the closure's statements
   * @param parseInfo - the parsing information
   */
  public ClosureCreationExpressionAST(TypeArgumentAST[] typeArguments, ArgumentAST[] arguments, TypeAST[] returnTypes, PointerTypeAST[] exceptionTypes, BlockAST block, ParseInfo parseInfo)
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
  public TypeArgumentAST[] getTypeArguments()
  {
    return typeArguments;
  }

  /**
   * @return the arguments
   */
  public ArgumentAST[] getArguments()
  {
    return arguments;
  }

  /**
   * @return the return types
   */
  public TypeAST[] getReturnTypes()
  {
    return returnTypes;
  }

  /**
   * @return the exceptionTypes
   */
  public PointerTypeAST[] getExceptionTypes()
  {
    return exceptionTypes;
  }

  /**
   * @return the block
   */
  public BlockAST getBlock()
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
    if (returnTypes.length > 0)
    {
      buffer.append(" -> ");
      for (int i = 0; i < returnTypes.length; i++)
      {
        buffer.append(returnTypes[i]);
        if (i != returnTypes.length - 1)
        {
          buffer.append(", ");
        }
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
