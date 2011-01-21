package compiler.language.conceptual;

/*
 * Created on 23 Dec 2010
 */

/**
 * @author Anthony Bryant
 */
public enum ScopeType
{
  // below are all of the possible scope types, followed by the type of object they contain
  PACKAGE,       // null - represents a whole package
  FILE,          // null - represents a single file, including imports
  CLASS,         // ConceptualClass
  INTERFACE,     // ConceptualInterface
  ENUM,          // ConceptualEnum
  TYPE_ARGUMENT, // TypeArgument
  MEMBER,        // ScopedMemberSet
  // TODO: add others
}
