package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class DefaultParameterAST extends SingleParameterAST
{

  private ExpressionAST defaultExpression;

  /**
   * Creates a new DefaultParameterAST with the specified modifiers, type, name and default expression.
   * @param modifiers - the modifiers of this default parameter
   * @param type - the type of this default parameter
   * @param name - the name of this default parameter
   * @param defaultExpression - the default expression for this default parameter
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public DefaultParameterAST(ModifierAST[] modifiers, TypeAST type, NameAST name, ExpressionAST defaultExpression, LexicalPhrase lexicalPhrase)
  {
    super(modifiers, type, name, lexicalPhrase);
    this.defaultExpression = defaultExpression;
  }

  /**
   * @return the default expression for this default parameter
   */
  public ExpressionAST getDefaultExpression()
  {
    return defaultExpression;
  }

  /**
   * @see compiler.language.ast.misc.SingleParameterAST#toString()
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
