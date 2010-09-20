package compiler.parser;

import java.io.Serializable;

/*
 * Created on 19 Sep 2010
 */

/**
 * @author Anthony Bryant
 */
public final class Production implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Object[] types;

  /**
   * Creates a new Production with the specified types
   * @param types - the types that this production requires
   */
  public Production(Object... types)
  {
    this.types = types;
  }

  /**
   * @return the types
   */
  public Object[] getTypes()
  {
    return types;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof Production))
    {
      return false;
    }
    Production production = (Production) o;
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
}
