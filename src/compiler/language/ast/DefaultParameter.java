package compiler.language.ast;

/*
 * Created on 16 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class DefaultParameter extends Parameter
{

  private Name name;
  private Expression expression;

  /**
   * Creates a new default parameter with the specified name and expression.
   * @param name - the name of the default parameter
   * @param expression - the expression for the default parameter
   */
  public DefaultParameter(Name name, Expression expression)
  {
    this.name = name;
    this.expression = expression;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the expression
   */
  public Expression getExpression()
  {
    return expression;
  }

}
