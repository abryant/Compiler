package test.brackets;

import compiler.parser.Token;
import compiler.parser.Tokenizer;

/*
 * Created on 15 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BracketTokenizer extends Tokenizer
{
  private int state = 0;

  private static final Token[] TEST1 =
  {
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.RPAREN, null),
    //new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.RPAREN, null),
    new Token(BracketsType.LPAREN, null),
    new Token(BracketsType.RPAREN, null),
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
