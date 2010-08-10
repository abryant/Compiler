package compiler.language.parser.rules.member;

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

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.member.AccessSpecifier;
import compiler.language.ast.member.Property;
import compiler.language.ast.statement.Block;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
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
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      return new Property(type, name, null, null, null, null, null,
                          ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(), (ParseInfo) args[3]));
    }
    if (types == RETRIEVE_PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      AccessSpecifier retrieveAccess = (AccessSpecifier) args[3];
      Block retrieveBlock = (Block) args[5];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[4],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         (ParseInfo) args[6]);
      return new Property(type, name, null, null, null, retrieveAccess, retrieveBlock, info);
    }
    if (types == ASSIGN_PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      AccessSpecifier assignAccess = (AccessSpecifier) args[3];
      Block assignBlock = (Block) args[5];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[4],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         (ParseInfo) args[6]);
      return new Property(type, name, null, assignAccess, assignBlock, null, null, info);
    }
    if (types == ASSIGN_RETRIEVE_PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      AccessSpecifier assignAccess = (AccessSpecifier) args[3];
      Block assignBlock = (Block) args[5];
      AccessSpecifier retrieveAccess = (AccessSpecifier) args[6];
      Block retrieveBlock = (Block) args[8];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[4],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[7],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         (ParseInfo) args[9]);
      return new Property(type, name, null, assignAccess, assignBlock, retrieveAccess, retrieveBlock, info);
    }
    if (types == RETRIEVE_ASSIGN_PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      AccessSpecifier retrieveAccess = (AccessSpecifier) args[3];
      Block retrieveBlock = (Block) args[5];
      AccessSpecifier assignAccess = (AccessSpecifier) args[6];
      Block assignBlock = (Block) args[8];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[4],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[7],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         (ParseInfo) args[9]);
      return new Property(type, name, null, assignAccess, assignBlock, retrieveAccess, retrieveBlock, info);
    }
    if (types == EQUALS_PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      Expression expression = (Expression) args[4];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         (ParseInfo) args[3],
                                         expression.getParseInfo(),
                                         (ParseInfo) args[5]);
      return new Property(type, name, expression, null, null, null, null, info);
    }
    if (types == EQUALS_RETRIEVE_PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      Expression expression = (Expression) args[4];
      AccessSpecifier retrieveAccess = (AccessSpecifier) args[5];
      Block retrieveBlock = (Block) args[7];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         (ParseInfo) args[3],
                                         expression.getParseInfo(),
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[6],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         (ParseInfo) args[8]);
      return new Property(type, name, expression, null, null, retrieveAccess, retrieveBlock, info);
    }
    if (types == EQUALS_ASSIGN_PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      Expression expression = (Expression) args[4];
      AccessSpecifier assignAccess = (AccessSpecifier) args[5];
      Block assignBlock = (Block) args[7];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         (ParseInfo) args[3],
                                         expression.getParseInfo(),
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[6],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         (ParseInfo) args[8]);
      return new Property(type, name, expression, assignAccess, assignBlock, null, null, info);
    }
    if (types == EQUALS_ASSIGN_RETRIEVE_PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      Expression expression = (Expression) args[4];
      AccessSpecifier assignAccess = (AccessSpecifier) args[5];
      Block assignBlock = (Block) args[7];
      AccessSpecifier retrieveAccess = (AccessSpecifier) args[8];
      Block retrieveBlock = (Block) args[10];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         (ParseInfo) args[3],
                                         expression.getParseInfo(),
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[6],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[9],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         (ParseInfo) args[11]);
      return new Property(type, name, expression, assignAccess, assignBlock, retrieveAccess, retrieveBlock, info);
    }
    if (types == EQUALS_RETRIEVE_ASSIGN_PRODUCTION)
    {
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      Expression expression = (Expression) args[4];
      AccessSpecifier retrieveAccess = (AccessSpecifier) args[5];
      Block retrieveBlock = (Block) args[7];
      AccessSpecifier assignAccess = (AccessSpecifier) args[8];
      Block assignBlock = (Block) args[10];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         (ParseInfo) args[3],
                                         expression.getParseInfo(),
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[6],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[9],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         (ParseInfo) args[11]);
      return new Property(type, name, expression, assignAccess, assignBlock, retrieveAccess, retrieveBlock, info);
    }
    throw badTypeList();
  }

}
