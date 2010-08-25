package compiler.language.ast.member;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public enum AccessSpecifier
{
  PUBLIC("public"),
  PACKAGE("package"),
  PACKAGE_PROTECTED("package protected"),
  PROTECTED("protected"),
  PRIVATE("private");

  private final String stringRepresentation;

  private AccessSpecifier(String stringRepresentation)
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
