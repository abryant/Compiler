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
  PACKAGE,         // ConceptualPackage
  FILE,            // ConceptualFile
  CLASS,           // ConceptualClass
  INTERFACE,       // ConceptualInterface
  ENUM,            // ConceptualEnum
  ENUM_CONSTANT,   // EnumConstant
  TYPE_ARGUMENT,   // TypeArgument
  MEMBER_VARIABLE, // MemberVariable
  PROPERTY,        // Property
  CONSTRUCTOR,     // Constructor
  METHOD,          // Set<Method>
  UNRESOLVABLE_DUMMY, // null - this is used where a Scope has a parent, but is not the child of any other scopes, and so will never have a fully qualified name
  // TODO: add others
}
