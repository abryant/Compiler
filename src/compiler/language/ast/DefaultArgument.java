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
   * Creates a new Default Argument with the specified modifiers, type, name and default expresion.
   * @param modifiers - the modifiers of this default argument
   * @param type - the type of this default argument
   * @param name - the name of this default argument
   * @param defaultExpression - the default expression for this default argument
   */
  public DefaultArgument(Modifier[] modifiers, Type type, Name name, Expression defaultExpression)
  {
    super(modifiers, type, name);
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
    StringBuffer buffer = new StringBuffer();
    for (Modifier modifier : modifiers)
    {
      buffer.append(modifier);
      buffer.append(" ");
    }
    buffer.append(type);
    buffer.append(" @");
    buffer.append(name);
    buffer.append(" = ");
    buffer.append(defaultExpression);
    return buffer.toString();
  }

}
