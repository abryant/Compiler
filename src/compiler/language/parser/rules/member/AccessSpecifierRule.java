package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.PACKAGE_KEYWORD;
import static compiler.language.parser.ParseType.PRIVATE_KEYWORD;
import static compiler.language.parser.ParseType.PROTECTED_KEYWORD;
import static compiler.language.parser.ParseType.PUBLIC_KEYWORD;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.AccessSpecifier;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class AccessSpecifierRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION             = new Object[] {};
  private static final Object[] PUBLIC_PRODUCTION            = new Object[] {PUBLIC_KEYWORD};
  private static final Object[] PACKAGE_PRODUCTION           = new Object[] {PACKAGE_KEYWORD};
  private static final Object[] PACKAGE_PROTECTED_PRODUCTION = new Object[] {PACKAGE_KEYWORD,   PROTECTED_KEYWORD};
  private static final Object[] PROTECTED_PACKAGE_PRODUCTION = new Object[] {PROTECTED_KEYWORD, PACKAGE_KEYWORD};
  private static final Object[] PROTECTED_PRODUCTION         = new Object[] {PROTECTED_KEYWORD};
  private static final Object[] PRIVATE_PRODUCTION           = new Object[] {PRIVATE_KEYWORD};

  public AccessSpecifierRule()
  {
    super(ACCESS_SPECIFIER, EMPTY_PRODUCTION,
                            PUBLIC_PRODUCTION,
                            PACKAGE_PRODUCTION,
                            PACKAGE_PROTECTED_PRODUCTION,
                            PROTECTED_PACKAGE_PRODUCTION,
                            PROTECTED_PRODUCTION,
                            PRIVATE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {

    if (types == EMPTY_PRODUCTION)
    {
      return new ParseContainer<AccessSpecifier>(null, null);
    }
    if (types == PUBLIC_PRODUCTION)
    {
      return new ParseContainer<AccessSpecifier>(AccessSpecifier.PUBLIC, (ParseInfo) args[0]);
    }
    if (types == PACKAGE_PRODUCTION)
    {
      return new ParseContainer<AccessSpecifier>(AccessSpecifier.PACKAGE, (ParseInfo) args[0]);
    }
    if (types == PACKAGE_PROTECTED_PRODUCTION || types == PROTECTED_PACKAGE_PRODUCTION)
    {
      return new ParseContainer<AccessSpecifier>(AccessSpecifier.PACKAGE_PROTECTED, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == PROTECTED_PRODUCTION)
    {
      return new ParseContainer<AccessSpecifier>(AccessSpecifier.PROTECTED, (ParseInfo) args[0]);
    }
    if (types == PRIVATE_PRODUCTION)
    {
      return new ParseContainer<AccessSpecifier>(AccessSpecifier.PRIVATE, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
