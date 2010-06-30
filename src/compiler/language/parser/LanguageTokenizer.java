package compiler.language.parser;

import compiler.language.ast.Name;
import compiler.language.ast.TypeDefinition;
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
    new Token(Type.PACKAGE_KEYWORD, null),
    new Token(Type.NAME, new Name("bryant")),
    new Token(Type.DOT, null),
    new Token(Type.NAME, new Name("anthony")),
    new Token(Type.DOT, null),
    new Token(Type.NAME, new Name("packageName")),
    new Token(Type.SEMICOLON, null),

    new Token(Type.IMPORT_KEYWORD, null),
    new Token(Type.NAME, new Name("test")),
    new Token(Type.DOT, null),
    new Token(Type.NAME, new Name("import")),
    new Token(Type.SEMICOLON, null),

    new Token(Type.TYPE_DEFINITION, new TypeDefinition())
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
