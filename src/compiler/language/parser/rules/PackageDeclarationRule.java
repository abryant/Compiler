package compiler.language.parser.rules;

import compiler.language.ast.PackageDeclaration;
import compiler.language.ast.QName;
import compiler.language.parser.Type;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class PackageDeclarationRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {Type.PACKAGE_KEYWORD, Type.QNAME, Type.SEMICOLON};

  public PackageDeclarationRule()
  {
    super(Type.PACKAGE_DECLARATION, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      // there is no package declaration (this is an epsilon rule), so give null as the package declaration
      return null;
    }
    if (types == PRODUCTION)
    {
      return new PackageDeclaration((QName) args[1]);
    }
    throw badTypeList();
  }

}
