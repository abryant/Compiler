package compiler.language.conceptual.misc;

import compiler.language.ast.misc.NativeSpecifierAST;

/*
 * Created on 17 Jan 2011
 */

/**
 * @author Anthony Bryant
 */
public class NativeSpecifier
{
  private String nativeName;

  /**
   * Creates a new NativeSpecifier with the specified native name
   * @param nativeName - the native name of this native specifier
   */
  public NativeSpecifier(String nativeName)
  {
    this.nativeName = nativeName;
  }

  /**
   * Creates a new NativeSpecifier from the specified NativeSpecifierAST.
   * @param nativeSpecifierAST - the NativeSpecifierAST to base the new NativeSpecifier on
   * @return the NativeSpecifier created
   */
  public static NativeSpecifier fromAST(NativeSpecifierAST nativeSpecifierAST)
  {
    String nativeName = nativeSpecifierAST.getNativeName().getLiteralValue();
    return new NativeSpecifier(nativeName);
  }

  /**
   * @return the nativeName
   */
  public String getNativeName()
  {
    return nativeName;
  }

}
