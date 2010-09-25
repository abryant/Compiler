package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InstanceOfExpressionAST extends ExpressionAST
{

  private ExpressionAST expression;
  private TypeAST type;

  /**
   * Creates a new InstanceOfExpressionAST to check the specified expression is of the specified type
   * @param expression - the expression to check
   * @param type - the type to check against
   * @param parseInfo - the parsing information
   */
  public InstanceOfExpressionAST(ExpressionAST expression, TypeAST type, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
    this.type = type;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @return the type
   */
  public TypeAST getType()
  {
    return type;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return expression + " instanceof " + type;
  }

}
