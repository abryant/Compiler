package compiler.language.ast;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class NativeSpecification extends Modifier
{

  private String nativeName;

  /**
   * Creates a new native specification with the specified native name.
   * @param nativeName - the native name associated with this NativeSpecification
   */
  public NativeSpecification(String nativeName)
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
