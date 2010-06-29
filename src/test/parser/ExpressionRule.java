package test.parser;

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

  private static final Object[] SUM_PRODUCTION = new Object[] {ExpressionType.SUM};

  public ExpressionRule()
  {
    super(ExpressionType.EXPRESSION, SUM_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == SUM_PRODUCTION)
    {
      return new Expression((Sum) args[0]);
    }
    throw new IllegalArgumentException();
  }

}
