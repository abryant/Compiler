package test.parser;

import compiler.parser.Rule;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 * 
 */
public class ProductRule extends Rule
{
  
  public ProductRule()
  {
    super(ExpressionType.PRODUCT, new Object[] {ExpressionType.VALUE},
                                  new Object[] {ExpressionType.PRODUCT, ExpressionType.TIMES, ExpressionType.VALUE});
  }
  
  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Object[] args)
  {
    if (args.length == 1)
    {
      // just a value
      return new Product(new Value[] {(Value) args[0]});
    }
    if (args.length == 3)
    {
      // Product * Value
      Value[] oldValues = ((Product) args[0]).getValues();
      Value[] newValues = new Value[oldValues.length + 1];
      System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
      newValues[oldValues.length] = (Value) args[2];
      return new Product(newValues);
    }
    throw new IllegalArgumentException();
  }
  
}
