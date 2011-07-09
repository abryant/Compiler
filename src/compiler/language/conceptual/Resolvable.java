package compiler.language.conceptual;

/*
 * Created on 6 Mar 2011
 */

/**
 * A part of the conceptual hierarchy which can resolve names.
 *
 * Contains an abstract method to resolve a method directly inside itself, and a non-abstract method for recursively resolving a name starting at this scope.
 * @author Anthony Bryant
 */
public abstract class Resolvable
{

  /**
   * @return the scope type of this Resolvable
   */
  public abstract ScopeType getType();

  /**
   * Resolves the specified name in this Resolvable object.
   * @param name - the name to resolve
   * @return the Resolvable object found, or null if the name could not be resolved
   * @throws NameConflictException - if a name conflict is detected while trying to resolve this name
   * @throws UnresolvableException - if it is unknown whether the name can be resolved
   * @throws ConceptualException - if a conceptual error is detected while resolving the name
   */
  public abstract Resolvable resolve(String name) throws NameConflictException, UnresolvableException;

  /**
   * @return the parent resolvable object that should be consulted about resolving names that this cannot resolve
   */
  public abstract Resolvable getParent();

  /**
   * Resolves the specified QName from this Resolvable object.
   * @param name - the QName to resolve
   * @param recurseUpwards - true to recurse back to the parent conceptual node if there are no results in this one, false to just return null in this scenario
   * @return the result of resolving the QName, as a Resolvable, or null if the name could not be resolved
   * @throws NameConflictException - if a name conflict is detected while trying to resolve the name
   * @throws UnresolvableException - if it is unknown whether the name can be resolved
   * @throws ConceptualException - if a conceptual error is detected while resolving the name
   */
  public final Resolvable resolve(QName name, boolean recurseUpwards) throws NameConflictException, UnresolvableException
  {
    return resolve(name, this, recurseUpwards);
  }

  /**
   * Resolves the specified QName from this Resolvable object.
   * @param name - the QName to resolve
   * @param startScope - the scope that the name is being resolved from, can be used for checking access specifiers
   * @param recurseUpwards - true to recurse back to the parent conceptual node if there are no results in this one, false to just return null in this scenario
   * @return the result of resolving the QName, as a Resolvable, or null if the name could not be resolved
   * @throws NameConflictException - if a name conflict is detected while trying to resolve the name
   * @throws UnresolvableException - if it is unknown whether the name can be resolved
   * @throws ConceptualException - if a conceptual error is detected while resolving the name
   */
  private final Resolvable resolve(QName name, Resolvable startScope, boolean recurseUpwards) throws NameConflictException, UnresolvableException
  {
    Resolvable resolvable = this;
    boolean resolvedOne = false;
    for (String subName : name.getNames())
    {
      resolvable = resolvable.resolve(subName);
      if (resolvable == null)
      {
        if (resolvedOne)
        {
          return null;
        }
        break;
      }
      resolvedOne = true;
    }
    if (resolvable != null)
    {
      return resolvable;
    }

    if (recurseUpwards)
    {
      Resolvable parent = getParent();
      if (parent != null)
      {
        return parent.resolve(name, startScope, true);
      }
    }
    return null;
  }

}
