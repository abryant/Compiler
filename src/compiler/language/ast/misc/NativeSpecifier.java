package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.StringLiteral;


/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class NativeSpecifier extends Modifier
{

  private StringLiteral nativeName;

  /**
   * Creates a new native specification with the specified native name.
   * @param nativeName - the native name associated with this NativeSpecifier
   * @param parseInfo - the parsing information
   */
  public NativeSpecifier(StringLiteral nativeName, ParseInfo parseInfo)
  {
    super(ModifierType.NATIVE_SPECIFIER, parseInfo);
    this.nativeName = nativeName;
  }

  /**
   * @return the nativeName
   */
  public StringLiteral getNativeName()
  {
    return nativeName;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.misc.Modifier#toString()
   */
  @Override
  public String toString()
  {
    return "native(\"" + nativeName + "\")";
  }

}
