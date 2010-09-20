package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.PACKAGE_KEYWORD;
import static compiler.language.parser.ParseType.PRIVATE_KEYWORD;
import static compiler.language.parser.ParseType.PROTECTED_KEYWORD;
import static compiler.language.parser.ParseType.PUBLIC_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.AccessSpecifier;
import compiler.language.ast.member.AccessSpecifierType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class AccessSpecifierRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION             = new Production();
  private static final Production PUBLIC_PRODUCTION            = new Production(PUBLIC_KEYWORD);
  private static final Production PACKAGE_PRODUCTION           = new Production(PACKAGE_KEYWORD);
  private static final Production PACKAGE_PROTECTED_PRODUCTION = new Production(PACKAGE_KEYWORD, PROTECTED_KEYWORD);
  private static final Production PROTECTED_PACKAGE_PRODUCTION = new Production(PROTECTED_KEYWORD, PACKAGE_KEYWORD);
  private static final Production PROTECTED_PRODUCTION         = new Production(PROTECTED_KEYWORD);
  private static final Production PRIVATE_PRODUCTION           = new Production(PRIVATE_KEYWORD);

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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {

    if (EMPTY_PRODUCTION.equals(production))
    {
      return null;
    }
    if (PUBLIC_PRODUCTION.equals(production))
    {
      return new AccessSpecifier(AccessSpecifierType.PUBLIC, (ParseInfo) args[0]);
    }
    if (PACKAGE_PRODUCTION.equals(production))
    {
      return new AccessSpecifier(AccessSpecifierType.PACKAGE, (ParseInfo) args[0]);
    }
    if (PACKAGE_PROTECTED_PRODUCTION.equals(production) || PROTECTED_PACKAGE_PRODUCTION.equals(production))
    {
      return new AccessSpecifier(AccessSpecifierType.PACKAGE_PROTECTED, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (PROTECTED_PRODUCTION.equals(production))
    {
      return new AccessSpecifier(AccessSpecifierType.PROTECTED, (ParseInfo) args[0]);
    }
    if (PRIVATE_PRODUCTION.equals(production))
    {
      return new AccessSpecifier(AccessSpecifierType.PRIVATE, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
