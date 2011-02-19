package compiler.language.ast.member;


/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public enum AccessSpecifierTypeAST
{
  PUBLIC("public"),
  PACKAGE("package"),
  PACKAGE_PROTECTED("package protected"),
  PROTECTED("protected"),
  PRIVATE("private");

  private final String stringRepresentation;

  private AccessSpecifierTypeAST(String stringRepresentation)
  {
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString()
  {
    return stringRepresentation;
  }
}
