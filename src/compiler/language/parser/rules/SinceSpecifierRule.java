package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.SINCE_KEYWORD;
import static compiler.language.parser.ParseType.SINCE_SPECIFIER;
import static compiler.language.parser.ParseType.VERSION_NUMBER;

import compiler.language.ast.SinceSpecifier;
import compiler.language.ast.VersionNumber;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class SinceSpecifierRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {SINCE_KEYWORD, LPAREN, VERSION_NUMBER, RPAREN};

  public SinceSpecifierRule()
  {
    super(SINCE_SPECIFIER, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new SinceSpecifier((VersionNumber) args[2]);
    }
    throw badTypeList();
  }

}
