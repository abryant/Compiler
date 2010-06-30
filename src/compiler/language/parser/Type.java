package compiler.language.parser;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public enum Type
{
  // non-terminals
  COMPILATION_UNIT,
  PACKAGE_DECLARATION,
  IMPORT_DECLARATIONS,
  IMPORT_DECLARATION,
  TYPE_DEFINITIONS,
  TYPE_DEFINITION,

  // common non-terminals
  QNAME,

  // literals
  NAME,

  // keywords
  IMPORT_KEYWORD,
  PACKAGE_KEYWORD,
  STATIC_KEYWORD,

  // symbols
  DOT,
  SEMICOLON,
  STAR
}
