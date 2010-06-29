package test.parser;

import compiler.parser.Rule;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class SumRule extends Rule
{

  private static final Object[] PRODUCT_PRODUCTION = new Object[] {ExpressionType.PRODUCT};
  private static final Object[] PLUS_PRODUCTION = new Object[] {ExpressionType.SUM, ExpressionType.PLUS, ExpressionType.PRODUCT};
  private static final Object[] MINUS_PRODUCTION = new Object[] {ExpressionType.SUM, ExpressionType.MINUS, ExpressionType.PRODUCT};

  public SumRule()
  {
    super(ExpressionType.SUM, PRODUCT_PRODUCTION, PLUS_PRODUCTION, MINUS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCT_PRODUCTION)
    {
      // just a product
      return new Sum(new Product[] {(Product) args[0]}, new boolean[] {true});
    }
    if (types == PLUS_PRODUCTION || types == MINUS_PRODUCTION)
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
