package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.PACKAGE_KEYWORD;
import static compiler.language.parser.ParseType.PRIVATE_KEYWORD;
import static compiler.language.parser.ParseType.PROTECTED_KEYWORD;
import static compiler.language.parser.ParseType.PUBLIC_KEYWORD;

import compiler.language.ast.AccessSpecifier;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class AccessSpecifierRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PUBLIC_PRODUCTION = new Object[] {PUBLIC_KEYWORD};
  // FIXME: shift-reduce conflict because access specifier is at the start of a ClassDefinition:
  // on no input at all, but with lookahead: package
  // the parser does not know whether to shift to something like:
  // package asdf;
  // or reduce via [PACKAGE_DECLARATION <- ] and go on to:
  // package class qwerty {}
  // or should the lookaheads be different?
  private static final Object[] PACKAGE_PRODUCTION = new Object[] {PACKAGE_KEYWORD};
  private static final Object[] PACKAGE_PROTECTED_PRODUCTION = new Object[] {PACKAGE_KEYWORD, PROTECTED_KEYWORD};
  private static final Object[] PROTECTED_PACKAGE_PRODUCTION = new Object[] {PROTECTED_KEYWORD, PACKAGE_KEYWORD};
  private static final Object[] PROTECTED_PRODUCTION = new Object[] {PROTECTED_KEYWORD};
  private static final Object[] PRIVATE_PRODUCTION = new Object[] {PRIVATE_KEYWORD};

  public AccessSpecifierRule()
  {
    super(ACCESS_SPECIFIER, PUBLIC_PRODUCTION, PACKAGE_PRODUCTION, PACKAGE_PROTECTED_PRODUCTION, PROTECTED_PACKAGE_PRODUCTION, PROTECTED_PRODUCTION, PRIVATE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return null;
    }
    if (types == PUBLIC_PRODUCTION)
    {
      return AccessSpecifier.PUBLIC;
    }
    if (types == PACKAGE_PRODUCTION)
    {
      return AccessSpecifier.PACKAGE;
    }
    if (types == PACKAGE_PROTECTED_PRODUCTION || types == PROTECTED_PACKAGE_PRODUCTION)
    {
      return AccessSpecifier.PACKAGE_PROTECTED;
    }
    if (types == PROTECTED_PRODUCTION)
    {
      return AccessSpecifier.PROTECTED;
    }
    if (types == PRIVATE_PRODUCTION)
    {
      return AccessSpecifier.PRIVATE;
    }
    throw badTypeList();
  }

}
