package test.parser;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 * 
 */
public class Sum
{
  
  private Product[] products;
  
  public Sum(Product[] products)
  {
    this.products = products;
  }
  
  public Product[] getProducts()
  {
    return products;
  }
  
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < products.length; i++)
    {
      buffer.append(products[i]);
      if (i != products.length - 1)
      {
        buffer.append(" + ");
      }
    }
    return buffer.toString();
  }
  
  public int evaluate()
  {
    int result = 0;
    for (Product p : products)
    {
      result += p.evaluate();
    }
    return result;
  }
  
}
