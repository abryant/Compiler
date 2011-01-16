package compiler.language.conceptual;

import java.math.BigInteger;

import compiler.language.ast.terminal.IntegerLiteralAST;
import compiler.language.ast.terminal.SinceSpecifierAST;
import compiler.language.ast.terminal.VersionNumberAST;

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
   * Creates a new conceptual SinceSpecifier from the specified SinceSpecifierAST.
   * @param sinceSpecifierAST - the SinceSpecifierAST to base the new SinceSpecifier on
   * @return the SinceSpecifier created
   * @throws ConceptualException - if the version number contains an illegal value (less than 0 or more than Integer.MAX_VALUE)
   */
  public static SinceSpecifier fromAST(SinceSpecifierAST sinceSpecifierAST) throws ConceptualException
  {
    if (sinceSpecifierAST == null)
    {
      return null;
    }
    VersionNumberAST version = sinceSpecifierAST.getVersion();
    IntegerLiteralAST[] literals = version.getVersionParts();
    int[] values = new int[literals.length];
    for (int i = 0; i < literals.length; i++)
    {
      BigInteger value = literals[i].getValue();
      int intValue = value.intValue();
      if (intValue < 0 || !BigInteger.valueOf(intValue).equals(value))
      {
        throw new ConceptualException("Illegal value in a Since Specifier: " + value, literals[i].getParseInfo());
      }
      values[i] = intValue;
    }
    return new SinceSpecifier(values);
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
