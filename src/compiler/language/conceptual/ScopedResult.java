package compiler.language.conceptual;

import compiler.language.conceptual.topLevel.ConceptualPackage;

/*
 * Created on 15 Feb 2011
 */

/**
 * @author Anthony Bryant
 */
public final class ScopedResult
{
  private ScopeType type;
  private Object value;

  /**
   * Creates a new ScopedResult with the specified type and value
   * @param type - the type of the new ScopedResult
   * @param value - the value of the new ScopedResult
   */
  public ScopedResult(ScopeType type, Object value)
  {
    this.type = type;
    this.value = value;
  }

  /**
   * @return the type
   */
  public ScopeType getType()
  {
    return type;
  }

  /**
   * @return the value
   */
  public Object getValue()
  {
    return value;
  }

  /**
   * Attempts to resolve the specified QName in the value of this ScopedResult
   * @param name - the name to resolve
   * @return the result of resolving the name, or null if there was no result
   * @throws NameConflictException - if a name conflict was detected while resolving the name
   */
  public ScopedResult resolve(QName name) throws NameConflictException
  {
    switch (type)
    {
    case PACKAGE:
      return ((ConceptualPackage) value).resolve(name, false);
    // TODO: fill in other types
    default:
      throw new IllegalStateException("Cannot resolve something with the type: " + type);
    }
  }

}
