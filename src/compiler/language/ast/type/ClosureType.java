package compiler.language.ast.type;


/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClosureType extends Type
{

  private Type[] parameterTypes;
  private Type[] resultTypes;
  private PointerType[] exceptionTypes;

  /**
   * Creates a new Closure type with the specified parameter types, result types, and exception types
   * @param parameterTypes - the types of the parameters to this closure
   * @param resultTypes - the types of the results of this closure
   * @param exceptionTypes - the types of the exceptions thrown by this closure
   */
  public ClosureType(Type[] parameterTypes, Type[] resultTypes, PointerType[] exceptionTypes)
  {
    this.parameterTypes = parameterTypes;
    this.resultTypes = resultTypes;
    this.exceptionTypes = exceptionTypes;
  }

  /**
   * @return the parameter types
   */
  public Type[] getParameterTypes()
  {
    return parameterTypes;
  }

  /**
   * @return the result types
   */
  public Type[] getResultTypes()
  {
    return resultTypes;
  }

  /**
   * @return the exception types
   */
  public PointerType[] getExceptionTypes()
  {
    return exceptionTypes;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("{");
    for (int i = 0; i < parameterTypes.length; i++)
    {
      buffer.append(parameterTypes[i]);
      if (i != parameterTypes.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(" -> ");
    for (int i = 0; i < resultTypes.length; i++)
    {
      buffer.append(resultTypes[i]);
      if (i != resultTypes.length - 1)
      {
        buffer.append(", ");
      }
    }
    if (exceptionTypes.length > 0)
    {
      buffer.append(" throws ");
      for (int i = 0; i < exceptionTypes.length; i++)
      {
        buffer.append(exceptionTypes[i]);
        if (i != exceptionTypes.length - 1)
        {
          buffer.append(", ");
        }
      }
    }
    buffer.append("}");
    return buffer.toString();
  }

}
