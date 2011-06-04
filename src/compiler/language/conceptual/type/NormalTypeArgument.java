package compiler.language.conceptual.type;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class NormalTypeArgument extends TypeArgument
{

  private Type type;

  /**
   * Creates a new NormalTypeArgument to hold the specified type
   * @param type - the type to store
   */
  public NormalTypeArgument(Type type)
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

}
