package compiler.language.translator.conceptual;

import compiler.language.conceptual.Scope;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.ScopedMemberSet;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 23 Dec 2010
 */

/**
 * @author Anthony Bryant
 */
public class ScopeFactory
{
  /**
   * Creates a new, empty Root scope.
   * @return the new root scope
   */
  public static Scope createRootScope()
  {
    return new Scope(null, ScopeType.PACKAGE, null);
  }

  /**
   * Gets the package scope with the specified name. If the scope does not already exist, it is created and returned.
   * @param rootScope - the root scope, which should have no parent
   * @param names - the list of package names to resolve in order
   * @return the package scope requested
   */
  public static Scope getPackageScope(Scope rootScope, String[] names)
  {
    Scope current = rootScope;
    for (int i = 0; i < names.length; i++)
    {
      Scope next = current.getChild(names[i]);
      if (next != null)
      {
        current = next;
        continue;
      }
      next = new Scope(current, ScopeType.PACKAGE, null);
      try
      {
        current.addChild(names[i], next);
      }
      catch (ScopeException e)
      {
        // impossible, unless we have a race condition
        e.printStackTrace();
      }
    }
    return current;
  }

  /**
   * Creates a new file scope with the specified package scope as its parent
   * @param packageScope - the parent scope for the new file scope
   * @return the new file scope
   */
  public static Scope createFileScope(Scope packageScope)
  {
    return new Scope(packageScope, ScopeType.FILE, null);
  }

  /**
   * Creates a new scope for a class definition.
   * @param classDefinition - the class definition value to store in the new scope
   * @param parentScope - the parent scope that should be searched if a name cannot be resolved in the new scope
   * @return the scope created
   */
  public static Scope createClassDefinitionScope(ConceptualClass classDefinition, Scope parentScope)
  {
    return new Scope(parentScope, ScopeType.CLASS, classDefinition);
  }

  /**
   * Creates a new scope for an interface definition.
   * @param interfaceDefinition - the interface definition value to store in the new scope
   * @param parentScope - the parent scope that should be searched if a name cannot be resolved in the new scope
   * @return the scope created
   */
  public static Scope createInterfaceDefinitionScope(ConceptualInterface interfaceDefinition, Scope parentScope)
  {
    return new Scope(parentScope, ScopeType.INTERFACE, interfaceDefinition);
  }

  /**
   * Creates a new scope for an enum definition.
   * @param enumDefinition - the enum definition value to store in the new scope
   * @param parentScope - the parent scope that should be searched if a name cannot be resolved in the new scope
   * @return the scope created
   */
  public static Scope createEnumDefinitionScope(ConceptualEnum enumDefinition, Scope parentScope)
  {
    return new Scope(parentScope, ScopeType.ENUM, enumDefinition);
  }

  /**
   * Creates a new scope for a set of members in a type definition.
   * @param memberSet - the member set to store in the new scope
   * @param parentScope - the parent scope that should be searched if a name cannot be resolved in the new scope
   * @return the scope created
   */
  public static Scope createMemberSetScope(ScopedMemberSet memberSet, Scope parentScope)
  {
    return new Scope(parentScope, ScopeType.MEMBER, memberSet);
  }
}
