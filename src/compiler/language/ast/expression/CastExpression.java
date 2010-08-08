package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.Type;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CastExpression extends Expression
{

  private Type type;
  private Expression subExpression;

  /**
   * Creates a new CastExpression to cast the specified subExpression to the specified type
   * @param type - the type to case the subExpression to
   * @param subExpression - the subExpression to cast to the specified type
   * @param parseInfo - the parsing information
   */
  public CastExpression(Type type, Expression subExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.type = type;
    this.subExpression = subExpression;
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return type;
  }

  /**
   * @return the subExpression
   */
  public Expression getSubExpression()
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
