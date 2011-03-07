package compiler.language.conceptual;

/*
 * Created on 6 Mar 2011
 */

/**
 * A part of the conceptual hierarchy which can resolve names.
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
   */
  public abstract Resolvable resolve(String name) throws NameConflictException;

  /**
   * @return the parent resolvable object that should be consulted about resolving names that this cannot resolve
   */
  protected abstract Resolvable getParent();

  /**
   * Resolves the specified QName from this Resolvable object.
   * @param name - the QName to resolve
   * @param recurseUpwards - true to recurse back to the parent conceptual node if there are no results in this one, false to just return null in this scenario
   * @return the result of resolving the QName, as a Resolvable, or null if the name could not be resolved
   * @throws NameConflictException - if a name conflict is detected while trying to resolve the name
   */
  public final Resolvable resolve(QName name, boolean recurseUpwards) throws NameConflictException
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
        return parent.resolve(name, true);
      }
    }
    return null;
  }

}
