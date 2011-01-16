package compiler.language.translator.conceptual;

import java.util.HashMap;
import java.util.Map;

import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.ast.topLevel.PackageDeclarationAST;
import compiler.language.ast.topLevel.TypeDefinitionAST;
import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.ast.typeDefinition.EnumDefinitionAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.ConceptualException;

/*
 * Created on 30 Dec 2010
 */

/**
 * This class contains methods for scanning through the names in compilation units.
 * @author Anthony Bryant
 */
public class NameScanner
{

  private Scope rootScope;
  private Map<CompilationUnitAST, Scope> fileScopes;

  /**
   * Creates a new NameScanner to add to the specified root scope
   * @param rootScope - the root scope to add to
   */
  public NameScanner(Scope rootScope)
  {
    this.rootScope = rootScope;
    fileScopes = new HashMap<CompilationUnitAST, Scope>();
  }

  /**
   * Scans the specified compilation unit for names.
   * @param compilationUnit - the compilation unit to scan
   * @throws ScopeException - if a scope conflict occurs TODO: take this out when conflict handling is better
   * @throws ConceptualException - if there is a problem creating a Conceptual node
   */
  public void scanCompilationUnit(CompilationUnitAST compilationUnit) throws ScopeException, ConceptualException
  {
    PackageDeclarationAST packageDeclaration = compilationUnit.getPackageDeclaration();
    Scope enclosingScope = null;
    if (packageDeclaration == null)
    {
      enclosingScope = rootScope;
    }
    else
    {
      QNameAST packageName = packageDeclaration.getPackageName();
      enclosingScope = ScopeFactory.getPackageScope(rootScope, packageName.getNameStrings());
    }

    Scope fileScope = ScopeFactory.createFileScope(enclosingScope);
    fileScopes.put(compilationUnit, fileScope);

    for (TypeDefinitionAST typeDefinition : compilationUnit.getTypes())
    {
      Scope typeScope = null;
      if (typeDefinition instanceof ClassDefinitionAST)
      {
        ClassDefinitionAST classDefinition = (ClassDefinitionAST) typeDefinition;
        typeScope = ScopeFactory.createClassDefinitionScope(classDefinition, fileScope);
        scanClassDefinition(classDefinition, typeScope);
      }
      else if (typeDefinition instanceof InterfaceDefinitionAST)
      {
        InterfaceDefinitionAST interfaceDefinition = (InterfaceDefinitionAST) typeDefinition;
        typeScope = ScopeFactory.createInterfaceDefinitionScope(interfaceDefinition, fileScope);
        scanInterfaceDefinition(interfaceDefinition, typeScope);
      }
      else if (typeDefinition instanceof EnumDefinitionAST)
      {
        EnumDefinitionAST enumDefinition = (EnumDefinitionAST) typeDefinition;
        typeScope = ScopeFactory.createEnumDefinitionScope(enumDefinition, fileScope);
        scanEnumDefinition(enumDefinition, typeScope);
      }
      else
      {
        throw new UnsupportedOperationException("Cannot translate a type definition that does not represent a class, interface or enum.");
      }

      // TODO: don't throw the exception if there is a conflict, just print it out and fail when we've detected all of the errors (probably not with an exception in fact)
      enclosingScope.addChild(typeDefinition.getName().getName(), typeScope);
    }
  }

  /**
   * Scans the names from the specified class definition into the specified enclosing scope.
   * @param classDefinition - the class definition to scan
   * @param enclosingScope - the scope to scan the names into
   */
  private void scanClassDefinition(ClassDefinitionAST classDefinition, Scope enclosingScope)
  {

  }

  /**
   * Scans the names from the specified interface definition into the specified enclosing scope.
   * @param interfaceDefinition - the interface definition to scan
   * @param enclosingScope - the scope to scan the names into
   */
  private void scanInterfaceDefinition(InterfaceDefinitionAST interfaceDefinition, Scope enclosingScope)
  {

  }

  /**
   * Scans the names from the specified enum definition into the specified enclosing scope.
   * @param enumDefinition - the enum definition to scan
   * @param enclosingScope - the scope to scan the names into
   */
  private void scanEnumDefinition(EnumDefinitionAST enumDefinition, Scope enclosingScope)
  {

  }

}
