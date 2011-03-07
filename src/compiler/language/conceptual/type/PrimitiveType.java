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
  public static final PrimitiveType BOOLEAN        = new PrimitiveType();
  public static final PrimitiveType CHARACTER      = new PrimitiveType();
  public static final PrimitiveType FLOAT          = new PrimitiveType();
  public static final PrimitiveType DOUBLE         = new PrimitiveType();
  public static final PrimitiveType UNSIGNED_BYTE  = new PrimitiveType();
  public static final PrimitiveType   SIGNED_BYTE  = new PrimitiveType();
  public static final PrimitiveType UNSIGNED_SHORT = new PrimitiveType();
  public static final PrimitiveType   SIGNED_SHORT = new PrimitiveType();
  public static final PrimitiveType UNSIGNED_INT   = new PrimitiveType();
  public static final PrimitiveType   SIGNED_INT   = new PrimitiveType();
  public static final PrimitiveType UNSIGNED_LONG  = new PrimitiveType();
  public static final PrimitiveType   SIGNED_LONG  = new PrimitiveType();
  public static final PrimitiveType VOID           = new PrimitiveType();

  /**
   * Creates a new PrimitiveType. This constructor is private so that only a certain set of values is possible
   */
  private PrimitiveType()
  {
    // do nothing
  }
}
