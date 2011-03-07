package compiler.language.conceptual.type;

/*
 * Created on 7 Mar 2011
 */

/**
 * A TypeInstance which just points to a TypeArgument.
 * @author Anthony Bryant
 */
public final class TypeArgumentInstance extends TypeInstance
{

  private TypeArgument typeArgument;

  /**
   * Creates a new TypeArgumentInstance with the specified TypeArgument
   * @param typeArgument - the TypeArgument that this TypeArgumentInstance points to
   */
  public TypeArgumentInstance(TypeArgument typeArgument)
  {
    this.typeArgument = typeArgument;
  }

  /**
   * @return the typeArgument
   */
  public TypeArgument getTypeArgument()
  {
    return typeArgument;
  }

}
