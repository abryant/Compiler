package compiler.language.conceptual.type;

/*
 * Created on 17 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerType extends Type
{
  private boolean immutable;
  private TypeInstance typeInstance;

  /**
   * Creates a new PointerType with the specified immutability and type instance
   * @param immutable - true if this pointer type should be immutable, false otherwise
   * @param typeInstance - the type instance that this pointer type represents
   */
  public PointerType(boolean immutable, TypeInstance typeInstance)
  {
    this.immutable = immutable;
    this.typeInstance = typeInstance;
  }

  /**
   * @return the immutable
   */
  public boolean isImmutable()
  {
    return immutable;
  }
  /**
   * @return the typeInstance
   */
  public TypeInstance getTypeInstance()
  {
    return typeInstance;
  }
}
