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
  
  public ExpressionRule()
  {
    super(ExpressionType.EXPRESSION, new Object[] {ExpressionType.SUM});
  }
  
  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Object[] args)
  {
    if (args.length == 1)
    {
      return new Expression((Sum) args[0]);
    }
    throw new IllegalArgumentException();
  }
  
}
