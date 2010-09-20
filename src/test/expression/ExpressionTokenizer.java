package test.expression;

import compiler.parser.Token;
import compiler.parser.Tokenizer;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class ExpressionTokenizer extends Tokenizer<ExpressionType>
{

  private int state = 0;

  @SuppressWarnings("rawtypes")
  private static final Token[] TEST1 =
  {
    new Token<ExpressionType>(ExpressionType.NUMBER, new Number(1)),
    new Token<ExpressionType>(ExpressionType.TIMES, ExpressionType.TIMES),
    new Token<ExpressionType>(ExpressionType.NUMBER, new Number(2)),
    new Token<ExpressionType>(ExpressionType.PLUS, ExpressionType.PLUS),
    new Token<ExpressionType>(ExpressionType.NUMBER, new Number(3)),
    new Token<ExpressionType>(ExpressionType.MINUS, ExpressionType.MINUS),
    new Token<ExpressionType>(ExpressionType.LPAREN, ExpressionType.LPAREN),
    new Token<ExpressionType>(ExpressionType.NUMBER, new Number(12)),
    new Token<ExpressionType>(ExpressionType.MINUS, ExpressionType.MINUS),
    new Token<ExpressionType>(ExpressionType.NUMBER, new Number(6)),
    new Token<ExpressionType>(ExpressionType.RPAREN, ExpressionType.RPAREN)
  };

  @SuppressWarnings("unchecked")
  private Token<ExpressionType>[] tokens = TEST1;



  /**
   * @see compiler.parser.Tokenizer#generateToken()
   */
  @Override
  protected Token<ExpressionType> generateToken()
  {
    if (state < tokens.length)
    {
      state++;
      return tokens[state - 1];
    }

    return null;
  }

}
