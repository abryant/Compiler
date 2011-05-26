package compiler.language.conceptual.type;

/*
 * Created on 17 Oct 2010
 */

/**
 * An abstract base class that all types of pointer inherit from.
 * Subclasses such as ClassPointerType add more information about the type that is being referenced.
 * @author Anthony Bryant
 */
public abstract class PointerType extends Type
{
  private boolean immutable;

  /**
   * Creates a new PointerType with the specified immutability
   * @param immutable - true if this pointer type should be immutable, false otherwise
   */
  public PointerType(boolean immutable)
  {
    this.immutable = immutable;
  }

  /**
   * @return the immutable
   */
  public boolean isImmutable()
  {
    return immutable;
  }
}
