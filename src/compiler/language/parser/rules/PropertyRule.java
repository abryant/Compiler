package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.ASSIGN_KEYWORD;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.OPTIONAL_BLOCK;
import static compiler.language.parser.ParseType.PROPERTY;
import static compiler.language.parser.ParseType.PROPERTY_KEYWORD;
import static compiler.language.parser.ParseType.RETRIEVE_KEYWORD;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.expression.Expression;
import compiler.language.ast.member.AccessSpecifier;
import compiler.language.ast.member.Property;
import compiler.language.ast.statement.Block;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.Type;
import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class PropertyRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, SEMICOLON};
  private static final Object[] RETRIEVE_PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, SEMICOLON};
  private static final Object[] ASSIGN_PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, SEMICOLON};
  private static final Object[] ASSIGN_RETRIEVE_PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, SEMICOLON};
  private static final Object[] RETRIEVE_ASSIGN_PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, SEMICOLON};

  private static final Object[] EQUALS_PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, SEMICOLON};
  private static final Object[] EQUALS_RETRIEVE_PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, SEMICOLON};
  private static final Object[] EQUALS_ASSIGN_PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, SEMICOLON};
  private static final Object[] EQUALS_ASSIGN_RETRIEVE_PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, SEMICOLON};
  private static final Object[] EQUALS_RETRIEVE_ASSIGN_PRODUCTION = new Object[] {PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, SEMICOLON};

  public PropertyRule()
  {
    super(PROPERTY, PRODUCTION, RETRIEVE_PRODUCTION, ASSIGN_PRODUCTION, ASSIGN_RETRIEVE_PRODUCTION, RETRIEVE_ASSIGN_PRODUCTION,
                    EQUALS_PRODUCTION, EQUALS_RETRIEVE_PRODUCTION, EQUALS_ASSIGN_PRODUCTION, EQUALS_ASSIGN_RETRIEVE_PRODUCTION, EQUALS_RETRIEVE_ASSIGN_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], null, null, null, null, null);
    }
    if (types == RETRIEVE_PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], null, null, null, (AccessSpecifier) args[3], (Block) args[5]);
    }
    if (types == ASSIGN_PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], null, (AccessSpecifier) args[3], (Block) args[5], null, null);
    }
    if (types == ASSIGN_RETRIEVE_PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], null, (AccessSpecifier) args[3], (Block) args[5], (AccessSpecifier) args[6], (Block) args[8]);
    }
    if (types == RETRIEVE_ASSIGN_PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], null, (AccessSpecifier) args[6], (Block) args[8], (AccessSpecifier) args[3], (Block) args[5]);
    }
    if (types == EQUALS_PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], (Expression) args[4], null, null, null, null);
    }
    if (types == EQUALS_RETRIEVE_PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], (Expression) args[4], null, null, (AccessSpecifier) args[5], (Block) args[7]);
    }
    if (types == EQUALS_ASSIGN_PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], (Expression) args[4], (AccessSpecifier) args[5], (Block) args[7], null, null);
    }
    if (types == EQUALS_ASSIGN_RETRIEVE_PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], (Expression) args[4], (AccessSpecifier) args[5], (Block) args[7], (AccessSpecifier) args[8], (Block) args[10]);
    }
    if (types == EQUALS_RETRIEVE_ASSIGN_PRODUCTION)
    {
      return new Property((Type) args[1], (Name) args[2], (Expression) args[4], (AccessSpecifier) args[8], (Block) args[10], (AccessSpecifier) args[5], (Block) args[7]);
    }
    throw badTypeList();
  }

}
