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
public class SumRule extends Rule<ExpressionType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ExpressionType> PRODUCT_PRODUCTION = new Production<ExpressionType>(ExpressionType.PRODUCT);
  private static final Production<ExpressionType> PLUS_PRODUCTION = new Production<ExpressionType>(ExpressionType.SUM, ExpressionType.PLUS, ExpressionType.PRODUCT);
  private static final Production<ExpressionType> MINUS_PRODUCTION = new Production<ExpressionType>(ExpressionType.SUM, ExpressionType.MINUS, ExpressionType.PRODUCT);

  @SuppressWarnings("unchecked")
  public SumRule()
  {
    super(ExpressionType.SUM, PRODUCT_PRODUCTION, PLUS_PRODUCTION, MINUS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Production<ExpressionType> production, Object[] args) throws ParseException
  {
    if (PRODUCT_PRODUCTION.equals(production))
    {
      // just a product
      return new Sum(new Product[] {(Product) args[0]}, new boolean[] {true});
    }
    if (PLUS_PRODUCTION.equals(production) || MINUS_PRODUCTION.equals(production))
    {
      // Sum + Product or Sum - Product
      Sum old = (Sum) args[0];
      Product[] oldProducts = old.getProducts();
      Product[] newProducts = new Product[oldProducts.length + 1];
      System.arraycopy(oldProducts, 0, newProducts, 0, oldProducts.length);
      newProducts[oldProducts.length] = (Product) args[2];
      boolean[] oldSigns = old.getSigns();
      boolean[] newSigns = new boolean[oldSigns.length + 1];
      System.arraycopy(oldSigns, 0, newSigns, 0, oldSigns.length);
      newSigns[oldSigns.length] = args[1] == ExpressionType.PLUS; // choose between PLUS_PRODUCTION and MINUS_PRODUCTION
      return new Sum(newProducts, newSigns);
    }
    throw new IllegalArgumentException();
  }

}
