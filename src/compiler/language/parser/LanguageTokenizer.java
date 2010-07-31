package compiler.language.parser;

import compiler.language.ast.Expression;
import compiler.language.ast.IntegerLiteral;
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

    new Token(ParseType.IMPORT_KEYWORD, null),
    new Token(ParseType.NAME, new Name("test")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.NAME, new Name("second")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.NAME, new Name("import")),
    new Token(ParseType.SEMICOLON, null),

    new Token(ParseType.PUBLIC_KEYWORD, null),
    new Token(ParseType.ABSTRACT_KEYWORD, null),
    new Token(ParseType.CLASS_KEYWORD, null),
    new Token(ParseType.NAME, new Name("Test")),
    new Token(ParseType.LANGLE, null),
    new Token(ParseType.NAME, new Name("K")),
    new Token(ParseType.EXTENDS_KEYWORD, null),
    new Token(ParseType.NAME, new Name("Object")),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.NAME, new Name("V")),
    new Token(ParseType.RANGLE, null),
    new Token(ParseType.EXTENDS_KEYWORD, null),
    new Token(ParseType.HASH, null),
    new Token(ParseType.NAME, new Name("Object")),
    new Token(ParseType.IMPLEMENTS_KEYWORD, null),
    new Token(ParseType.HASH, null),
    new Token(ParseType.NAME, new Name("test")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.NAME, new Name("Interface")),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.NAME, new Name("other")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.NAME, new Name("Interface")),
    new Token(ParseType.LBRACE, null),

    new Token(ParseType.PUBLIC_KEYWORD, null),
    new Token(ParseType.STATIC_KEYWORD, null),
    new Token(ParseType.CLASS_KEYWORD, null),
    new Token(ParseType.NAME, new Name("NestedTest")),
    new Token(ParseType.EXTENDS_KEYWORD, null),
    new Token(ParseType.NAME, new Name("SecondBase")),
    new Token(ParseType.LANGLE, null),
    new Token(ParseType.NAME, new Name("TypeParam")),
    new Token(ParseType.RANGLE, null),
    new Token(ParseType.LBRACE, null),
    new Token(ParseType.RBRACE, null),

    new Token(ParseType.RBRACE, null),

    new Token(ParseType.PACKAGE_KEYWORD, null),
    new Token(ParseType.IMMUTABLE_KEYWORD, null),
    new Token(ParseType.INTERFACE_KEYWORD, null),
    new Token(ParseType.NAME, new Name("Test")),
    new Token(ParseType.EXTENDS_KEYWORD, null),
    new Token(ParseType.NAME, new Name("Base")),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.HASH, null),
    new Token(ParseType.NAME, new Name("test")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.NAME, new Name("Interface")),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.HASH, null),
    new Token(ParseType.NAME, new Name("other")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.NAME, new Name("Interface")),
    new Token(ParseType.LBRACE, null),

    new Token(ParseType.PRIVATE_KEYWORD, null),
    new Token(ParseType.UNSIGNED_KEYWORD, null),
    new Token(ParseType.BYTE_KEYWORD, null),
    new Token(ParseType.NAME, new Name("variable")),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.NAME, new Name("var2")),
    new Token(ParseType.EQUALS, null),
    new Token(ParseType.EXPRESSION, new Expression()),
    new Token(ParseType.SEMICOLON, null),

    new Token(ParseType.PACKAGE_KEYWORD, null),
    new Token(ParseType.PROTECTED_KEYWORD, null),
    new Token(ParseType.MUTABLE_KEYWORD, null),
    new Token(ParseType.LPAREN, null),
    new Token(ParseType.UNSIGNED_KEYWORD, null),
    new Token(ParseType.LONG_KEYWORD, null),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.HASH, null),
    new Token(ParseType.NAME, new Name("String")),
    new Token(ParseType.RPAREN, null),
    new Token(ParseType.NAME, new Name("a")),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.NAME, new Name("b")),
    new Token(ParseType.SEMICOLON, null),

    new Token(ParseType.STATIC_KEYWORD, null),
    new Token(ParseType.LBRACE, null),
    new Token(ParseType.INT_KEYWORD, null),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.NAME, new Name("String")),
    new Token(ParseType.ARROW, null),
    new Token(ParseType.VOID_KEYWORD, null),
    new Token(ParseType.THROWS_KEYWORD, null),
    new Token(ParseType.NAME, new Name("IOException")),
    new Token(ParseType.RBRACE, null),
    new Token(ParseType.NAME, new Name("someClosure")),
    new Token(ParseType.SEMICOLON, null),

    new Token(ParseType.PRIVATE_KEYWORD, null),
    new Token(ParseType.SINCE_KEYWORD, null),
    new Token(ParseType.LPAREN, null),
    new Token(ParseType.INTEGER_LITERAL, new IntegerLiteral("1")),
    new Token(ParseType.DOT, null),
    new Token(ParseType.INTEGER_LITERAL, new IntegerLiteral("2")),
    new Token(ParseType.RPAREN, null),
    new Token(ParseType.VOID_KEYWORD, null),
    new Token(ParseType.NAME, new Name("method")),
    new Token(ParseType.LPAREN, null),
    new Token(ParseType.FINAL_KEYWORD, null),
    new Token(ParseType.NAME, new Name("String")),
    new Token(ParseType.AT, null),
    new Token(ParseType.NAME, new Name("defaultArg")),
    new Token(ParseType.EQUALS, null),
    new Token(ParseType.EXPRESSION_NO_TUPLE, new Expression()),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.UNSIGNED_KEYWORD, null),
    new Token(ParseType.INT_KEYWORD, null),
    new Token(ParseType.ELLIPSIS, null),
    new Token(ParseType.NAME, new Name("varArg")),
    new Token(ParseType.RPAREN, null),
    new Token(ParseType.LBRACE, null),
    new Token(ParseType.RBRACE, null),

    new Token(ParseType.STATIC_KEYWORD, null),
    new Token(ParseType.LBRACE, null),
    new Token(ParseType.RBRACE, null),

    new Token(ParseType.PROPERTY_KEYWORD, null),
    new Token(ParseType.LBRACE, null),
    new Token(ParseType.NAME, new Name("String")),
    new Token(ParseType.ARROW, null),
    new Token(ParseType.VOID_KEYWORD, null),
    new Token(ParseType.RBRACE, null),
    new Token(ParseType.NAME, new Name("test")),
    new Token(ParseType.EQUALS, null),
    new Token(ParseType.EXPRESSION, new Expression()),
    new Token(ParseType.PACKAGE_KEYWORD, null),
    new Token(ParseType.ASSIGN_KEYWORD, null),
    new Token(ParseType.LBRACE, null),
    new Token(ParseType.RBRACE, null),
    new Token(ParseType.PROTECTED_KEYWORD, null),
    new Token(ParseType.RETRIEVE_KEYWORD, null),
    new Token(ParseType.LBRACE, null),
    new Token(ParseType.SEMICOLON, null),
    new Token(ParseType.RBRACE, null),
    new Token(ParseType.SEMICOLON, null),

    new Token(ParseType.PRIVATE_KEYWORD, null),
    new Token(ParseType.NAME, new Name("Constructor")),
    new Token(ParseType.LPAREN, null),
    new Token(ParseType.RPAREN, null),
    new Token(ParseType.THROWS_KEYWORD, null),
    new Token(ParseType.NAME, new Name("Exception")),
    new Token(ParseType.LBRACE, null),
    new Token(ParseType.RBRACE, null),

    new Token(ParseType.RBRACE, null),


    new Token(ParseType.PROTECTED_KEYWORD, null),
    new Token(ParseType.ENUM_KEYWORD, null),
    new Token(ParseType.NAME, new Name("TestEnum")),
    new Token(ParseType.LBRACE, null),
    new Token(ParseType.NAME, new Name("CONSTANT_A")),
    new Token(ParseType.LPAREN, null),
    new Token(ParseType.EXPRESSION_NO_TUPLE, new Expression()),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.AT, null),
    new Token(ParseType.NAME, new Name("defaultParam")),
    new Token(ParseType.EQUALS, null),
    new Token(ParseType.EXPRESSION_NO_TUPLE, new Expression()),
    new Token(ParseType.RPAREN, null),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.NAME, new Name("CONSTANT_B")),
    new Token(ParseType.LPAREN, null),
    new Token(ParseType.RPAREN, null),
    new Token(ParseType.COMMA, null),
    new Token(ParseType.NAME, new Name("CONSTANT_C")),
    new Token(ParseType.SEMICOLON, null),
    new Token(ParseType.BYTE_KEYWORD, null),
    new Token(ParseType.NAME, new Name("test")),
    new Token(ParseType.SEMICOLON, null),
    new Token(ParseType.RBRACE, null),
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
