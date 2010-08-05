package compiler.language.ast.member;

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
   */
  public NativeSpecifier(StringLiteral nativeName)
  {
    super("native(\"" + nativeName + "\")");
    this.nativeName = nativeName;
  }

  /**
   * @return the nativeName
   */
  public StringLiteral getNativeName()
  {
    return nativeName;
  }

}
