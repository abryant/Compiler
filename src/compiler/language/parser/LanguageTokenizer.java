package compiler.language.parser;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ThisAccessExpression;
import compiler.language.ast.terminal.IntegerLiteral;
import compiler.language.ast.terminal.Name;
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
    new Token(ParseType.PACKAGE_KEYWORD, info(0)),
    new Token(ParseType.NAME, new Name("bryant", info(1))),
    new Token(ParseType.DOT, info(2)),
    new Token(ParseType.NAME, new Name("anthony", info(3))),
    new Token(ParseType.DOT, info(4)),
    new Token(ParseType.NAME, new Name("packageName", info(5))),
    new Token(ParseType.SEMICOLON, info(6)),

    new Token(ParseType.IMPORT_KEYWORD, info(10)),
    new Token(ParseType.NAME, new Name("test", info(11))),
    new Token(ParseType.DOT, info(12)),
    new Token(ParseType.NAME, new Name("import", info(13))),
    new Token(ParseType.SEMICOLON, info(14)),

    new Token(ParseType.IMPORT_KEYWORD, info(20)),
    new Token(ParseType.NAME, new Name("test", info(21))),
    new Token(ParseType.DOT, info(22)),
    new Token(ParseType.NAME, new Name("second", info(23))),
    new Token(ParseType.DOT, info(24)),
    new Token(ParseType.NAME, new Name("import", info(25))),
    new Token(ParseType.SEMICOLON, info(26)),

    new Token(ParseType.PUBLIC_KEYWORD, info(30)),
    new Token(ParseType.ABSTRACT_KEYWORD, info(31)),
    new Token(ParseType.CLASS_KEYWORD, info(32)),
    new Token(ParseType.NAME, new Name("Test", info(33))),
    new Token(ParseType.LANGLE, info(34)),
    new Token(ParseType.NAME, new Name("K", info(35))),
    new Token(ParseType.EXTENDS_KEYWORD, info(36)),
    new Token(ParseType.NAME, new Name("Object", info(37))),
    new Token(ParseType.COMMA, info(38)),
    new Token(ParseType.NAME, new Name("V", info(39))),
    new Token(ParseType.RANGLE, info(40)),
    new Token(ParseType.EXTENDS_KEYWORD, info(41)),
    new Token(ParseType.HASH, info(42)),
    new Token(ParseType.NAME, new Name("Object", info(43))),
    new Token(ParseType.IMPLEMENTS_KEYWORD, info(44)),
    new Token(ParseType.HASH, info(45)),
    new Token(ParseType.NAME, new Name("test", info(46))),
    new Token(ParseType.DOT, info(47)),
    new Token(ParseType.NAME, new Name("Interface", info(48))),
    new Token(ParseType.COMMA, info(49)),
    new Token(ParseType.NAME, new Name("other", info(50))),
    new Token(ParseType.DOT, info(51)),
    new Token(ParseType.NAME, new Name("Interface", info(52))),
    new Token(ParseType.LBRACE, info(53)),

    new Token(ParseType.PUBLIC_KEYWORD, info(60)),
    new Token(ParseType.STATIC_KEYWORD, info(61)),
    new Token(ParseType.CLASS_KEYWORD, info(62)),
    new Token(ParseType.NAME, new Name("NestedTest", info(63))),
    new Token(ParseType.EXTENDS_KEYWORD, info(64)),
    new Token(ParseType.NAME, new Name("SecondBase", info(65))),
    new Token(ParseType.LANGLE, info(66)),
    new Token(ParseType.NAME, new Name("TypeParam", info(67))),
    new Token(ParseType.RANGLE, info(68)),
    new Token(ParseType.LBRACE, info(69)),
    new Token(ParseType.RBRACE, info(70)),

    new Token(ParseType.RBRACE, info(100)),

    new Token(ParseType.PACKAGE_KEYWORD, info(110)),
    new Token(ParseType.IMMUTABLE_KEYWORD, info(111)),
    new Token(ParseType.INTERFACE_KEYWORD, info(112)),
    new Token(ParseType.NAME, new Name("Test", info(113))),
    new Token(ParseType.EXTENDS_KEYWORD, info(114)),
    new Token(ParseType.NAME, new Name("Base", info(115))),
    new Token(ParseType.COMMA, info(116)),
    new Token(ParseType.HASH, info(117)),
    new Token(ParseType.NAME, new Name("test", info(118))),
    new Token(ParseType.DOT, info(119)),
    new Token(ParseType.NAME, new Name("Interface", info(120))),
    new Token(ParseType.COMMA, info(121)),
    new Token(ParseType.HASH, info(122)),
    new Token(ParseType.NAME, new Name("other", info(123))),
    new Token(ParseType.DOT, info(124)),
    new Token(ParseType.NAME, new Name("Interface", info(125))),
    new Token(ParseType.LBRACE, info(126)),

    new Token(ParseType.PRIVATE_KEYWORD, info(130)),
    new Token(ParseType.UNSIGNED_KEYWORD, info(131)),
    new Token(ParseType.BYTE_KEYWORD, info(132)),
    new Token(ParseType.NAME, new Name("variable", info(133))),
    new Token(ParseType.COMMA, info(134)),
    new Token(ParseType.NAME, new Name("var2", info(135))),
    new Token(ParseType.SEMICOLON, info(136)),

    new Token(ParseType.PACKAGE_KEYWORD, info(150)),
    new Token(ParseType.PROTECTED_KEYWORD, info(151)),
    new Token(ParseType.MUTABLE_KEYWORD, info(152)),
    new Token(ParseType.LPAREN, info(153)),
    new Token(ParseType.UNSIGNED_KEYWORD, info(154)),
    new Token(ParseType.LONG_KEYWORD, info(155)),
    new Token(ParseType.COMMA, info(156)),
    new Token(ParseType.HASH, info(157)),
    new Token(ParseType.NAME, new Name("String", info(158))),
    new Token(ParseType.RPAREN, info(159)),
    new Token(ParseType.NAME, new Name("a", info(160))),
    new Token(ParseType.COMMA, info(161)),
    new Token(ParseType.NAME, new Name("b", info(162))),
    new Token(ParseType.SEMICOLON, info(163)),

    new Token(ParseType.STATIC_KEYWORD, info(164)),
    new Token(ParseType.LBRACE, info(165)),
    new Token(ParseType.INT_KEYWORD, info(166)),
    new Token(ParseType.COMMA, info(167)),
    new Token(ParseType.NAME, new Name("String", info(168))),
    new Token(ParseType.ARROW, info(169)),
    new Token(ParseType.VOID_KEYWORD, info(170)),
    new Token(ParseType.THROWS_KEYWORD, info(171)),
    new Token(ParseType.NAME, new Name("IOException", info(172))),
    new Token(ParseType.RBRACE, info(173)),
    new Token(ParseType.NAME, new Name("someClosure", info(174))),
    new Token(ParseType.SEMICOLON, info(175)),

    new Token(ParseType.PRIVATE_KEYWORD, info(190)),
    new Token(ParseType.SINCE_KEYWORD, info(191)),
    new Token(ParseType.LPAREN, info(192)),
    new Token(ParseType.INTEGER_LITERAL, new IntegerLiteral("1", info(193))),
    new Token(ParseType.DOT, info(194)),
    new Token(ParseType.INTEGER_LITERAL, new IntegerLiteral("2", info(195))),
    new Token(ParseType.RPAREN, info(196)),
    new Token(ParseType.VOID_KEYWORD, info(197)),
    new Token(ParseType.NAME, new Name("method", info(198))),
    new Token(ParseType.LPAREN, info(199)),
    new Token(ParseType.FINAL_KEYWORD, info(200)),
    new Token(ParseType.NAME, new Name("String", info(201))),
    new Token(ParseType.AT, info(202)),
    new Token(ParseType.NAME, new Name("defaultArg", info(203))),
    new Token(ParseType.EQUALS, info(204)),
    new Token(ParseType.EXPRESSION_NO_TUPLE, new ThisAccessExpression(null, info(205))),
    new Token(ParseType.COMMA, info(220)),
    new Token(ParseType.UNSIGNED_KEYWORD, info(221)),
    new Token(ParseType.INT_KEYWORD, info(222)),
    new Token(ParseType.ELLIPSIS, info(223)),
    new Token(ParseType.NAME, new Name("varArg", info(224))),
    new Token(ParseType.RPAREN, info(225)),
    new Token(ParseType.LBRACE, info(226)),
    new Token(ParseType.RBRACE, info(227)),

    new Token(ParseType.STATIC_KEYWORD, info(240)),
    new Token(ParseType.LBRACE, info(241)),
    new Token(ParseType.RBRACE, info(242)),

    new Token(ParseType.PROPERTY_KEYWORD, info(250)),
    new Token(ParseType.LBRACE, info(251)),
    new Token(ParseType.NAME, new Name("String", info(252))),
    new Token(ParseType.ARROW, info(253)),
    new Token(ParseType.VOID_KEYWORD, info(254)),
    new Token(ParseType.RBRACE, info(255)),
    new Token(ParseType.NAME, new Name("test", info(256))),
    new Token(ParseType.EQUALS, info(257)),
    new Token(ParseType.EXPRESSION, new ThisAccessExpression(null, info(258))),
    new Token(ParseType.PACKAGE_KEYWORD, info(280)),
    new Token(ParseType.ASSIGN_KEYWORD, info(281)),
    new Token(ParseType.LBRACE, info(282)),
    new Token(ParseType.RBRACE, info(283)),
    new Token(ParseType.PROTECTED_KEYWORD, info(284)),
    new Token(ParseType.RETRIEVE_KEYWORD, info(285)),
    new Token(ParseType.LBRACE, info(286)),
    new Token(ParseType.SEMICOLON, info(287)),
    new Token(ParseType.RBRACE, info(288)),
    new Token(ParseType.SEMICOLON, info(289)),

    new Token(ParseType.PRIVATE_KEYWORD, info(300)),
    new Token(ParseType.NAME, new Name("Constructor", info(301))),
    new Token(ParseType.LPAREN, info(302)),
    new Token(ParseType.RPAREN, info(303)),
    new Token(ParseType.THROWS_KEYWORD, info(304)),
    new Token(ParseType.NAME, new Name("Exception", info(305))),
    new Token(ParseType.LBRACE, info(306)),
    new Token(ParseType.RBRACE, info(307)),

    new Token(ParseType.RBRACE, info(320)),


    new Token(ParseType.PROTECTED_KEYWORD, info(400)),
    new Token(ParseType.ENUM_KEYWORD, info(401)),
    new Token(ParseType.NAME, new Name("TestEnum", info(402))),
    new Token(ParseType.LBRACE, info(403)),
    new Token(ParseType.NAME, new Name("CONSTANT_A", info(404))),
    new Token(ParseType.LPAREN, info(405)),
    new Token(ParseType.EXPRESSION_NO_TUPLE, new ThisAccessExpression(null, info(406))),
    new Token(ParseType.COMMA, info(407)),
    new Token(ParseType.AT, info(408)),
    new Token(ParseType.NAME, new Name("defaultParam", info(409))),
    new Token(ParseType.EQUALS, info(410)),
    new Token(ParseType.EXPRESSION_NO_TUPLE, new ThisAccessExpression(null, info(411))),
    new Token(ParseType.RPAREN, info(430)),
    new Token(ParseType.COMMA, info(431)),
    new Token(ParseType.NAME, new Name("CONSTANT_B", info(432))),
    new Token(ParseType.LPAREN, info(433)),
    new Token(ParseType.RPAREN, info(434)),
    new Token(ParseType.COMMA, info(435)),
    new Token(ParseType.NAME, new Name("CONSTANT_C", info(436))),
    new Token(ParseType.SEMICOLON, info(437)),
    new Token(ParseType.BYTE_KEYWORD, info(438)),
    new Token(ParseType.NAME, new Name("test", info(439))),
    new Token(ParseType.SEMICOLON, info(440)),
    new Token(ParseType.RBRACE, info(441)),
  };

  private Token[] tokens = TEST1;

  /**
   * Creates a ParseInfo object for the specified position on the first line
   * @param position - the position of the item with this ParseInfo object
   * @return a new ParseInfo object with the specified position
   */
  private static ParseInfo info(int position)
  {
    return new ParseInfo(1, position, 1, position + 1);
  }

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
