package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class DefaultArgumentAST extends SingleArgumentAST
{

  private ExpressionAST defaultExpression;

  /**
   * Creates a new Default ArgumentAST with the specified modifiers, type, name and default expresion.
   * @param modifiers - the modifiers of this default argument
   * @param type - the type of this default argument
   * @param name - the name of this default argument
   * @param defaultExpression - the default expression for this default argument
   * @param parseInfo - the parsing information
   */
  public DefaultArgumentAST(ModifierAST[] modifiers, TypeAST type, NameAST name, ExpressionAST defaultExpression, ParseInfo parseInfo)
  {
    super(modifiers, type, name, parseInfo);
    this.defaultExpression = defaultExpression;
  }

  /**
   * @return the default expression for this default argument
   */
  public ExpressionAST getDefaultExpression()
  {
    return defaultExpression;
  }

  /**
   * @see compiler.language.ast.misc.SingleArgumentAST#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (ModifierAST modifier : modifiers)
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
