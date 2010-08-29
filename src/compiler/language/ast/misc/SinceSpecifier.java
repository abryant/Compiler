package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class SinceSpecifier extends Modifier
{

  private VersionNumber version;

  /**
   * Creates a new SinceSpecifier with the specified parts of the version number.
   * @param version - the version number associated with this since specifier
   * @param parseInfo - the parsing information
   */
  public SinceSpecifier(VersionNumber version, ParseInfo parseInfo)
  {
    super(ModifierType.SINCE_SPECIFIER, parseInfo);
    this.version = version;
  }

  /**
   * @return the version
   */
  public VersionNumber getVersion()
  {
    return version;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.misc.Modifier#toString()
   */
  @Override
  public String toString()
  {
    return "since(" + version + ")";
  }

}
