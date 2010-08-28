package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanLiteralExpression extends Expression
{

  private boolean value;

  /**
   * Creates a new BooleanLiteralExpression with the specified value
   * @param value - the boolean value that this literal stores
   * @param parseInfo - the parsing information
   */
  public BooleanLiteralExpression(boolean value, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.value = value;
  }

  /**
   * @return the value
   */
  public boolean isValue()
  {
    return value;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return value ? "true" : "false";
  }
}
