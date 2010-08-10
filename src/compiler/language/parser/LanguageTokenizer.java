package compiler.language.parser;

import compiler.language.ast.ParseInfo;
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
    new Token(ParseType.NAME, new Name("bryant", info(10))),
    new Token(ParseType.DOT, info(20)),
    new Token(ParseType.NAME, new Name("anthony", info(30))),
    new Token(ParseType.DOT, info(40)),
    new Token(ParseType.NAME, new Name("packageName", info(50))),
    new Token(ParseType.SEMICOLON, info(60)),

    new Token(ParseType.IMPORT_KEYWORD, info(100)),
    new Token(ParseType.NAME, new Name("test", info(110))),
    new Token(ParseType.DOT, info(120)),
    new Token(ParseType.NAME, new Name("import", info(130))),
    new Token(ParseType.SEMICOLON, info(140)),

    new Token(ParseType.IMPORT_KEYWORD, info(200)),
    new Token(ParseType.NAME, new Name("test", info(210))),
    new Token(ParseType.DOT, info(220)),
    new Token(ParseType.NAME, new Name("second", info(230))),
    new Token(ParseType.DOT, info(240)),
    new Token(ParseType.NAME, new Name("import", info(250))),
    new Token(ParseType.SEMICOLON, info(260)),

    new Token(ParseType.PUBLIC_KEYWORD, info(300)),
    new Token(ParseType.ABSTRACT_KEYWORD, info(310)),
    new Token(ParseType.CLASS_KEYWORD, info(320)),
    new Token(ParseType.NAME, new Name("Test", info(330))),
    new Token(ParseType.LANGLE, info(340)),
    new Token(ParseType.NAME, new Name("K", info(350))),
    new Token(ParseType.EXTENDS_KEYWORD, info(360)),
    new Token(ParseType.NAME, new Name("Object", info(370))),
    new Token(ParseType.COMMA, info(380)),

    new Token(ParseType.NAME, new Name("V", info(390))),
    new Token(ParseType.EXTENDS_KEYWORD, info(391)),
    new Token(ParseType.NAME, new Name("X", info(392))),
    new Token(ParseType.LANGLE, info(393)),
    new Token(ParseType.NAME, new Name("Y", info(394))),
    new Token(ParseType.DOUBLE_RANGLE, new ParseInfo(1, 395, 1, 397)),

    new Token(ParseType.EXTENDS_KEYWORD, info(410)),
    new Token(ParseType.HASH, info(420)),
    new Token(ParseType.NAME, new Name("Object", info(430))),
    new Token(ParseType.IMPLEMENTS_KEYWORD, info(440)),
    new Token(ParseType.HASH, info(450)),
    new Token(ParseType.NAME, new Name("test", info(460))),
    new Token(ParseType.DOT, info(470)),
    new Token(ParseType.NAME, new Name("Interface", info(480))),
    new Token(ParseType.COMMA, info(490)),
    new Token(ParseType.NAME, new Name("other", info(500))),
    new Token(ParseType.DOT, info(510)),
    new Token(ParseType.NAME, new Name("Interface", info(520))),
    new Token(ParseType.LANGLE, info(530)),
    new Token(ParseType.HASH, info(540)),
    new Token(ParseType.NAME, new Name("Type", info(550))),
    new Token(ParseType.LANGLE, info(560)),
    new Token(ParseType.NAME, new Name("Generic", info(570))),
    new Token(ParseType.RANGLE, info(580)),
    new Token(ParseType.RANGLE, info(590)),
    new Token(ParseType.LBRACE, info(595)),

    new Token(ParseType.PUBLIC_KEYWORD, info(600)),
    new Token(ParseType.STATIC_KEYWORD, info(610)),
    new Token(ParseType.CLASS_KEYWORD, info(620)),
    new Token(ParseType.NAME, new Name("NestedTest", info(630))),
    new Token(ParseType.EXTENDS_KEYWORD, info(640)),
    new Token(ParseType.NAME, new Name("SecondBase", info(650))),
    new Token(ParseType.LANGLE, info(660)),
    new Token(ParseType.NAME, new Name("TypeParam", info(670))),
    new Token(ParseType.RANGLE, info(680)),
    new Token(ParseType.LBRACE, info(690)),
    new Token(ParseType.RBRACE, info(700)),

    new Token(ParseType.RBRACE, info(1000)),

    new Token(ParseType.PACKAGE_KEYWORD, info(1100)),
    new Token(ParseType.IMMUTABLE_KEYWORD, info(1110)),
    new Token(ParseType.INTERFACE_KEYWORD, info(1120)),
    new Token(ParseType.NAME, new Name("Test", info(1130))),
    new Token(ParseType.EXTENDS_KEYWORD, info(1140)),
    new Token(ParseType.NAME, new Name("Base", info(1150))),
    new Token(ParseType.COMMA, info(1160)),
    new Token(ParseType.HASH, info(1170)),
    new Token(ParseType.NAME, new Name("test", info(1180))),
    new Token(ParseType.DOT, info(1190)),
    new Token(ParseType.NAME, new Name("Interface", info(1200))),
    new Token(ParseType.COMMA, info(1210)),
    new Token(ParseType.HASH, info(1220)),
    new Token(ParseType.NAME, new Name("other", info(1230))),
    new Token(ParseType.DOT, info(1240)),
    new Token(ParseType.NAME, new Name("Interface", info(1250))),
    new Token(ParseType.LBRACE, info(1260)),

    /* TODO: fix assignees
    new Token(ParseType.PRIVATE_KEYWORD, info(1300)),
    new Token(ParseType.UNSIGNED_KEYWORD, info(1310)),
    new Token(ParseType.BYTE_KEYWORD, info(1320)),
    new Token(ParseType.NAME, new Name("variable", info(1330))),
    new Token(ParseType.COMMA, info(1340)),
    new Token(ParseType.NAME, new Name("var2", info(1350))),
    new Token(ParseType.SEMICOLON, info(1360)),

    new Token(ParseType.PACKAGE_KEYWORD, info(1500)),
    new Token(ParseType.PROTECTED_KEYWORD, info(1510)),
    new Token(ParseType.MUTABLE_KEYWORD, info(1520)),
    new Token(ParseType.LPAREN, info(1530)),
    new Token(ParseType.UNSIGNED_KEYWORD, info(1540)),
    new Token(ParseType.LONG_KEYWORD, info(1550)),
    new Token(ParseType.COMMA, info(1560)),
    new Token(ParseType.HASH, info(1570)),
    new Token(ParseType.NAME, new Name("String", info(1580))),
    new Token(ParseType.RPAREN, info(1590)),
    new Token(ParseType.NAME, new Name("a", info(1600))),
    new Token(ParseType.COMMA, info(1610)),
    new Token(ParseType.NAME, new Name("b", info(1620))),
    new Token(ParseType.SEMICOLON, info(1630)),

    new Token(ParseType.STATIC_KEYWORD, info(1640)),
    new Token(ParseType.LBRACE, info(1650)),
    new Token(ParseType.INT_KEYWORD, info(1660)),
    new Token(ParseType.COMMA, info(1670)),
    new Token(ParseType.NAME, new Name("String", info(1680))),
    new Token(ParseType.ARROW, info(1690)),
    new Token(ParseType.VOID_KEYWORD, info(1700)),
    new Token(ParseType.THROWS_KEYWORD, info(1710)),
    new Token(ParseType.NAME, new Name("IOException", info(1720))),
    new Token(ParseType.RBRACE, info(1730)),
    new Token(ParseType.NAME, new Name("someClosure", info(1740))),
    new Token(ParseType.SEMICOLON, info(1750)),
    */

    new Token(ParseType.PRIVATE_KEYWORD, info(1900)),
    new Token(ParseType.SINCE_KEYWORD, info(1910)),
    new Token(ParseType.LPAREN, info(1920)),
    new Token(ParseType.INTEGER_LITERAL, new IntegerLiteral("1", info(1930))),
    new Token(ParseType.DOT, info(1940)),
    new Token(ParseType.INTEGER_LITERAL, new IntegerLiteral("2", info(1950))),
    new Token(ParseType.RPAREN, info(1960)),
    new Token(ParseType.VOID_KEYWORD, info(1970)),
    new Token(ParseType.NAME, new Name("method", info(1980))),
    new Token(ParseType.LPAREN, info(1990)),
    /* TODO: fix assignees
    new Token(ParseType.FINAL_KEYWORD, info(2000)),
    new Token(ParseType.NAME, new Name("String", info(2010))),
    new Token(ParseType.AT, info(2020)),
    new Token(ParseType.NAME, new Name("defaultArg", info(2030))),
    new Token(ParseType.EQUALS, info(2040)),
    new Token(ParseType.EXPRESSION_NO_TUPLE, new ThisAccessExpression(null, info(2050))),
    new Token(ParseType.COMMA, info(2200)),
    */
    new Token(ParseType.UNSIGNED_KEYWORD, info(2210)),
    new Token(ParseType.INT_KEYWORD, info(2220)),
    new Token(ParseType.ELLIPSIS, info(2230)),
    new Token(ParseType.NAME, new Name("varArg", info(2240))),
    new Token(ParseType.RPAREN, info(2250)),
    new Token(ParseType.LBRACE, info(2260)),
    new Token(ParseType.RBRACE, info(2270)),

    new Token(ParseType.STATIC_KEYWORD, info(2400)),
    new Token(ParseType.LBRACE, info(2410)),
    new Token(ParseType.RBRACE, info(2420)),

    new Token(ParseType.PROPERTY_KEYWORD, info(2500)),
    new Token(ParseType.LBRACE, info(2510)),
    new Token(ParseType.NAME, new Name("String", info(2520))),
    new Token(ParseType.ARROW, info(2530)),
    new Token(ParseType.VOID_KEYWORD, info(2540)),
    new Token(ParseType.RBRACE, info(2550)),
    new Token(ParseType.NAME, new Name("test", info(2560))),
    /* TODO: finish expressions
    new Token(ParseType.EQUALS, info(2570)),
    new Token(ParseType.EXPRESSION, new ThisAccessExpression(null, info(2580))),
    */
    new Token(ParseType.PACKAGE_KEYWORD, info(2800)),
    new Token(ParseType.ASSIGN_KEYWORD, info(2810)),
    new Token(ParseType.LBRACE, info(2820)),
    new Token(ParseType.RBRACE, info(2830)),
    new Token(ParseType.PROTECTED_KEYWORD, info(2840)),
    new Token(ParseType.RETRIEVE_KEYWORD, info(2850)),
    new Token(ParseType.LBRACE, info(2860)),
    new Token(ParseType.SEMICOLON, info(2870)),
    new Token(ParseType.RBRACE, info(2880)),
    new Token(ParseType.SEMICOLON, info(2890)),

    new Token(ParseType.PRIVATE_KEYWORD, info(3000)),
    new Token(ParseType.NAME, new Name("Constructor", info(3010))),
    new Token(ParseType.LPAREN, info(3020)),
    new Token(ParseType.RPAREN, info(3030)),
    new Token(ParseType.THROWS_KEYWORD, info(3040)),
    new Token(ParseType.NAME, new Name("Exception", info(3050))),
    new Token(ParseType.LBRACE, info(3060)),
    new Token(ParseType.RBRACE, info(3070)),

    new Token(ParseType.RBRACE, info(3200)),


    new Token(ParseType.PROTECTED_KEYWORD, info(4000)),
    new Token(ParseType.ENUM_KEYWORD, info(4010)),
    new Token(ParseType.NAME, new Name("TestEnum", info(4011))),

    new Token(ParseType.EXTENDS_KEYWORD, info(4021)),
    // #a.b.C<#V, W.X<Y>>.D.E<Z>.F
    new Token(ParseType.HASH, info(4030)),
    new Token(ParseType.NAME, new Name("a", info(4031))),
    new Token(ParseType.DOT, info(4032)),
    new Token(ParseType.NAME, new Name("b", info(4033))),
    new Token(ParseType.DOT, info(4034)),
    new Token(ParseType.NAME, new Name("C", info(4035))),
    new Token(ParseType.LANGLE, info(4036)),
    new Token(ParseType.HASH, info(4037)),
    new Token(ParseType.NAME, new Name("V", info(4038))),
    new Token(ParseType.COMMA, info(4039)),
    new Token(ParseType.NAME, new Name("W", info(4040))),
    new Token(ParseType.DOT, info(4041)),
    new Token(ParseType.NAME, new Name("X", info(4042))),
    new Token(ParseType.LANGLE, info(4043)),
    new Token(ParseType.NAME, new Name("Y", info(4044))),
    new Token(ParseType.DOUBLE_RANGLE, new ParseInfo(1, 4045, 1, 4047)),
    new Token(ParseType.DOT, info(4048)),
    new Token(ParseType.NAME, new Name("D", info(4049))),
    new Token(ParseType.DOT, info(4050)),
    new Token(ParseType.NAME, new Name("E", info(4051))),
    new Token(ParseType.LANGLE, info(4052)),
    new Token(ParseType.NAME, new Name("Z", info(4053))),
    new Token(ParseType.RANGLE, info(4054)),
    new Token(ParseType.DOT, info(4055)),
    new Token(ParseType.NAME, new Name("F", info(4056))),

    new Token(ParseType.LBRACE, info(4100)),
    new Token(ParseType.NAME, new Name("CONSTANT_A", info(4110))),
    new Token(ParseType.LPAREN, info(4120)),
    /* TODO: finish expressions
    new Token(ParseType.EXPRESSION_NO_TUPLE, new ThisAccessExpression(null, info(4060))),
    new Token(ParseType.COMMA, info(4070)),
    new Token(ParseType.AT, info(4080)),
    new Token(ParseType.NAME, new Name("defaultParam", info(4090))),
    new Token(ParseType.EQUALS, info(4100)),
    new Token(ParseType.EXPRESSION_NO_TUPLE, new ThisAccessExpression(null, info(4110))),
    */
    new Token(ParseType.RPAREN, info(4300)),
    new Token(ParseType.COMMA, info(4310)),
    new Token(ParseType.NAME, new Name("CONSTANT_B", info(4320))),
    new Token(ParseType.LPAREN, info(4330)),
    new Token(ParseType.RPAREN, info(4340)),
    new Token(ParseType.COMMA, info(4350)),
    new Token(ParseType.NAME, new Name("CONSTANT_C", info(4360))),
    new Token(ParseType.SEMICOLON, info(4370)),
    /* TODO: fix assignees
    new Token(ParseType.BYTE_KEYWORD, info(4380)),
    new Token(ParseType.NAME, new Name("test", info(4390))),
    new Token(ParseType.SEMICOLON, info(4400)),
    */
    new Token(ParseType.RBRACE, info(4410)),
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
