package test.parser;

import compiler.parser.Token;
import compiler.parser.Tokenizer;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 * 
 */
public class ExpressionTokenizer extends Tokenizer
{
  
  private int state = 0;
  
  private static final Token[] TEST1 =
  {
    new Token(ExpressionType.NUMBER, new Number(1)),
    new Token(ExpressionType.TIMES, ExpressionType.TIMES),
    new Token(ExpressionType.NUMBER, new Number(2)),
    new Token(ExpressionType.PLUS, ExpressionType.PLUS),
    new Token(ExpressionType.NUMBER, new Number(3)),
    new Token(ExpressionType.MINUS, ExpressionType.MINUS),
    new Token(ExpressionType.LPAREN, ExpressionType.LPAREN),
    new Token(ExpressionType.NUMBER, new Number(12)),
    new Token(ExpressionType.MINUS, ExpressionType.MINUS),
    new Token(ExpressionType.NUMBER, new Number(6)),
    new Token(ExpressionType.RPAREN, ExpressionType.RPAREN)
  };
  
  private Token[] tokens = TEST1;
  
  
  
  /**
   * @see compiler.parser.Tokenizer#generateToken()
   */
  @Override
  protected Token generateToken()
  {
    if (state < tokens.length)
    {
      state++;
      return tokens[state - 1];
    }
    
    return null;
  }
  
}
