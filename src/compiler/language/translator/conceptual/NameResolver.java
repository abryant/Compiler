package compiler.language.translator.conceptual;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import compiler.language.LexicalPhrase;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.conceptual.topLevel.ConceptualPackage;
import compiler.language.conceptual.type.OuterClassPointerType;

/*
 * Created on 20 Feb 2011
 */

/**
 * @author Anthony Bryant
 */
public final class NameResolver
{

  private AccessSpecifierChecker accessSpecifierChecker;

  private TypeResolver typeResolver;

  /**
   * Creates a new NameResolver with the specified mapping from Conceptual node to AST node.
   * @param conceptualASTNodes - the mapping which stores the AST node of each conceptual node which has been converted
   */
  public NameResolver(Map<Object, Object> conceptualASTNodes)
  {
    typeResolver = new TypeResolver(conceptualASTNodes);
  }

  /**
   * Initialises this NameResolver.
   * This involves building the universal base class, which becomes the base class of any classes which do not specify a base class
   * @param rootPackage - the root package to look up names in
   */
  public void initialise(ConceptualPackage rootPackage)
  {
    OuterClassPointerType universalBaseClass = typeResolver.buildUniversalBaseClass(rootPackage);
    accessSpecifierChecker = new AccessSpecifierChecker(universalBaseClass.getClassType());
    typeResolver.setAccessSpecifierChecker(accessSpecifierChecker);
  }

  /**
   * @return the AccessSpecifierChecker that should be used to check that accessing certain conceptual objects is valid
   */
  public AccessSpecifierChecker getAccessSpecifierChecker()
  {
    return accessSpecifierChecker;
  }

  /**
   * Adds all of the type definitions in the specified ConceptualFile to the resolver's queues
   * @param file - the file to add the type definitions of
   */
  public void addFile(ConceptualFile file)
  {
    typeResolver.addFile(file);
  }

  /**
   * Resolves all of the names in all of the types in the files which have been added via addFile(),
   * which finishes the conversion from the AST to the conceptual hierarchy.
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent types
   */
  public void resolveNames() throws NameConflictException, ConceptualException
  {
    // resolve base types
    boolean changed = true;
    Set<LexicalPhrase> unresolvedLexicalPhrases = new HashSet<LexicalPhrase>();

    while (changed)
    {
      // clear the unresolvedLexicalPhrases set, as it will be repopulated during the loop, and should only contain the unresolved LexicalPhrase since the last change
      unresolvedLexicalPhrases.clear();

      changed = resolveNames(unresolvedLexicalPhrases);
    }

    if (!typeResolver.finishedProcessing())
    {
      // something could not be resolved, so throw an exception with the correct LexicalPhrase
      throw new ConceptualException("Unresolvable type(s)", unresolvedLexicalPhrases.toArray(new LexicalPhrase[unresolvedLexicalPhrases.size()]));
    }
  }

  /**
   * Resolves as many QNames as possible from a single queue.
   * For example, if there are type definitions to resolve the parents of then this method resolves as many as possible, and then returns.
   * The queues are checked in a fixed order, so that type parents will always be resolved in preference to type parameter bounds and members.
   * @param unresolvedLexicalPhrases - the set of LexicalPhrase objects for names which could not be resolved since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a name
   * @throws ConceptualException - if a conceptual problem occurs while resolving a name
   */
  private boolean resolveNames(Set<LexicalPhrase> unresolvedLexicalPhrases) throws NameConflictException, ConceptualException
  {
    if (typeResolver.hasUnresolvedTypeParents())
    {
      return typeResolver.resolveTypeParents(unresolvedLexicalPhrases);
    }
    if (typeResolver.hasUnresolvedTypeParameterBounds())
    {
      return typeResolver.resolveTypeParameterBounds(unresolvedLexicalPhrases);
    }
    if (typeResolver.hasUnresolvedMembers())
    {
      return typeResolver.resolveTypeMembers(unresolvedLexicalPhrases);
    }
    return false;
  }
}
