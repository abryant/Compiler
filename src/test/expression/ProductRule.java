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
public class ProductRule extends Rule<ExpressionType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ExpressionType> VALUE_PRODUCTION = new Production<ExpressionType>(ExpressionType.VALUE);
  private static final Production<ExpressionType> TIMES_PRODUCTION = new Production<ExpressionType>(ExpressionType.PRODUCT, ExpressionType.TIMES, ExpressionType.VALUE);

  @SuppressWarnings("unchecked")
  public ProductRule()
  {
    super(ExpressionType.PRODUCT, VALUE_PRODUCTION, TIMES_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Production<ExpressionType> production, Object[] args) throws ParseException
  {
    if (VALUE_PRODUCTION.equals(production))
    {
      // just a value
      return new Product(new Value[] {(Value) args[0]});
    }
    if (TIMES_PRODUCTION.equals(production))
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
