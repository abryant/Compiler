package compiler.language.conceptual.type;

/*
 * Created on 7 Mar 2011
 */

/**
 * A TypeInstance which just points to a TypeArgument.
 * @author Anthony Bryant
 */
public final class TypeArgumentPointerType extends PointerType
{

  private TypeArgument typeArgument;

  /**
   * Creates a new TypeArgumentPointerType with the specified TypeArgument and immutability
   * @param typeArgument - the TypeArgument that this TypeArgumentInstance points to
   * @param immutable - true if this PointerType should be immutable, false otherwise
   */
  public TypeArgumentPointerType(TypeArgument typeArgument, boolean immutable)
  {
    super(immutable);
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
