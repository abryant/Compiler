package compiler.language.conceptual;

import compiler.language.ast.member.AccessSpecifierAST;

/*
 * Created on 16 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public enum AccessSpecifier
{
  PUBLIC,
  PACKAGE,
  PROTECTED,
  PACKAGE_PROTECTED,
  PRIVATE;

  /**
   * Converts the specified AccessSpecifierAST into a conceptual AccessSpecifier
   * @param accessSpecifierAST - the AccessSpecifierAST to base the choice of AccessSpecifier on
   * @return the AccessSpecifier corresponding to the specified AccessSpecifierAST's type
   */
  public static AccessSpecifier fromAST(AccessSpecifierAST accessSpecifierAST)
  {
    if (accessSpecifierAST == null)
    {
      return null;
    }
    switch (accessSpecifierAST.getType())
    {
    case PACKAGE:
      return PACKAGE;
    case PACKAGE_PROTECTED:
      return PACKAGE_PROTECTED;
    case PRIVATE:
      return PRIVATE;
    case PROTECTED:
      return PROTECTED;
    case PUBLIC:
      return PUBLIC;
    default:
      return null;
    }
  }
}
