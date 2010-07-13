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
  IMPLEMENTS_CLAUSE,        // PointerType[] (length == 0 if not specified)
  INTERFACE_EXTENDS_CLAUSE, // PointerType[] (length == 0 if not specified)
  MEMBER_LIST,              // Member[] TODO: tidy this up into a Members object containing a list of each type of member? then get rid of Member completely
  MEMBER,                   // Member
  FIELD,                    // Field
  CONSTRUCTOR,              // Constructor
  METHOD,                   // Method
  PROPERTY,                 // Property TODO
  STATIC_INITIALIZER,       // StaticInitializer

  // statements
  BLOCK,              // Block
  STATEMENTS,         // Statement[]
  STATEMENT,          // Statement TODO: add a rule for this

  // expressions
  EXPRESSION, // Expression TODO: add a rule for this

  // types
  BOOLEAN_TYPE,            // BooleanType
  CHARACTER_TYPE,          // CharacterType
  FLOATING_TYPE_LENGTH,    // FloatingTypeLength
  FLOATING_TYPE,           // FloatingType
  INTEGER_TYPE_LENGTH,     // IntegerTypeLength
  INTEGER_TYPE,            // IntegerType
  PRIMITIVE_TYPE,          // PrimitiveType
  POINTER_TYPE,            // PointerType
  TUPLE_TYPE,              // TupleType
  TYPE_LIST,               // Type[]
  TYPE,                    // Type
  TYPE_ARGUMENTS,          // TypeArgument[] (length > 0)
  TYPE_ARGUMENT_LIST,      // TypeArgument[] (length > 0)
  TYPE_ARGUMENT,           // TypeArgument
  TYPE_PARAMETERS,         // TypeParameter[] (length > 0)
  TYPE_PARAMETER_LIST,     // TypeParameter[] (length > 0)
  TYPE_PARAMETER,          // TypeParameter
  NORMAL_TYPE_PARAMETER,   // NormalTypeParameter
  WILDCARD_TYPE_PARAMETER, // WildcardTypeParameter
  VOID_TYPE,               // VoidType

  // common non-terminals
  ARGUMENT,           // Argument
  ARGUMENT_LIST,      // Argument[]
  ARGUMENTS,          // ArgumentList
  ASSIGNEE,           // Assignee
  ASSIGNEE_LIST,      // Assignee[]
  QNAME,              // QName
  THROWS_LIST,        // PointerType[] (length > 0)
  THROWS_CLAUSE,      // PointerType[] (length == 0 if none are specified)

  // literals
  NAME,           // Name
  STRING_LITERAL, // String

  // keywords (values do not matter for these, but should be null)
  ABSTRACT_KEYWORD,
  BOOLEAN_KEYWORD,
  BYTE_KEYWORD,
  CHARACTER_KEYWORD,
  CLASS_KEYWORD,
  DOUBLE_KEYWORD,
  EXTENDS_KEYWORD,
  FINAL_KEYWORD,
  FLOAT_KEYWORD,
  IMMUTABLE_KEYWORD,
  IMPLEMENTS_KEYWORD,
  IMPORT_KEYWORD,
  INT_KEYWORD,
  INTERFACE_KEYWORD,
  LONG_KEYWORD,
  MUTABLE_KEYWORD,
  NATIVE_KEYWORD,
  PACKAGE_KEYWORD,
  PRIVATE_KEYWORD,
  PROTECTED_KEYWORD,
  PUBLIC_KEYWORD,
  SHORT_KEYWORD,
  SIGNED_KEYWORD,
  STATIC_KEYWORD,
  SUPER_KEYWORD,
  SYNCHRONIZED_KEYWORD,
  THROWS_KEYWORD,
  TRANSIENT_KEYWORD,
  UNSIGNED_KEYWORD,
  VOID_KEYWORD,
  VOLATILE_KEYWORD,

  // symbols (values do not matter for these, but should be null)
  AT,
  COMMA,
  DOT,
  EQUALS,
  HASH,
  LANGLE,
  LBRACE,
  LPAREN,
  QUESTION_MARK,
  RANGLE,
  RBRACE,
  RPAREN,
  SEMICOLON,
  STAR,
  UNDERSCORE,
}
