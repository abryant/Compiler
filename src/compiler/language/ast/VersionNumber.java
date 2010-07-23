package compiler.language.ast;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class VersionNumber
{
  private IntegerLiteral[] versionParts;

  /**
   * Creates a new version number with the specified version parts
   * @param versionParts - the integer literals representing the parts of this version number
   */
  public VersionNumber(IntegerLiteral[] versionParts)
  {
    this.versionParts = versionParts;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < versionParts.length; i++)
    {
      buffer.append(versionParts[i]);
      if (i != versionParts.length - 1)
      {
        buffer.append(".");
      }
    }
    return buffer.toString();
  }

  /**
   * @return the individual parts of the version number
   */
  public IntegerLiteral[] getVersionParts()
  {
    return versionParts;
  }
}
