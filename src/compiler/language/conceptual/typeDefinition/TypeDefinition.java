package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.ScopedResult;

/*
 * Created on 19 Feb 2011
 */

/**
 * The abstract base class of all type definitions, including classes, interfaces and enums.
 * @author Anthony Bryant
 */
public abstract class TypeDefinition
{

  /**
   * Resolves the specified name in this level of the type definition.
   * @param name - the name to resolve
   * @return the result of resolving the specified name in this type definition, or null if the name does not resolve
   */
  protected abstract ScopedResult resolve(String name);

  /**
   * Resolves the specified QName in the part of the conceptual hierarchy that encloses this type definition.
   * @param qname - the QName to resolve
   * @return the result of resolving the specified QName, or null if the QName does not resolve
   * @throws NameConflictException - if a name conflict is detected while resolving the specified QName
   */
  protected abstract ScopedResult resolveEnclosing(QName qname) throws NameConflictException;

  /**
   * Resolves the specified QName inside this type definition.
   * @param name - the name to resolve
   * @param recurseUpwards - true to recurse back to this type definition's parent if no names match, false otherwise
   * @return the ScopedResult found, or null if the name does not resolve to anything
   * @throws NameConflictException - if a name conflict is detected while resolving the specified QName
   */
  public final ScopedResult resolve(QName name, boolean recurseUpwards) throws NameConflictException
  {
    String first = name.getFirstName();
    ScopedResult result = resolve(first);
    if (result != null)
    {
      if (name.getLength() == 1)
      {
        return result;
      }
      return result.resolve(name.getTrailingNames());
    }
    if (recurseUpwards)
    {
      return resolveEnclosing(name);
    }
    return null;
  }

}
