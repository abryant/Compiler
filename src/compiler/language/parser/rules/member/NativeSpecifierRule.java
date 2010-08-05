package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.NATIVE_KEYWORD;
import static compiler.language.parser.ParseType.NATIVE_SPECIFIER;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.STRING_LITERAL;

import compiler.language.ast.member.NativeSpecifier;
import compiler.language.ast.terminal.StringLiteral;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class NativeSpecifierRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {NATIVE_KEYWORD, LPAREN, STRING_LITERAL, RPAREN};

  public NativeSpecifierRule()
  {
    super(NATIVE_SPECIFIER, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new NativeSpecifier((StringLiteral) args[2]);
    }
    throw badTypeList();
  }

}
