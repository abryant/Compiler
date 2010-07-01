package compiler.language.parser;

import static compiler.language.parser.ParseType.ABSTRACT_KEYWORD;
import static compiler.language.parser.ParseType.CLASS_KEYWORD;
import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.IMPORT_KEYWORD;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.PACKAGE_KEYWORD;
import static compiler.language.parser.ParseType.PUBLIC_KEYWORD;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.Member;
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
    new Token(PACKAGE_KEYWORD, null),
    new Token(NAME, new Name("bryant")),
    new Token(DOT, null),
    new Token(NAME, new Name("anthony")),
    new Token(DOT, null),
    new Token(NAME, new Name("packageName")),
    new Token(SEMICOLON, null),

    new Token(IMPORT_KEYWORD, null),
    new Token(NAME, new Name("test")),
    new Token(DOT, null),
    new Token(NAME, new Name("import")),
    new Token(SEMICOLON, null),

    new Token(IMPORT_KEYWORD, null),
    new Token(NAME, new Name("test")),
    new Token(DOT, null),
    new Token(NAME, new Name("second")),
    new Token(DOT, null),
    new Token(NAME, new Name("import")),
    new Token(SEMICOLON, null),

    new Token(PUBLIC_KEYWORD, null),
    new Token(ABSTRACT_KEYWORD, null),
    new Token(CLASS_KEYWORD, null),
    new Token(NAME, new Name("Test")),
    new Token(LBRACE, null),
    new Token(MEMBER_LIST, new Member[] {}),
    new Token(RBRACE, null),
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
