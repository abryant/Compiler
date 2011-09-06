package compiler.language.conceptual.type;

/*
 * Created on 7 Mar 2011
 */

/**
 * The class which represents all primitive types, (e.g. float)
 * @author Anthony Bryant
 */
public class PrimitiveType extends Type
{
  // basically, this is an enum which extends Type
  // but since enums in Java cannot extend a class, it has to be implemented using constant objects
  public static final PrimitiveType BOOLEAN        = new PrimitiveType(1);
  public static final PrimitiveType CHARACTER      = new PrimitiveType(2);
  public static final PrimitiveType FLOAT          = new PrimitiveType(3);
  public static final PrimitiveType DOUBLE         = new PrimitiveType(4);
  public static final PrimitiveType UNSIGNED_BYTE  = new PrimitiveType(5);
  public static final PrimitiveType   SIGNED_BYTE  = new PrimitiveType(6);
  public static final PrimitiveType UNSIGNED_SHORT = new PrimitiveType(7);
  public static final PrimitiveType   SIGNED_SHORT = new PrimitiveType(8);
  public static final PrimitiveType UNSIGNED_INT   = new PrimitiveType(9);
  public static final PrimitiveType   SIGNED_INT   = new PrimitiveType(10);
  public static final PrimitiveType UNSIGNED_LONG  = new PrimitiveType(11);
  public static final PrimitiveType   SIGNED_LONG  = new PrimitiveType(12);
  public static final PrimitiveType VOID           = new PrimitiveType(13);

  // an ordinal for deciding the hash code, and checking whether two primitive types are assignment compatible
  private int ordinal;

  /**
   * Creates a new PrimitiveType. This constructor is private so that only a certain set of values is possible.
   */
  private PrimitiveType(int ordinal)
  {
    this.ordinal = ordinal;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canAssign(Type type)
  {
    return (type instanceof PrimitiveType) &&
           ordinal == ((PrimitiveType) type).ordinal;
  }

}
