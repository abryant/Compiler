package compiler.language.conceptual.type;

/*
 * Created on 7 Mar 2011
 */

/**
 * @author Anthony Bryant
 */
public class ClosureType extends Type
{

  private TypeParameter[] typeParameters;
  private Type[] argumentTypes;
  private Type[] resultTypes;
  private PointerType[] exceptionTypes;

  /**
   * Creates a new ClosureType with the specified type parameters, argument and result types, and exception types
   * @param typeParameters - the type parameters
   * @param argumentTypes - the argument types
   * @param resultTypes - the result types
   * @param exceptionTypes - the exception types
   */
  public ClosureType(TypeParameter[] typeParameters, Type[] argumentTypes, Type[] resultTypes, PointerType[] exceptionTypes)
  {
    this.typeParameters = typeParameters;
    this.argumentTypes = argumentTypes;
    this.resultTypes = resultTypes;
    this.exceptionTypes = exceptionTypes;
  }

  /**
   * @return the typeParameters
   */
  public TypeParameter[] getTypeParameters()
  {
    return typeParameters;
  }

  /**
   * @return the argumentTypes
   */
  public Type[] getArgumentTypes()
  {
    return argumentTypes;
  }

  /**
   * @return the resultTypes
   */
  public Type[] getResultTypes()
  {
    return resultTypes;
  }

  /**
   * @return the exceptionTypes
   */
  public PointerType[] getExceptionTypes()
  {
    return exceptionTypes;
  }

}
