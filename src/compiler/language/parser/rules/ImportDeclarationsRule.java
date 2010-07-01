package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.IMPORT_DECLARATION;
import static compiler.language.parser.ParseType.IMPORT_DECLARATIONS;

import compiler.language.ast.ImportDeclaration;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ImportDeclarationsRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {IMPORT_DECLARATIONS, IMPORT_DECLARATION};

  public ImportDeclarationsRule()
  {
    super(IMPORT_DECLARATIONS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new ImportDeclaration[0];
    }
    if (types == PRODUCTION)
    {
      ImportDeclaration[] oldDeclarations = (ImportDeclaration[]) args[0];
      ImportDeclaration[] newDeclarations = new ImportDeclaration[oldDeclarations.length + 1];
      System.arraycopy(oldDeclarations, 0, newDeclarations, 0, oldDeclarations.length);
      newDeclarations[oldDeclarations.length] = (ImportDeclaration) args[1];
      return newDeclarations;
    }
    throw badTypeList();
  }

}
