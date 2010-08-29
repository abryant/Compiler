package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.SINCE_KEYWORD;
import static compiler.language.parser.ParseType.SINCE_SPECIFIER;
import static compiler.language.parser.ParseType.VERSION_NUMBER;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.SinceSpecifier;
import compiler.language.ast.misc.VersionNumber;
import compiler.parser.ParseException;
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
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      VersionNumber version = (VersionNumber) args[2];
      return new SinceSpecifier(version, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], version.getParseInfo(), (ParseInfo) args[3]));
    }
    throw badTypeList();
  }

}
