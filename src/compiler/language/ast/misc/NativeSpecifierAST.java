package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.StringLiteralAST;


/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class NativeSpecifierAST extends ModifierAST
{
  // TODO: maybe make it possible to omit the nativeName? in this case a generated name will be provided by the compiler

  private StringLiteralAST nativeName;

  /**
   * Creates a new native specification with the specified native name.
   * @param nativeName - the native name associated with this NativeSpecifierAST
   * @param parseInfo - the parsing information
   */
  public NativeSpecifierAST(StringLiteralAST nativeName, ParseInfo parseInfo)
  {
    super(ModifierTypeAST.NATIVE_SPECIFIER, parseInfo);
    this.nativeName = nativeName;
  }

  /**
   * @return the nativeName
   */
  public StringLiteralAST getNativeName()
  {
    return nativeName;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.misc.ModifierAST#toString()
   */
  @Override
  public String toString()
  {
    return "native(" + nativeName + ")";
  }

}
