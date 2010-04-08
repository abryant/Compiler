package test.parser;

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
  
  public ValueRule()
  {
    super(ExpressionType.VALUE, new Object[] {ExpressionType.NUMBER},
                                new Object[] {ExpressionType.LPAREN, ExpressionType.EXPRESSION, ExpressionType.RPAREN});
  }
  
  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Object[] args)
  {
    if (args.length == 1)
    {
      // just a number
      return new Value((Number) args[0]);
    }
    if (args.length == 3)
    {
      // ( Expression )
      return new Value((Expression) args[1]);
    }
    throw new IllegalArgumentException();
  }
  
}
