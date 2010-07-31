package compiler.language.ast.member;

import compiler.language.ast.misc.VersionNumber;

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
   */
  public SinceSpecifier(VersionNumber version)
  {
    super("since(" + version + ")");
    this.version = version;
  }

  /**
   * @return the version
   */
  public VersionNumber getVersion()
  {
    return version;
  }

}
