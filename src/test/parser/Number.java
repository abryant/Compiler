package test.parser;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 * 
 */
public class Number
{
  
  private double n;
  
  public Number(double n)
  {
    this.n = n;
  }
  
  public double get()
  {
    return n;
  }
  
  @Override
  public String toString()
  {
    return "" + n;
  }
  
}
