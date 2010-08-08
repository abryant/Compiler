package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.member.Modifier;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.Type;

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
   * @param parseInfo - the parsing information
   */
  public DefaultArgument(Modifier[] modifiers, Type type, Name name, Expression defaultExpression, ParseInfo parseInfo)
  {
    super(modifiers, type, name, parseInfo);
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
   * @see compiler.language.ast.misc.SingleArgument#toString()
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
