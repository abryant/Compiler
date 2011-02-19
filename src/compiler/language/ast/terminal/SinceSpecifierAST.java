package compiler.language.ast.terminal;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.ModifierTypeAST;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class SinceSpecifierAST extends ModifierAST
{

  private VersionNumberAST version;

  /**
   * Creates a new SinceSpecifierAST with the specified parts of the version number.
   * @param version - the version number associated with this since specifier
   * @param parseInfo - the parsing information
   */
  public SinceSpecifierAST(VersionNumberAST version, ParseInfo parseInfo)
  {
    super(ModifierTypeAST.SINCE_SPECIFIER, parseInfo);
    this.version = version;
  }

  /**
   * @return the version
   */
  public VersionNumberAST getVersion()
  {
    return version;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.misc.ModifierAST#toString()
   */
  @Override
  public String toString()
  {
    return "since(" + version + ")";
  }

}
