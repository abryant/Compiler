package compiler.language.parser.rules.topLevel;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.IMPORT_DECLARATION;
import static compiler.language.parser.ParseType.IMPORT_KEYWORD;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.STAR;
import static compiler.language.parser.ParseType.STATIC_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.topLevel.ImportDeclaration;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ImportDeclarationRule extends Rule
{

  private static final Object[] NORMAL_PRODUCTION = new Object[] {IMPORT_KEYWORD, QNAME, SEMICOLON};
  private static final Object[] ALL_PRODUCTION = new Object[] {IMPORT_KEYWORD, QNAME, DOT, STAR, SEMICOLON};
  private static final Object[] STATIC_NORMAL_PRODUCTION = new Object[] {IMPORT_KEYWORD, STATIC_KEYWORD, QNAME, SEMICOLON};
  private static final Object[] STATIC_ALL_PRODUCTION = new Object[] {IMPORT_KEYWORD, STATIC_KEYWORD, QNAME, DOT, STAR, SEMICOLON};

  public ImportDeclarationRule()
  {
    super(IMPORT_DECLARATION, NORMAL_PRODUCTION, ALL_PRODUCTION, STATIC_NORMAL_PRODUCTION, STATIC_ALL_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == NORMAL_PRODUCTION)
    {
      QName qname = (QName) args[1];
      return new ImportDeclaration(qname, false, false, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2]));
    }
    if (types == ALL_PRODUCTION)
    {
      QName qname = (QName) args[1];
      return new ImportDeclaration(qname, true, false, ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2], (ParseInfo) args[3], (ParseInfo) args[4]));
    }
    if (types == STATIC_NORMAL_PRODUCTION)
    {
      QName qname = (QName) args[2];
      return new ImportDeclaration(qname, false, true, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3]));
    }
    if (types == STATIC_ALL_PRODUCTION)
    {
      QName qname = (QName) args[2];
      return new ImportDeclaration(qname, true, true, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3], (ParseInfo) args[4], (ParseInfo) args[5]));
    }
    throw badTypeList();
  }

}
