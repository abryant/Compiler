package test.expression;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 * 
 */
public class Product
{
  
  private Value[] values;
  
  public Product(Value[] values)
  {
    this.values = values;
  }
  
  public Value[] getValues()
  {
    return values;
  }
  
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < values.length; i++)
    {
      buffer.append(values[i]);
      if (i != values.length - 1)
      {
        buffer.append(" * ");
      }
    }
    return buffer.toString();
  }
  
  public double evaluate()
  {
    double result = 1;
    for (Value v : values)
    {
      result *= v.evaluate();
    }
    return result;
  }
  
}
