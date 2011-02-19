package compiler.language.conceptual.type;

/*
 * Created on 21 Oct 2010
 */

/**
 * A type parameter that relays a type from a type argument.
 *
 * @author Anthony Bryant
 */
public class RelayedTypeParameter extends TypeParameter
{

  private TypeArgument typeArgument;

  /**
   * Creates a new RelayedTypeParameter to relay the specified TypeArgument as a TypeParameter
   * @param typeArgument - the TypeArgument to relay
   */
  public RelayedTypeParameter(TypeArgument typeArgument)
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
