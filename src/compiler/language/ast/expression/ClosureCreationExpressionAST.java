package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.ParameterAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeParameterAST;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClosureCreationExpressionAST extends ExpressionAST
{

  private TypeParameterAST[] typeParameters;
  private ParameterAST[] parameters;
  private TypeAST[] returnTypes;
  private PointerTypeAST[] exceptionTypes;
  private BlockAST block;

  /**
   * Creates a new ClosureCreationExpressionAST with the specified type parameters, return type, parameters, thrown exception types and block
   * @param typeParameters - the type parameters of the closure
   * @param parameters - the parameters to the closure
   * @param returnTypes - the return types of the closure
   * @param exceptionTypes - the types of exceptions that this closure can throw
   * @param block - the block containing the closure's statements
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ClosureCreationExpressionAST(TypeParameterAST[] typeParameters, ParameterAST[] parameters, TypeAST[] returnTypes, PointerTypeAST[] exceptionTypes, BlockAST block, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.typeParameters = typeParameters;
    this.parameters = parameters;
    this.returnTypes = returnTypes;
    this.exceptionTypes = exceptionTypes;
    this.block = block;
  }

  /**
   * @return the typeParameters
   */
  public TypeParameterAST[] getTypeParameters()
  {
    return typeParameters;
  }

  /**
   * @return the parameters
   */
  public ParameterAST[] getParameters()
  {
    return parameters;
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
      buffer.append(">");
    }
    buffer.append("(");
    if (parameters.length == 0)
    {
      buffer.append("void");
    }
    else
    {
      for (int i = 0; i < parameters.length; i++)
      {
        buffer.append(parameters[i]);
        if (i != parameters.length - 1)
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
