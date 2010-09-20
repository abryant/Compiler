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
public class ExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production SUM_PRODUCTION = new Production(ExpressionType.SUM);

  public ExpressionRule()
  {
    super(ExpressionType.EXPRESSION, SUM_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (SUM_PRODUCTION.equals(production))
    {
      return new Expression((Sum) args[0]);
    }
    throw new IllegalArgumentException();
  }

}
