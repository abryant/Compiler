package compiler.language.parser.rules;

import compiler.language.ast.AccessSpecifier;
import compiler.language.parser.Type;
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
  private static final Object[] PUBLIC_PRODUCTION = new Object[] {Type.PUBLIC_KEYWORD};
  private static final Object[] PACKAGE_PRODUCTION = new Object[] {Type.PACKAGE_KEYWORD};
  private static final Object[] PACKAGE_PROTECTED_PRODUCTION = new Object[] {Type.PACKAGE_KEYWORD, Type.PROTECTED_KEYWORD};
  private static final Object[] PROTECTED_PACKAGE_PRODUCTION = new Object[] {Type.PROTECTED_KEYWORD, Type.PACKAGE_KEYWORD};
  private static final Object[] PROTECTED_PRODUCTION = new Object[] {Type.PROTECTED_KEYWORD};
  private static final Object[] PRIVATE_PRODUCTION = new Object[] {Type.PRIVATE_KEYWORD};

  public AccessSpecifierRule()
  {
    super(Type.ACCESS_SPECIFIER, PUBLIC_PRODUCTION, PACKAGE_PRODUCTION, PACKAGE_PROTECTED_PRODUCTION, PROTECTED_PACKAGE_PRODUCTION, PROTECTED_PRODUCTION, PRIVATE_PRODUCTION);
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
