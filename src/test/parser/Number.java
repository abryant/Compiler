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
  
  private int n;
  
  public Number(int n)
  {
    this.n = n;
  }
  
  public int get()
  {
    return n;
  }
  
  @Override
  public String toString()
  {
    return "" + n;
  }
  
}
