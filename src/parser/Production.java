package parser;

import java.io.Serializable;

/*
 * Created on 19 Sep 2010
 */

/**
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public final class Production<T extends Enum<T>> implements Serializable
{
  private static final long serialVersionUID = 1L;

  private T[] types;

  /**
   * Creates a new Production with the specified types
   * @param types - the types that this production requires
   */
  public Production(T... types)
  {
    this.types = types;
  }

  /**
   * @return the types
   */
  public T[] getTypes()
  {
    return types;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof Production))
    {
      return false;
    }
    // this is not nice, but is necessary to check whether the productions
    // are equal without knowing their generic type parameters
    @SuppressWarnings("unchecked")
    Production<T> production = (Production<T>) o;
    if (types.length != production.types.length)
    {
      return false;
    }
    for (int i = 0; i < types.length; i++)
    {
      if (types[i] != production.types[i])
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    int hash = 0;
    for (int i = 0; i < types.length; i++)
    {
      hash = 31 * hash + types[i].hashCode();
    }
    return hash;
  }
}
