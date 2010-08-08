package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.Type;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InstanceOfExpression extends Expression
{

  private Expression expression;
  private Type type;

  /**
   * Creates a new InstanceOfExpression to check the specified expression is of the specified type
   * @param expression - the expression to check
   * @param type - the type to check against
   * @param parseInfo - the parsing information
   */
  public InstanceOfExpression(Expression expression, Type type, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
    this.type = type;
  }

  /**
   * @return the expression
   */
  public Expression getExpression()
  {
    return expression;
  }

  /**
   * @return the type
   */
  public Type getType()
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
