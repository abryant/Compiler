package test.expression;

import compiler.parser.ParseException;
import compiler.parser.Production;
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
  private static final long serialVersionUID = 1L;

  private static final Production NUMBER_PRODUCTION = new Production(ExpressionType.NUMBER);
  private static final Production EXPRESSION_PRODUCTION = new Production(ExpressionType.LPAREN, ExpressionType.EXPRESSION, ExpressionType.RPAREN);

  public ValueRule()
  {
    super(ExpressionType.VALUE, NUMBER_PRODUCTION, EXPRESSION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (NUMBER_PRODUCTION.equals(production))
    {
      // just a number
      return new Value((Number) args[0]);
    }
    if (EXPRESSION_PRODUCTION.equals(production))
    {
      // ( Expression )
      return new Value((Expression) args[1]);
    }
    throw new IllegalArgumentException();
  }

}
