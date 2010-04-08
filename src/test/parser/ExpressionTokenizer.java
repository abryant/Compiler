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
  
  /**
   * @see compiler.parser.Tokenizer#generateToken()
   */
  @Override
  protected Token generateToken()
  {
    state++;
    /*
    switch (state)
    {
    case 1:
      return new Token(ExpressionType.NUMBER, new Number(1));
    case 2:
      return new Token(ExpressionType.TIMES, null);
    case 3:
      return new Token(ExpressionType.NUMBER, new Number(2));
    case 4:
      return new Token(ExpressionType.PLUS, null);
    case 5:
      return new Token(ExpressionType.NUMBER, new Number(3));
    case 6:
      return new Token(ExpressionType.LPAREN, null);
    case 7:
      return new Token(ExpressionType.NUMBER, new Number(5));
    case 8:
      return new Token(ExpressionType.PLUS, null);
    case 9:
      return new Token(ExpressionType.NUMBER, new Number(6));
    case 10:
      return new Token(ExpressionType.RPAREN, null);
    default:
      return null;
    }
    */
    
    switch (state)
    {
    case 1:
      return new Token(ExpressionType.NUMBER, new Number(1));
    case 2:
      return new Token(ExpressionType.TIMES, ExpressionType.TIMES);
    case 3:
      return new Token(ExpressionType.NUMBER, new Number(2));
    case 4:
      return new Token(ExpressionType.PLUS, ExpressionType.PLUS);
    case 5:
      return new Token(ExpressionType.NUMBER, new Number(3));
    case 6:
      return new Token(ExpressionType.TIMES, ExpressionType.TIMES);
    case 7:
      return new Token(ExpressionType.LPAREN, ExpressionType.LPAREN);
    case 8:
      return new Token(ExpressionType.NUMBER, new Number(12));
    case 9:
      return new Token(ExpressionType.MINUS, ExpressionType.MINUS);
    case 10:
      return new Token(ExpressionType.NUMBER, new Number(6));
    case 11:
      return new Token(ExpressionType.RPAREN, ExpressionType.RPAREN);
    default:
      return null;
    }
    
  }
  
}
