package compiler.language.parser.rules.topLevel;

import static compiler.language.parser.ParseType.PACKAGE_DECLARATION;
import static compiler.language.parser.ParseType.PACKAGE_KEYWORD;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.SEMICOLON;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.topLevel.PackageDeclarationAST;
import compiler.language.parser.ParseType;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PackageDeclarationRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(PACKAGE_KEYWORD, QNAME, SEMICOLON);

  @SuppressWarnings("unchecked")
  public PackageDeclarationRule()
  {
    super(PACKAGE_DECLARATION, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];
      return new PackageDeclarationAST(qname, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
