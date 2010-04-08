package test.parser;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 * 
 */
public class Expression
{
  
  private Sum sum;
  
  public Expression(Sum sum)
  {
    this.sum = sum;
  }
  
  public Sum getSum()
  {
    return sum;
  }
  
  @Override
  public String toString()
  {
    return sum.toString();
  }
  
  public int evaluate()
  {
    return sum.evaluate();
  }
  
}
