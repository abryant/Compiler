package compiler.language.translator.conceptual;

import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.ast.typeDefinition.EnumDefinitionAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.ConceptualException;
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
   * @throws ConceptualException - if there is a problem converting the ClassDefinitionAST to a ConceptualClass
   */
  public static Scope createClassDefinitionScope(ClassDefinitionAST classDefinition, Scope parentScope) throws ConceptualException
  {
    return new Scope(parentScope, ScopeType.CLASS, ConceptualClass.fromAST(classDefinition));
  }

  /**
   * Creates a new scope for an interface definition.
   * @param interfaceDefinition - the interface definition value to store in the new scope
   * @param parentScope - the parent scope that should be searched if a name cannot be resolved in the new scope
   * @return the scope created
   * @throws ConceptualException - if there is a problem converting the InterfaceDefinitionAST to a ConceptualInterface
   */
  public static Scope createInterfaceDefinitionScope(InterfaceDefinitionAST interfaceDefinition, Scope parentScope) throws ConceptualException
  {
    return new Scope(parentScope, ScopeType.INTERFACE, ConceptualInterface.fromAST(interfaceDefinition));
  }

  /**
   * Creates a new scope for an enum definition.
   * @param enumDefinition - the enum definition value to store in the new scope
   * @param parentScope - the parent scope that should be searched if a name cannot be resolved in the new scope
   * @return the scope created
   * @throws ConceptualException - if there is a problem converting the EnumDefinitionAST to a ConceptualEnum
   */
  public static Scope createEnumDefinitionScope(EnumDefinitionAST enumDefinition, Scope parentScope) throws ConceptualException
  {
    return new Scope(parentScope, ScopeType.ENUM, ConceptualEnum.fromAST(enumDefinition));
  }
}
