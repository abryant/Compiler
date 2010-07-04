package compiler.language.ast;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerType extends Type
{

  private QName typeName;
  private boolean immutable;

  /**
   * Creates a new PointerType with the specified name and immutability
   * @param typeName - the name of the type
   * @param immutable - true if this type should be immutable, false otherwise
   */
  public PointerType(QName typeName, boolean immutable)
  {
    this.typeName = typeName;
    this.immutable = immutable;
  }

  /**
   * @return the type name
   */
  public QName getTypeName()
  {
    return typeName;
  }

  /**
   * @return true if this type is immutable, false otherwise
   */
  public boolean isImmutable()
  {
    return immutable;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return (immutable ? "#" : "") + typeName;
  }
}