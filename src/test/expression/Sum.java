package test.expression;

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
  private boolean[] signs;
  
  public Sum(Product[] products, boolean[] signs)
  {
    this.products = products;
    this.signs = signs;
    if (products.length != signs.length)
    {
      throw new IllegalArgumentException();
    }
  }
  
  public Product[] getProducts()
  {
    return products;
  }
  
  public boolean[] getSigns()
  {
    return signs;
  }
  
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < products.length; i++)
    {
      if (signs[i])
      {
        // the current element is positive
        buffer.append(i == 0 ? "" : " + ");
      }
      else
      {
        buffer.append(i == 0 ? "- " : " - ");
      }
      buffer.append(products[i]);
    }
    return buffer.toString();
  }
  
  public double evaluate()
  {
    double result = 0;
    for (int i = 0; i < products.length; i++)
    {
      if (signs[i])
      {
        result += products[i].evaluate();
      }
      else
      {
        result -= products[i].evaluate();
      }
    }
    return result;
  }
  
}
