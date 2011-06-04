package compiler.language.conceptual.type;

/*
 * Created on 7 Mar 2011
 */

/**
 * A TypeInstance which just points to a TypeParameter.
 * @author Anthony Bryant
 */
public final class TypeParameterPointerType extends PointerType
{

  private TypeParameter typeParameter;

  /**
   * Creates a new TypeParameterPointerType with the specified TypeParameter and immutability
   * @param typeParameter - the TypeParameter that this TypeParameterPointerType points to
   * @param immutable - true if this PointerType should be immutable, false otherwise
   */
  public TypeParameterPointerType(TypeParameter typeParameter, boolean immutable)
  {
    super(immutable);
    this.typeParameter = typeParameter;
  }

  /**
   * @return the typeParameter
   */
  public TypeParameter getTypeParameter()
  {
    return typeParameter;
  }

}
