package compiler.language.parser;

import compiler.language.ast.Name;
import compiler.parser.Token;
import compiler.parser.Tokenizer;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class LanguageTokenizer extends Tokenizer
{

  private int state = 0;

  private static final Token[] TEST1 =
  {
    new Token(ParseType.PACKAGE_KEYWORD, null),
    new Token(ParseType.NAME, new Name("bryant")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.NAME, new Name("anthony")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.NAME, new Name("packageName")),
    new Token(ParseType.SEMICOLON, null),

    new Token(ParseType.IMPORT_KEYWORD, null),
    new Token(ParseType.NAME, new Name("test")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.NAME, new Name("import")),
    new Token(ParseType.SEMICOLON, null),

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
