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
  
  public SumRule()
  {
    super(ExpressionType.SUM, new Object[] {ExpressionType.PRODUCT},
                              new Object[] {ExpressionType.SUM, ExpressionType.PLUS, ExpressionType.PRODUCT});
  }
  
  /**
   * @see compiler.parser.Rule#match(java.lang.Object[])
   */
  @Override
  public Object match(Object[] args)
  {
    if (args.length == 1)
    {
      // just a product
      return new Sum(new Product[] {(Product) args[0]});
    }
    if (args.length == 3)
    {
      // Sum + Product
      Sum old = (Sum) args[0];
      Product[] oldProducts = old.getProducts();
      Product[] newProducts = new Product[oldProducts.length + 1];
      System.arraycopy(oldProducts, 0, newProducts, 0, oldProducts.length);
      newProducts[oldProducts.length] = (Product) args[2];
      return new Sum(newProducts);
    }
    throw new IllegalArgumentException();
  }
  
}
