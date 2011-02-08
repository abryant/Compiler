package compiler.language.conceptual.misc;


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
   * {@inheritDoc}
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString()
  {
    // convert the name into a more human-readable format
    return name().toLowerCase().replace('_', ' ');
  }
}
