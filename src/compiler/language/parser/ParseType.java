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
  // =================
  //   NON-TERMINALS
  // =================

  // top level
  COMPILATION_UNIT,         // CompilationUnit
  PACKAGE_DECLARATION,      // PackageDeclaration
  IMPORT_DECLARATION,       // ImportDeclaration
  TYPE_DEFINITION,          // TypeDefinition

  // type definitions
  CLASS_DEFINITION,         // ClassDefinition
  INTERFACE_DEFINITION,     // InterfaceDefinition
  ENUM_DEFINITION,          // EnumDefinition
  CLASS_EXTENDS_CLAUSE,     // PointerType or null
  INTERFACE_LIST,           // ParseList<PointerType> (length > 0)
  IMPLEMENTS_CLAUSE,        // ParseList<PointerType> (length == 0 if not specified)
  INTERFACE_EXTENDS_CLAUSE, // ParseList<PointerType> (length == 0 if not specified)
  ENUM_CONSTANTS,           // ParseList<EnumConstant> (length == 0 if not specified)
  ENUM_CONSTANT_LIST,       // ParseList<EnumConstant> (length > 0)
  ENUM_CONSTANT,            // EnumConstant

  // members
  ACCESS_SPECIFIER,         // AccessSpecifier or null
  MODIFIER,                 // Modifier
  MODIFIERS,                // ParseList<Modifier> (length > 0)
  NATIVE_SPECIFIER,         // NativeSpecifier
  SINCE_SPECIFIER,          // SinceSpecifier
  MEMBER_HEADER,            // MemberHeader
  MEMBER_LIST,              // ParseList<Member>
  MEMBER,                   // Member
  FIELD,                    // Field
  CONSTRUCTOR,              // Constructor
  METHOD,                   // Method
  PROPERTY,                 // Property
  STATIC_INITIALIZER,       // StaticInitializer

  // statements
  BLOCK,                    // Block
  OPTIONAL_BLOCK,           // Block or null
  STATEMENTS,               // ParseList<Statement>
  STATEMENT,                // Statement
  EMPTY_STATEMENT,          // EmptyStatement

  // expressions
  // the return types that are Expression (AnotherExpression) mean that if the
  // rule's main production is used (e.g. for additive: a + b) then the type in
  // brackets will be returned but otherwise some other subclass of Expression
  // will be returned (e.g. BracketedExpression)
  // This is handled in the rules by checking for their created type via
  // 'instanceof', so Bracketed expressions must not just return their argument
  EXPRESSION,                     // Expression
  STATEMENT_EXPRESSION,           // StatementExpression
  TUPLE_EXPRESSION,               // Expression (TupleExpression)
  EXPRESSION_NO_TUPLE,            // Expression
  INLINE_IF_EXPRESSION,           // Expression (InlineIfExpression)
  BOOLEAN_OR_EXPRESSION,          // Expression (BooleanOrExpression)
  BOOLEAN_XOR_EXPRESSION,         // Expression (BooleanXorExpression)
  BOOLEAN_AND_EXPRESSION,         // Expression (BooleanAndExpression)
  EQUALITY_EXPRESSION,            // Expression (EqualityExpression)
  RELATIONAL_EXPRESSION,          // Expression (RelationalExpression or InstanceOfExpression)
  BITWISE_OR_EXPRESSION,          // Expression (BitwiseOrExpression)
  BITWISE_XOR_EXPRESSION,         // Expression (BitwiseXorExpression)
  BITWISE_AND_EXPRESSION,         // Expression (BitwiseAndExpression)
  SHIFT_EXPRESSION,               // Expression (ShiftExpression)
  ADDITIVE_EXPRESSION,            // Expression (AdditiveExpression)
  MULTIPLICATIVE_EXPRESSION,      // Expression (MultiplicativeExpression)
  TUPLE_INDEX_EXPRESSION,         // Expression (TupleIndexExpression)
  UNARY_EXPRESSION,               // Expression
  BOOLEAN_NOT_EXPRESSION,         // BooleanNotExpression
  BITWISE_NOT_EXPRESSION,         // BitwiseNotExpression
  CAST_EXPRESSION,                // CastExpression
  UNARY_PLUS_EXPRESSION,          // UnaryPlusExpression
  UNARY_MINUS_EXPRESSION,         // UnaryMinusExpression
  PRIMARY,                        // Expression
  PRIMARY_NO_TRAILING_DIMENSIONS, // Expression
  METHOD_CALL_EXPRESSION,         // MethodCallExpression
  FIELD_ACCESS_EXPRESSION,        // FieldAccessExpression
  THIS_ACCESS_EXPRESSION,         // ThisAccessExpression
  ARRAY_ACCESS_EXPRESSION,        // ArrayAccessExpression
  INSTANCIATION_EXPRESSION,       // InstanciationExpression
  CLOSURE_CREATION_EXPRESSION,    // ClosureCreationExpression
  ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER, // ArrayInstanciationExpression
  ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER,   // ArrayInstanciationExpression
  ARRAY_INITIALIZER,              // ParseList<Expression>
  EXPRESSION_LIST,                // ParseList<Expression>
  DIMENSION_EXPRESSIONS,          // ParseList<Expression>
  DIMENSIONS,                     // TODO: decide on something, should store ParseInfo and an integer
  ASSIGNMENT_OPERATOR,            // AssignmentOperator

  // types
  BOOLEAN_TYPE,             // BooleanType
  CHARACTER_TYPE,           // CharacterType
  CLOSURE_TYPE,             // ClosureType
  FLOATING_TYPE,            // FloatingType
  INTEGER_TYPE,             // IntegerType
  PRIMITIVE_TYPE,           // PrimitiveType
  POINTER_TYPE,             // PointerType TODO: this needs redoing in Grammar.txt to handle type arguments properly - a preliminary version is currently in Grammar.txt
  TUPLE_TYPE,               // TupleType
  TYPE_LIST,                // ParseList<Type> (length > 0)
  TYPE,                     // Type
  TYPE_ARGUMENTS,           // ParseList<TypeArgument> (length > 0)
  TYPE_ARGUMENT_LIST,       // ParseList<TypeArgument> (length > 0)
  TYPE_ARGUMENT,            // TypeArgument
  TYPE_PARAMETERS,          // ParseList<TypeParameter> (length > 0)
  TYPE_PARAMETER_LIST,      // ParseList<TypeParameter> (length > 0)
  TYPE_PARAMETER,           // TypeParameter
  NORMAL_TYPE_PARAMETER,    // NormalTypeParameter
  WILDCARD_TYPE_PARAMETER,  // WildcardTypeParameter
  VOID_TYPE,                // VoidType

  // miscellaneous non-terminals
  ARGUMENT,                 // Argument
  ARGUMENT_LIST,            // ParseList<Argument>
  ARGUMENTS,                // ArgumentList
  ASSIGNEE,                 // Assignee
  ASSIGNEE_LIST,            // ParseList<Assignee>
  PARAMETER,                // Parameter
  PARAMETER_LIST,           // ParseList<Parameter> (length > 0)
  PARAMETERS,               // ParseList<Parameter>
  QNAME,                    // QName
  THROWS_LIST,              // ParseList<PointerType> (length > 0)
  THROWS_CLAUSE,            // ParseList<PointerType> (length == 0 if none are specified)
  VERSION_NUMBER,           // VersionNumber

  // =============
  //   TERMINALS
  // =============

  // literals
  NAME,                     // Name
  INTEGER_LITERAL,          // IntegerLiteral
  STRING_LITERAL,           // StringLiteral

  // keywords (values for these should all be ParseInfo)
  ABSTRACT_KEYWORD,
  ASSIGN_KEYWORD,
  BOOLEAN_KEYWORD,
  BYTE_KEYWORD,
  CHARACTER_KEYWORD,
  CLASS_KEYWORD,
  DOUBLE_KEYWORD,
  ENUM_KEYWORD,
  EXTENDS_KEYWORD,
  FINAL_KEYWORD,
  FLOAT_KEYWORD,
  IMMUTABLE_KEYWORD,
  IMPLEMENTS_KEYWORD,
  IMPORT_KEYWORD,
  INSTANCEOF_KEYWORD,
  INT_KEYWORD,
  INTERFACE_KEYWORD,
  LONG_KEYWORD,
  MUTABLE_KEYWORD,
  NATIVE_KEYWORD,
  PACKAGE_KEYWORD,
  PRIVATE_KEYWORD,
  PROPERTY_KEYWORD,
  PROTECTED_KEYWORD,
  PUBLIC_KEYWORD,
  RETRIEVE_KEYWORD,
  SHORT_KEYWORD,
  SIGNED_KEYWORD,
  SINCE_KEYWORD,
  STATIC_KEYWORD,
  SUPER_KEYWORD,
  SYNCHRONIZED_KEYWORD,
  THROWS_KEYWORD,
  TRANSIENT_KEYWORD,
  UNSIGNED_KEYWORD,
  VOID_KEYWORD,
  VOLATILE_KEYWORD,

  // symbols (values for these should all be ParseInfo)
  AMPERSAND,
  ARROW, // "->", same as MINUS RANGLE, but without any whitespace between them
  AT,
  CARET,
  COLON,
  COMMA,
  DOT,
  DOUBLE_AMPERSAND,
  DOUBLE_CARET,
  DOUBLE_EQUALS,
  DOUBLE_PIPE,
  ELLIPSIS,
  EQUALS,
  EXCLAIMATION_MARK,
  EXCLAIMATION_MARK_EQUALS,
  HASH,
  LANGLE,
  LANGLE_EQUALS,
  LBRACE,
  LPAREN,
  PIPE,
  QUESTION_MARK,
  RANGLE,
  RANGLE_EQUALS,
  RBRACE,
  RPAREN,
  SEMICOLON,
  STAR,
  UNDERSCORE,
}
