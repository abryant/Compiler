package compiler.language.parser;

/*
 * Created on 30 Jun 2010
 */

/**
 * The types used by the language parser.
 * The comment after each type represents the type of value that will be stored in the same token as it.
 * @author Anthony Bryant
 */
public enum ParseType
{
  // non-terminals

  // top level
  COMPILATION_UNIT,    // CompilationUnit
  PACKAGE_DECLARATION, // PackageDeclaration
  IMPORT_DECLARATION,  // ImportDeclaration
  TYPE_DEFINITION,     // TypeDefinition

  // classes, interfaces, enums
  ACCESS_SPECIFIER,         // AccessSpecifier or null
  MODIFIER,                 // Modifier
  MODIFIERS,                // Modifier[]
  NATIVE_SPECIFIER,         // NativeSpecifier
  CLASS_DEFINITION,         // ClassDefinition
  INTERFACE_DEFINITION,     // InterfaceDefinition
  CLASS_EXTENDS_CLAUSE,     // PointerType or null
  INTERFACE_LIST,           // PointerType[] (length > 0)
  IMPLEMENTS_CLAUSE,        // PointerType[] (length > 0) or null
  INTERFACE_EXTENDS_CLAUSE, // PointerType[] (length > 0) or null
  MEMBER_LIST,              // Member[] TODO: tidy this up into a Members object containing a list of each type of member? then get rid of Member completely
  MEMBER,                   // Member
  FIELD,                    // Field
  CONSTRUCTOR,              // Constructor TODO
  METHOD,                   // Method TODO
  STATIC_INITIALIZER,       // StaticInitializer TODO

  // statements
  BLOCK,              // Block
  STATEMENTS,         // Statement[]
  STATEMENT,          // Statement TODO: add a rule for this

  // expressions
  EXPRESSION, // Expression TODO: add a rule for this

  // common non-terminals
  ASSIGNEE,           // Assignee
  ASSIGNEE_LIST,      // Assignee[]
  POINTER_TYPE,       // PointerType
  QNAME,              // QName
  THROWS_LIST,        // PointerType[] (length > 0)
  THROWS_CLAUSE,      // PointerType[] (length > 0) or null
  TYPE,               // Type
  TYPE_ARGUMENT,      // TypeArgument
  TYPE_ARGUMENT_LIST, // TypeArgument[] (length > 0)
  TYPE_ARGUMENTS,     // TypeArgument[] (length > 0) or null

  // literals
  NAME,           // Name
  STRING_LITERAL, // String

  // keywords (values do not matter for these, but should be null)
  ABSTRACT_KEYWORD,
  CLASS_KEYWORD,
  EXTENDS_KEYWORD,
  FINAL_KEYWORD,
  IMMUTABLE_KEYWORD,
  IMPLEMENTS_KEYWORD,
  IMPORT_KEYWORD,
  INTERFACE_KEYWORD,
  NATIVE_KEYWORD,
  PACKAGE_KEYWORD,
  PRIVATE_KEYWORD,
  PROTECTED_KEYWORD,
  PUBLIC_KEYWORD,
  STATIC_KEYWORD,
  SUPER_KEYWORD,
  SYNCHRONIZED_KEYWORD,
  THROWS_KEYWORD,
  TRANSIENT_KEYWORD,
  VOLATILE_KEYWORD,

  // symbols (values do not matter for these, but should be null)
  COMMA,
  DOT,
  EQUALS,
  HASH,
  LANGLE,
  LBRACE,
  LPAREN,
  RANGLE,
  RBRACE,
  RPAREN,
  SEMICOLON,
  STAR,
  UNDERSCORE,
}
