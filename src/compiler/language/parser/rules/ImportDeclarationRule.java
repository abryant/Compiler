package compiler.language.parser.rules;

import compiler.language.ast.ImportDeclaration;
import compiler.language.ast.QName;
import compiler.language.parser.Type;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ImportDeclarationRule extends Rule
{

  private static final Object[] NORMAL_PRODUCTION = new Object[] {Type.IMPORT_KEYWORD, Type.QNAME, Type.SEMICOLON};
  private static final Object[] ALL_PRODUCTION = new Object[] {Type.IMPORT_KEYWORD, Type.QNAME, Type.DOT, Type.STAR, Type.SEMICOLON};
  private static final Object[] STATIC_NORMAL_PRODUCTION = new Object[] {Type.IMPORT_KEYWORD, Type.STATIC_KEYWORD, Type.QNAME, Type.SEMICOLON};
  private static final Object[] STATIC_ALL_PRODUCTION = new Object[] {Type.IMPORT_KEYWORD, Type.STATIC_KEYWORD, Type.QNAME, Type.DOT, Type.STAR, Type.SEMICOLON};

  public ImportDeclarationRule()
  {
    super(Type.IMPORT_DECLARATION, NORMAL_PRODUCTION, ALL_PRODUCTION, STATIC_NORMAL_PRODUCTION, STATIC_ALL_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == NORMAL_PRODUCTION)
    {
      return new ImportDeclaration((QName) args[1], false, false);
    }
    if (types == ALL_PRODUCTION)
    {
      return new ImportDeclaration((QName) args[1], true, false);
    }
    if (types == STATIC_NORMAL_PRODUCTION)
    {
      return new ImportDeclaration((QName) args[1], false, true);
    }
    if (types == STATIC_ALL_PRODUCTION)
    {
      return new ImportDeclaration((QName) args[1], true, true);
    }
    throw new IllegalArgumentException("Invalid type list passed to match()");
  }

}
