package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CastExpressionAST extends ExpressionAST
{

  private TypeAST type;
  private ExpressionAST subExpression;

  /**
   * Creates a new CastExpressionAST to cast the specified subExpression to the specified type
   * @param type - the type to case the subExpression to
   * @param subExpression - the subExpression to cast to the specified type
   * @param parseInfo - the parsing information
   */
  public CastExpressionAST(TypeAST type, ExpressionAST subExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.type = type;
    this.subExpression = subExpression;
  }

  /**
   * @return the type
   */
  public TypeAST getType()
  {
    return type;
  }

  /**
   * @return the subExpression
   */
  public ExpressionAST getSubExpression()
  {
    return subExpression;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "cast<" + type + "> " + subExpression;
  }

}
