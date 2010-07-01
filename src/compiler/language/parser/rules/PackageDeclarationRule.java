package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.PACKAGE_DECLARATION;
import static compiler.language.parser.ParseType.PACKAGE_KEYWORD;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.PackageDeclaration;
import compiler.language.ast.QName;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class PackageDeclarationRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {PACKAGE_KEYWORD, QNAME, SEMICOLON};

  public PackageDeclarationRule()
  {
    super(PACKAGE_DECLARATION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new PackageDeclaration((QName) args[1]);
    }
    throw badTypeList();
  }

}
