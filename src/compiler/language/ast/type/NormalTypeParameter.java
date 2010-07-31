package compiler.language.ast.type;


/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class NormalTypeParameter extends TypeParameter
{

  private Type type;

  /**
   * Creates a new normal type parameter with the specified type.
   * @param type - the type assigned to this type parameter
   */
  public NormalTypeParameter(Type type)
  {
    this.type = type;
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return type;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return type.toString();
  }

}
