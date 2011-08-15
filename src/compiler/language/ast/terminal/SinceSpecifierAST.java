package compiler.language.ast.terminal;

import compiler.language.LexicalPhrase;
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
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public SinceSpecifierAST(VersionNumberAST version, LexicalPhrase lexicalPhrase)
  {
    super(ModifierTypeAST.SINCE_SPECIFIER, lexicalPhrase);
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
