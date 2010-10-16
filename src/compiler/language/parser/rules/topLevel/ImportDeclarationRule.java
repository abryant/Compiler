package compiler.language.parser.rules.topLevel;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.IMPORT_DECLARATION;
import static compiler.language.parser.ParseType.IMPORT_KEYWORD;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.STAR;
import static compiler.language.parser.ParseType.STATIC_KEYWORD;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.topLevel.ImportDeclarationAST;
import compiler.language.parser.ParseType;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ImportDeclarationRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NORMAL_PRODUCTION = new Production<ParseType>(IMPORT_KEYWORD, QNAME, SEMICOLON);
  private static final Production<ParseType> ALL_PRODUCTION = new Production<ParseType>(IMPORT_KEYWORD, QNAME, DOT, STAR, SEMICOLON);
  private static final Production<ParseType> STATIC_NORMAL_PRODUCTION = new Production<ParseType>(IMPORT_KEYWORD, STATIC_KEYWORD, QNAME, SEMICOLON);
  private static final Production<ParseType> STATIC_ALL_PRODUCTION = new Production<ParseType>(IMPORT_KEYWORD, STATIC_KEYWORD, QNAME, DOT, STAR, SEMICOLON);

  @SuppressWarnings("unchecked")
  public ImportDeclarationRule()
  {
    super(IMPORT_DECLARATION, NORMAL_PRODUCTION, ALL_PRODUCTION, STATIC_NORMAL_PRODUCTION, STATIC_ALL_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (NORMAL_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];
      return new ImportDeclarationAST(qname, false, false, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2]));
    }
    if (ALL_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];
      return new ImportDeclarationAST(qname, true, false, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2], (ParseInfo) args[3], (ParseInfo) args[4]));
    }
    if (STATIC_NORMAL_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[2];
      return new ImportDeclarationAST(qname, false, true, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3]));
    }
    if (STATIC_ALL_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[2];
      return new ImportDeclarationAST(qname, true, true, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3], (ParseInfo) args[4], (ParseInfo) args[5]));
    }
    throw badTypeList();
  }

}
