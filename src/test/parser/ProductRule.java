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
public class ProductRule extends Rule
{

  private static final Object[] VALUE_PRODUCTION = new Object[] {ExpressionType.VALUE};
  private static final Object[] TIMES_PRODUCTION = new Object[] {ExpressionType.PRODUCT, ExpressionType.TIMES, ExpressionType.VALUE};

  public ProductRule()
  {
    super(ExpressionType.PRODUCT, VALUE_PRODUCTION, TIMES_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == VALUE_PRODUCTION)
    {
      // just a value
      return new Product(new Value[] {(Value) args[0]});
    }
    if (types == TIMES_PRODUCTION)
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
