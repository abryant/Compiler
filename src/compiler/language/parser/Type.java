package compiler.language.parser;

/*
 * Created on 30 Jun 2010
 */

/**
 * The types used by the language parser.
 * The comment after each type represents the type of value that will be stored in the same token as it.
 * @author Anthony Bryant
 */
public enum Type
{
  // non-terminals
  COMPILATION_UNIT,    // CompilationUnit
  PACKAGE_DECLARATION, // PackageDeclaration or null
  IMPORT_DECLARATIONS, // ImportDeclaration[]
  IMPORT_DECLARATION,  // ImportDeclaration
  TYPE_DEFINITIONS,    // TypeDefinition[]
  TYPE_DEFINITION,     // TypeDefinition

  // common non-terminals
  ACCESS_SPECIFIER, // AccessSpecifier or null
  MODIFIER,         // Modifier
  MODIFIERS,        // Modifier[]
  NATIVE_SPECIFIER, // NativeSpecifier
  QNAME,            // QName

  // literals
  NAME,           // Name
  STRING_LITERAL, // String

  // keywords (values do not matter for these, but should be null)
  ABSTRACT_KEYWORD,
  FINAL_KEYWORD,
  IMMUTABLE_KEYWORD,
  IMPORT_KEYWORD,
  NATIVE_KEYWORD,
  PACKAGE_KEYWORD,
  PRIVATE_KEYWORD,
  PROTECTED_KEYWORD,
  PUBLIC_KEYWORD,
  STATIC_KEYWORD,
  SYNCHRONIZED_KEYWORD,
  TRANSIENT_KEYWORD,
  VOLATILE_KEYWORD,

  // symbols (values do not matter for these, but should be null)
  DOT,
  LPAREN,
  RPAREN,
  SEMICOLON,
  STAR
}
