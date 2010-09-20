package compiler.language.parser.rules.topLevel;

import static compiler.language.parser.ParseType.PACKAGE_DECLARATION;
import static compiler.language.parser.ParseType.PACKAGE_KEYWORD;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.topLevel.PackageDeclaration;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class PackageDeclarationRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(PACKAGE_KEYWORD, QNAME, SEMICOLON);

  public PackageDeclarationRule()
  {
    super(PACKAGE_DECLARATION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      QName qname = (QName) args[1];
      return new PackageDeclaration(qname, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
