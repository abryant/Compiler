package compiler.language.translator.conceptual;

/*
 * Created on 23 Dec 2010
 */

/**
 * @author Anthony Bryant
 */
public enum ScopeType
{
  // below are all of the possible scope types, followed by the type of object they contain
  PACKAGE,   // null - represents a whole package
  FILE,      // null - represents a single file, including imports
  CLASS,     // ConceptualClass
  INTERFACE, // ConceptualInterface
  ENUM,      // ConceptualEnum
  // TODO: add others
}
