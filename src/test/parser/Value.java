package test.parser;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 * 
 */
public class Value
{
  
  private Number num = null;
  private Expression expr = null;
  
  public Value(Number num)
  {
    this.num = num;
  }
  
  public Value(Expression expr)
  {
    this.expr = expr;
  }
  
  public Number getNumber()
  {
    return num;
  }
  
  public Expression getExpression()
  {
    return expr;
  }
  
  @Override
  public String toString()
  {
    if (num != null)
    {
      return num.toString();
    }
    return "(" + expr.toString() + ")";
  }
  
  public int evaluate()
  {
    if (num != null)
    {
      return num.get();
    }
    return expr.evaluate();
  }
  
}
