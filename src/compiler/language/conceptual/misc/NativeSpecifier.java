package compiler.language.conceptual.misc;


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
   * @return the nativeName
   */
  public String getNativeName()
  {
    return nativeName;
  }

}
