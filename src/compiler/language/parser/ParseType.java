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
  BLOCK,                            // Block
  OPTIONAL_BLOCK,                   // Block or null
  STATEMENTS,                       // ParseList<Statement>
  STATEMENT,                        // Statement
  EMPTY_STATEMENT,                  // EmptyStatement
  CONSTRUCTOR_INVOCATION_STATEMENT, // ConstructorInvocationStatement
  LOCAL_DECLARATION,                // LocalDeclarationStatement
  ASSIGNMENT,                       // AssignmentStatement
  IF_STATEMENT,                     // IfStatement
  ELSE_IF_CLAUSES,                  // ParseList<ElseIfClause>
  ELSE_IF_CLAUSE,                   // ElseIfClause
  ELSE_CLAUSE,                      // Block or null
  WHILE_STATEMENT,                  // WhileStatement
  DO_STATEMENT,                     // DoStatement
  FOR_EACH_STATEMENT,               // ForEachStatement
  FOR_STATEMENT,                    // ForStatement
  FOR_INIT,                         // Statement or null
  FOR_UPDATE,                       // Statement or null
  SWITCH_STATEMENT,                 // SwitchStatement
  SWITCH_CASES,                     // ParseList<SwitchCase>
  SWITCH_CASE,                      // SwitchCase
  BREAK_STATEMENT,                  // BreakStatement
  CONTINUE_STATEMENT,               // ContinueStatement
  FALLTHROUGH_STATEMENT,            // FallthroughStatement
  RETURN_STATEMENT,                 // ReturnStatement
  THROW_STATEMENT,                  // ThrowStatement
  SYNCHRONIZED_STATEMENT,           // SynchronizedStatement
  TRY_STATEMENT,                    // TryStatement
  CATCH_CLAUSES,                    // ParseList<CatchClause>
  CATCH_CLAUSE,                     // CatchClause
  FINALLY_CLAUSE,                   // Block or null
  INCREMENT,                        // IncrementStatement
  DECREMENT,                        // DecrementStatement


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
  CAST_EXPRESSION,                // CastExpression
  PRIMARY,                        // Expression
  PRIMARY_NOT_QNAME,              // Expression
  PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME, // Expression
  METHOD_CALL_EXPRESSION,         // MethodCallExpression
  FIELD_ACCESS_EXPRESSION,        // FieldAccessExpression
  FIELD_ACCESS_EXPRESSION_NOT_QNAME, // FieldAccessExpression
  THIS_ACCESS_EXPRESSION,         // ThisAccessExpression
  SUPER_ACCESS_EXPRESSION,        // SuperAccessExpression
  ARRAY_ACCESS_EXPRESSION,        // ArrayAccessExpression
  INSTANCIATION_EXPRESSION,       // InstanciationExpression
  CLOSURE_CREATION_EXPRESSION,    // ClosureCreationExpression
  ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER, // ArrayInstanciationExpression
  ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER,   // ArrayInstanciationExpression
  ARRAY_INITIALIZER,              // ParseList<Expression>
  EXPRESSION_LIST,                // ParseList<Expression>
  DIMENSION_EXPRESSION,           // ParseContainer<Expression>
  DIMENSION_EXPRESSIONS,          // ParseList<Expression>

  // types
  ARRAY_TYPE,                        // ArrayType
  BOOLEAN_TYPE,                      // BooleanType
  CHARACTER_TYPE,                    // CharacterType
  CLOSURE_TYPE,                      // ClosureType
  FLOATING_TYPE,                     // FloatingType
  INTEGER_TYPE,                      // IntegerType
  PRIMITIVE_TYPE,                    // PrimitiveType
  POINTER_TYPE,                      // PointerType
  POINTER_TYPE_RANGLE,               // ParseContainer<PointerType>
  POINTER_TYPE_NOT_QNAME,            // PointerType
  POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME, // PointerType
  POINTER_TYPE_TRAILING_PARAMS,              // PointerType
  POINTER_TYPE_TRAILING_PARAMS_RANGLE,       // ParseContainer<PointerType>
  TUPLE_TYPE,                        // TupleType
  TYPE_LIST,                         // ParseList<Type> (length > 0)
  TYPE,                              // Type
  TYPE_RANGLE,                       // ParseContainer<Type>
  TYPE_NOT_QNAME,                    // Type
  TYPE_NOT_POINTER_TYPE,             // Type
  TYPE_NOT_ARRAY_TYPE,               // Type
  TYPE_ARGUMENTS,                    // ParseList<TypeArgument> (length > 0)
  TYPE_ARGUMENT_LIST,                // ParseList<TypeArgument> (length > 0)
  TYPE_ARGUMENT_LIST_RANGLE,         // ParseContainer<ParseList<TypeArgument>> (length > 0)
  TYPE_ARGUMENT,                     // TypeArgument
  TYPE_ARGUMENT_RANGLE,              // ParseContainer<TypeArgument>
  TYPE_PARAMETERS,                   // ParseList<TypeParameter> (length > 0)
  TYPE_PARAMETER_LIST,               // ParseList<TypeParameter> (length > 0)
  TYPE_PARAMETER_LIST_RANGLE,        // ParseContainer<ParseList<TypeParameter>> (length > 0)
  TYPE_PARAMETER_LIST_DOUBLE_RANGLE, // ParseContainer<ParseContainer<ParseList<TypeParameter>>> (length > 0)
  TYPE_PARAMETER,                    // TypeParameter
  TYPE_PARAMETER_RANGLE,             // ParseContainer<TypeParameter>
  WILDCARD_TYPE_PARAMETER,           // WildcardTypeParameter
  WILDCARD_TYPE_PARAMETER_RANGLE,    // ParseContainer<WildcardTypeParameter>
  VOID_TYPE,                         // VoidType

  // miscellaneous non-terminals
  ARGUMENT,                  // Argument
  ARGUMENT_LIST,             // ParseList<Argument>
  ARGUMENTS,                 // ArgumentList
  ASSIGNEE,                  // Assignee
  ASSIGNEE_LIST,             // ParseList<Assignee>
  ASSIGNMENT_OPERATOR,       // AssignmentOperator
  DECLARATION_ASSIGNEE,      // DeclarationAssignee
  DECLARATION_ASSIGNEE_LIST, // ParseList<DeclarationAssignee>
  DIMENSIONS,                // Dimensions
  PARAMETER,                 // Parameter
  PARAMETER_LIST,            // ParseList<Parameter> (length > 0)
  PARAMETERS,                // ParseList<Parameter>
  QNAME,                     // QName
  THROWS_LIST,               // ParseList<PointerType> (length > 0)
  THROWS_CLAUSE,             // ParseList<PointerType> (length == 0 if none are specified)
  VERSION_NUMBER,            // VersionNumber

  // =============
  //   TERMINALS
  // =============

  // literals
  // all literals must have a special case for extracting ParseInfo from them in LanguageParser
  NAME,              // Name
  INTEGER_LITERAL,   // IntegerLiteral
  FLOATING_LITERAL,  // FloatingLiteral
  CHARACTER_LITERAL, // CharacterLiteral
  STRING_LITERAL,    // StringLiteral

  // keywords (values for these should all be ParseInfo)
  ABSTRACT_KEYWORD,
  ASSIGN_KEYWORD,
  BOOLEAN_KEYWORD,
  BYTE_KEYWORD,
  CAST_KEYWORD,
  CHARACTER_KEYWORD,
  CLASS_KEYWORD,
  CLOSURE_KEYWORD,
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
  NEW_KEYWORD,
  NIL_KEYWORD,
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
  THIS_KEYWORD,
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
  DOUBLE_LANGLE,
  DOUBLE_PIPE,
  DOUBLE_RANGLE,
  ELLIPSIS,
  EQUALS,
  EXCLAIMATION_MARK,
  EXCLAIMATION_MARK_EQUALS,
  FORWARD_SLASH,
  HASH,
  LANGLE,
  LANGLE_EQUALS,
  LBRACE,
  LPAREN,
  LSQUARE,
  MINUS,
  PERCENT,
  PIPE,
  PLUS,
  QUESTION_MARK,
  RANGLE,
  RANGLE_EQUALS,
  RBRACE,
  RPAREN,
  RSQUARE,
  SEMICOLON,
  STAR,
  TILDE,
  UNDERSCORE,
}
