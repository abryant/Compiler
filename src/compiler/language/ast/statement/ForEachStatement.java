package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.Type;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ForEachStatement extends Statement
{

  private Type type;
  private Name name;
  private Expression expression;
  private Block block;

  /**
   * Creates a new ForEachStatement which iterates a variable over all of the values that are stored by the result of the expression
   * @param type - the type of the loop variable
   * @param name - the name of the loop variable
   * @param expression - the expression to iterate over
   * @param block - the block to execute for each iteration of the loop
   * @param parseInfo - the parsing information
   */
  public ForEachStatement(Type type, Name name, Expression expression, Block block, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.type = type;
    this.name = name;
    this.expression = expression;
    this.block = block;
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return type;
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

  /**
   * @return the block
   */
  public Block getBlock()
  {
    return block;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "for " + type + " " + name + " : " + expression + "\n" + block;
  }
}
