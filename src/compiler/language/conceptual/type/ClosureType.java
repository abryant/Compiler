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

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canAssign(Type type)
  {
    if (!(type instanceof ClosureType))
    {
      return false;
    }
    ClosureType other = (ClosureType) type;

    // check the type parameters
    if (typeParameters != null)
    {
      if (other.typeParameters == null || other.typeParameters.length != typeParameters.length)
      {
        return false;
      }
      for (int i = 0; i < typeParameters.length; i++)
      {
        if (!typeParameters[i].canAssign(other.typeParameters[i]))
        {
          return false;
        }
      }
    }

    // check the argument types
    if (argumentTypes.length != other.argumentTypes.length)
    {
      return false;
    }
    for (int i = 0; i < argumentTypes.length; i++)
    {
      if (!argumentTypes[i].canAssign(other.argumentTypes[i]))
      {
        return false;
      }
    }

    // check the result types
    if (resultTypes.length != other.resultTypes.length)
    {
      return false;
    }
    for (int i = 0; i < resultTypes.length; i++)
    {
      if (!resultTypes[i].canAssign(other.resultTypes[i]))
      {
        return false;
      }
    }

    // check the exception types
    // each exception in the other type must be assignable to one of the exceptions in this type
    for (int i = 0; i < other.exceptionTypes.length; i++)
    {
      boolean found = false;
      for (int j = 0; j < exceptionTypes.length; j++)
      {
        if (exceptionTypes[j].canAssign(other.exceptionTypes[i]))
        {
          found = true;
          break;
        }
      }
      if (!found)
      {
        return false;
      }
    }

    // if we didn't find any problems, it must be assignable
    return true;
  }

}
