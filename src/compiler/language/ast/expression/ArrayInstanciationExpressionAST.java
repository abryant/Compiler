package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayInstanciationExpressionAST extends ExpressionAST
{

  private TypeAST baseType;
  private ExpressionAST[] dimensionExpressions;
  private int dimensions;
  private ExpressionAST[] arrayInitializerExpressions;

  /**
   * Creates a new ArrayInstanciationExpressionAST with the specified base type, dimension expressions, number of total dimensions, and array initializer expressions
   * @param baseType - the base type of the array
   * @param dimensionExpressions - the dimension expressions
   * @param dimensions - the total number of dimensions, including those from the dimension expressions
   * @param arrayInitializerExpressions - the array initializer's expressions, or null if no initializer was specified
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ArrayInstanciationExpressionAST(TypeAST baseType, ExpressionAST[] dimensionExpressions, int dimensions, ExpressionAST[] arrayInitializerExpressions, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.baseType = baseType;
    this.dimensionExpressions = dimensionExpressions;
    this.dimensions = dimensions;
    this.arrayInitializerExpressions = arrayInitializerExpressions;
  }

  /**
   * @return the baseType
   */
  public TypeAST getBaseType()
  {
    return baseType;
  }

  /**
   * @return the dimensionExpressions
   */
  public ExpressionAST[] getDimensionExpressions()
  {
    return dimensionExpressions;
  }

  /**
   * @return the total number of dimensions in this array instanciation, including the ones which have dimension expressions
   */
  public int getDimensions()
  {
    return dimensions;
  }

  /**
   * @return the arrayInitializerExpressions
   */
  public ExpressionAST[] getArrayInitializerExpressions()
  {
    return arrayInitializerExpressions;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("new ");
    buffer.append(baseType);
    for (int i = 0; i < dimensions; i++)
    {
      buffer.append("[");
      if (i < dimensionExpressions.length)
      {
        buffer.append(dimensionExpressions[i]);
      }
      buffer.append("]");
    }
    if (arrayInitializerExpressions != null)
    {
      buffer.append(" {");
      for (int i = 0; i < arrayInitializerExpressions.length; i++)
      {
        buffer.append(arrayInitializerExpressions[i]);
        if (i != arrayInitializerExpressions.length - 1)
        {
          buffer.append(", ");
        }
      }
      buffer.append("}");
    }
    return buffer.toString();
  }

}
