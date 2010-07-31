package compiler.language.ast.type;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public enum IntegerTypeLength
{
  BYTE("byte"),
  SHORT("short"),
  INT("int"),
  LONG("long");

  private final String stringRepresentation;

  private IntegerTypeLength(String stringRepresentation)
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
