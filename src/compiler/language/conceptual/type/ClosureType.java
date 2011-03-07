package compiler.language.conceptual.type;

/*
 * Created on 7 Mar 2011
 */

/**
 * @author Anthony Bryant
 */
public class ClosureType extends Type
{

  private TypeArgument[] typeArguments;
  private Type[] parameterTypes;
  private Type[] resultTypes;
  private PointerType[] exceptionTypes;

  /**
   * Creates a new ClosureType with the specified type arguments, parameter and result types, and exception types
   * @param typeArguments - the type arguments
   * @param parameterTypes - the parameter types
   * @param resultTypes - the result types
   * @param exceptionTypes - the exception types
   */
  public ClosureType(TypeArgument[] typeArguments, Type[] parameterTypes, Type[] resultTypes, PointerType[] exceptionTypes)
  {
    this.typeArguments = typeArguments;
    this.parameterTypes = parameterTypes;
    this.resultTypes = resultTypes;
    this.exceptionTypes = exceptionTypes;
  }

  /**
   * @return the typeArguments
   */
  public TypeArgument[] getTypeArguments()
  {
    return typeArguments;
  }

  /**
   * @return the parameterTypes
   */
  public Type[] getParameterTypes()
  {
    return parameterTypes;
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
