package compiler.language.conceptual;

import java.util.HashMap;
import java.util.Map;

import compiler.language.translator.conceptual.ScopeException;

/*
 * Created on 14 Nov 2010
 */

/**
 * Represents a single scope level.
 * @author Anthony Bryant
 */
public class Scope
{

  private ScopeType type;
  private Object value;

  private Scope parent;
  private HashMap<String, Scope> children;

  /**
   * Creates a new scope, with the specified parent, type and value
   * @param parent - the parent scope
   * @param type - the type of object that this scope stores
   * @param value - the object that this scope stores
   */
  public Scope(Scope parent, ScopeType type, Object value)
  {
    this.type = type;
    this.value = value;
    this.parent = parent;
    children = new HashMap<String, Scope>();
  }

  /**
   * @return the parent scope
   */
  public Scope getParent()
  {
    return parent;
  }

  /**
   * @return the type of this scope
   */
  public ScopeType getType()
  {
    return type;
  }

  /**
   * @return the object that this scope stores
   */
  public Object getValue()
  {
    return value;
  }

  /**
   * @param name - the name of the child to get
   * @return the child scope with the specified name, or null if none exists
   */
  public Scope getChild(String name)
  {
    return children.get(name);
  }

  /**
   * @return the current map from name to the children of this Scope
   */
  @SuppressWarnings("unchecked")
  public Map<String, Scope> getChildren()
  {
    return (Map<String, Scope>) children.clone();
  }

  /**
   * Copies all of the children of the specified scope to also be children of this scope.
   * @param scope - the scope to copy the children of
   */
  public void copyChildren(Scope scope)
  {
    children.putAll(scope.children);
  }

  /**
   * Adds a child to the specified scope, with the specified name.
   * @param name - the name to add the child under
   * @param scope - the child scope to add
   * @throws ScopeException - if a sub-scope with the specified name already exists
   */
  public void addChild(String name, Scope scope) throws ScopeException
  {
    if (children.containsKey(name))
    {
      throw new ScopeException("Name conflict: " + name);
    }
    children.put(name, scope);
  }

  /**
   * Looks up the specified name in this scope, and all of its parent scopes recursively if necessary.
   * @param name - the qualified name to look up
   * @return the Scope found, or null if none could be found
   */
  public Scope lookup(String[] name) // TODO: is this ever used?
  {
    Scope root = this;
    while (root != null)
    {
      Scope current = root;
      for (int i = 0; i < name.length; i++)
      {
        current = current.getChild(name[i]);
        if (current == null)
        {
          break;
        }
      }
      if (current != null)
      {
        return current;
      }
      root = root.getParent();
    }
    return null;
  }

}
