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
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.member.AccessSpecifierAST;
import compiler.language.ast.member.PropertyAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PropertyRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, SEMICOLON);
  private static final Production<ParseType> RETRIEVE_PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> ASSIGN_PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> ASSIGN_RETRIEVE_PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> RETRIEVE_ASSIGN_PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);

  private static final Production<ParseType> EQUALS_PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, SEMICOLON);
  private static final Production<ParseType> EQUALS_RETRIEVE_PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> EQUALS_ASSIGN_PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> EQUALS_ASSIGN_RETRIEVE_PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> EQUALS_RETRIEVE_ASSIGN_PRODUCTION = new Production<ParseType>(PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, RETRIEVE_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, ASSIGN_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);

  @SuppressWarnings("unchecked")
  public PropertyRule()
  {
    super(PROPERTY, PRODUCTION, RETRIEVE_PRODUCTION, ASSIGN_PRODUCTION, ASSIGN_RETRIEVE_PRODUCTION, RETRIEVE_ASSIGN_PRODUCTION,
                    EQUALS_PRODUCTION, EQUALS_RETRIEVE_PRODUCTION, EQUALS_ASSIGN_PRODUCTION, EQUALS_ASSIGN_RETRIEVE_PRODUCTION, EQUALS_RETRIEVE_ASSIGN_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      return new PropertyAST(type, name, null, null, null, null, null,
                          ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(), (ParseInfo) args[3]));
    }
    if (RETRIEVE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[3];
      BlockAST retrieveBlock = (BlockAST) args[5];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[4],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         (ParseInfo) args[6]);
      return new PropertyAST(type, name, null, null, null, retrieveAccess, retrieveBlock, info);
    }
    if (ASSIGN_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[3];
      BlockAST assignBlock = (BlockAST) args[5];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[4],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         (ParseInfo) args[6]);
      return new PropertyAST(type, name, null, assignAccess, assignBlock, null, null, info);
    }
    if (ASSIGN_RETRIEVE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[3];
      BlockAST assignBlock = (BlockAST) args[5];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[6];
      BlockAST retrieveBlock = (BlockAST) args[8];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[4],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[7],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         (ParseInfo) args[9]);
      return new PropertyAST(type, name, null, assignAccess, assignBlock, retrieveAccess, retrieveBlock, info);
    }
    if (RETRIEVE_ASSIGN_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[3];
      BlockAST retrieveBlock = (BlockAST) args[5];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[6];
      BlockAST assignBlock = (BlockAST) args[8];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[4],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[7],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         (ParseInfo) args[9]);
      return new PropertyAST(type, name, null, assignAccess, assignBlock, retrieveAccess, retrieveBlock, info);
    }
    if (EQUALS_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      ExpressionAST expression = (ExpressionAST) args[4];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         (ParseInfo) args[3],
                                         expression.getParseInfo(),
                                         (ParseInfo) args[5]);
      return new PropertyAST(type, name, expression, null, null, null, null, info);
    }
    if (EQUALS_RETRIEVE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      ExpressionAST expression = (ExpressionAST) args[4];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[5];
      BlockAST retrieveBlock = (BlockAST) args[7];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         (ParseInfo) args[3],
                                         expression.getParseInfo(),
                                         retrieveAccess != null ? retrieveAccess.getParseInfo() : null,
                                         (ParseInfo) args[6],
                                         retrieveBlock != null ? retrieveBlock.getParseInfo() : null,
                                         (ParseInfo) args[8]);
      return new PropertyAST(type, name, expression, null, null, retrieveAccess, retrieveBlock, info);
    }
    if (EQUALS_ASSIGN_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      ExpressionAST expression = (ExpressionAST) args[4];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[5];
      BlockAST assignBlock = (BlockAST) args[7];
      ParseInfo info = ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(),
                                         (ParseInfo) args[3],
                                         expression.getParseInfo(),
                                         assignAccess != null ? assignAccess.getParseInfo() : null,
                                         (ParseInfo) args[6],
                                         assignBlock != null ? assignBlock.getParseInfo() : null,
                                         (ParseInfo) args[8]);
      return new PropertyAST(type, name, expression, assignAccess, assignBlock, null, null, info);
    }
    if (EQUALS_ASSIGN_RETRIEVE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      ExpressionAST expression = (ExpressionAST) args[4];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[5];
      BlockAST assignBlock = (BlockAST) args[7];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[8];
      BlockAST retrieveBlock = (BlockAST) args[10];
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
      return new PropertyAST(type, name, expression, assignAccess, assignBlock, retrieveAccess, retrieveBlock, info);
    }
    if (EQUALS_RETRIEVE_ASSIGN_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      ExpressionAST expression = (ExpressionAST) args[4];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[5];
      BlockAST retrieveBlock = (BlockAST) args[7];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[8];
      BlockAST assignBlock = (BlockAST) args[10];
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
      return new PropertyAST(type, name, expression, assignAccess, assignBlock, retrieveAccess, retrieveBlock, info);
    }
    throw badTypeList();
  }

}
