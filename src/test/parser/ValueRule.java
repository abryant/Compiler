package test.parser;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class ValueRule extends Rule
{

  private static final Object[] NUMBER_PRODUCTION = new Object[] {ExpressionType.NUMBER};
  private static final Object[] EXPRESSION_PRODUCTION = new Object[] {ExpressionType.LPAREN, ExpressionType.EXPRESSION, ExpressionType.RPAREN};

  public ValueRule()
  {
    super(ExpressionType.VALUE, NUMBER_PRODUCTION, EXPRESSION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == NUMBER_PRODUCTION)
    {
      // just a number
      return new Value((Number) args[0]);
    }
    if (types == EXPRESSION_PRODUCTION)
    {
      // ( Expression )
      return new Value((Expression) args[1]);
    }
    throw new IllegalArgumentException();
  }

}
