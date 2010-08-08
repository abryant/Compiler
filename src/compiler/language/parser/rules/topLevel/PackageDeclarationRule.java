package compiler.language.parser.rules.topLevel;

import static compiler.language.parser.ParseType.PACKAGE_DECLARATION;
import static compiler.language.parser.ParseType.PACKAGE_KEYWORD;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.topLevel.PackageDeclaration;
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
      QName qname = (QName) args[1];
      return new PackageDeclaration(qname, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
