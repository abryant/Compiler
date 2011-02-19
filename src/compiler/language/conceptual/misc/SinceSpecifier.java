package compiler.language.conceptual.misc;


/*
 * Created on 16 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class SinceSpecifier implements Comparable<SinceSpecifier>
{
  private int[] sinceVersion;

  /**
   * Creates a new SinceSpecifier with the specified version number.
   * @param sinceVersion - the version number associated with this since specifier
   */
  public SinceSpecifier(int[] sinceVersion)
  {
    this.sinceVersion = sinceVersion;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(SinceSpecifier other)
  {
    int[] otherVersion = other.sinceVersion;
    for (int i = 0; i < sinceVersion.length && i < otherVersion.length; i++)
    {
      if (sinceVersion[i] != otherVersion[i])
      {
        return sinceVersion[i] - otherVersion[i];
      }
    }
    return sinceVersion.length - otherVersion.length;
  }
}
