package compiler.language.ast;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class DefaultArgument extends SingleArgument
{
  private Expression defaultExpression;

  /**
   * Creates a new Default Argument with the specified type, name and default expresion.
   * @param type - the type of this default argument
   * @param name - the name of this default argument
   * @param defaultExpression - the default expression for this default argument
   */
  public DefaultArgument(Type type, Name name, Expression defaultExpression)
  {
    super(type, name);
    this.defaultExpression = defaultExpression;
  }

  /**
   * @return the default expression for this default argument
   */
  public Expression getDefaultExpression()
  {
    return defaultExpression;
  }

  /**
   * @see compiler.language.ast.SingleArgument#toString()
   */
  @Override
  public String toString()
  {
    return type + " @" + name + " = " + defaultExpression;
  }

}
