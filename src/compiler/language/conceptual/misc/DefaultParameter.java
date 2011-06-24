package compiler.language.conceptual.misc;

import compiler.language.conceptual.expression.Expression;
import compiler.language.conceptual.type.Type;

/*
 * Created on 22 Jun 2011
 */

/**
 * @author Anthony Bryant
 */
public class DefaultParameter extends SingleParameter
{

  private Expression defaultExpression;

  /**
   * Creates a new default parameter with the specified properties.
   * The default expression can be set later with setDefaultExpression()
   * @param isFinal - true if the parameter is final, false otherwise
   * @param isVolatile - true if the parameter is volatile, false otherwise
   * @param type - the type of the parameter
   * @param name - the name of the parameter
   */
  public DefaultParameter(boolean isFinal, boolean isVolatile, Type type, String name)
  {
    super(isFinal, isVolatile, type, name);
  }

  /**
   * @param defaultExpression - the defaultExpression to set
   */
  public void setDefaultExpression(Expression defaultExpression)
  {
    this.defaultExpression = defaultExpression;
  }

  /**
   * @return the defaultExpression
   */
  public Expression getDefaultExpression()
  {
    return defaultExpression;
  }

}
