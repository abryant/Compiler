package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.GETTER_KEYWORD;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.OPTIONAL_BLOCK;
import static compiler.language.parser.ParseType.PROPERTY;
import static compiler.language.parser.ParseType.PROPERTY_KEYWORD;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.SETTER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.member.AccessSpecifierAST;
import compiler.language.ast.member.MemberHeaderAST;
import compiler.language.ast.member.PropertyAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.LanguageParseException;
import compiler.language.parser.ParseType;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PropertyRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, SEMICOLON);
  private static final Production<ParseType> RETRIEVE_PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, GETTER_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> ASSIGN_PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, SETTER_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> ASSIGN_RETRIEVE_PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, SETTER_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, GETTER_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> RETRIEVE_ASSIGN_PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, ACCESS_SPECIFIER, GETTER_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, SETTER_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);

  private static final Production<ParseType> EQUALS_PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, SEMICOLON);
  private static final Production<ParseType> EQUALS_RETRIEVE_PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, GETTER_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> EQUALS_ASSIGN_PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, SETTER_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> EQUALS_ASSIGN_RETRIEVE_PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, SETTER_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, GETTER_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);
  private static final Production<ParseType> EQUALS_RETRIEVE_ASSIGN_PRODUCTION =
    new Production<ParseType>(MEMBER_HEADER, PROPERTY_KEYWORD, TYPE, NAME, EQUALS, EXPRESSION, ACCESS_SPECIFIER, GETTER_KEYWORD, OPTIONAL_BLOCK, ACCESS_SPECIFIER, SETTER_KEYWORD, OPTIONAL_BLOCK, SEMICOLON);

  @SuppressWarnings("unchecked")
  public PropertyRule()
  {
    super(PROPERTY, PRODUCTION, RETRIEVE_PRODUCTION, ASSIGN_PRODUCTION, ASSIGN_RETRIEVE_PRODUCTION, RETRIEVE_ASSIGN_PRODUCTION,
                    EQUALS_PRODUCTION, EQUALS_RETRIEVE_PRODUCTION, EQUALS_ASSIGN_PRODUCTION, EQUALS_ASSIGN_RETRIEVE_PRODUCTION, EQUALS_RETRIEVE_ASSIGN_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    MemberHeaderAST header = (MemberHeaderAST) args[0];
    if (header.getAccessSpecifier() != null)
    {
      throw new LanguageParseException("Access Specifiers are not allowed at the start of a property", header.getAccessSpecifier().getLexicalPhrase());
    }
    ModifierAST[] modifiers = header.getModifiers();
    if (PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      return new PropertyAST(modifiers, type, name, null, null, null, null, null,
                             LexicalPhrase.combine(header.getLexicalPhrase(), (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(), (LexicalPhrase) args[4]));
    }
    if (RETRIEVE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[4];
      BlockAST retrieveBlock = (BlockAST) args[6];
      LexicalPhrase phrase = LexicalPhrase.combine(header.getLexicalPhrase(),
                                         (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(),
                                         retrieveAccess != null ? retrieveAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[5],
                                         retrieveBlock != null ? retrieveBlock.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[7]);
      return new PropertyAST(modifiers, type, name, null, null, null, retrieveAccess, retrieveBlock, phrase);
    }
    if (ASSIGN_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[4];
      BlockAST assignBlock = (BlockAST) args[6];
      LexicalPhrase phrase = LexicalPhrase.combine(header.getLexicalPhrase(),
                                         (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(),
                                         assignAccess != null ? assignAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[5],
                                         assignBlock != null ? assignBlock.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[7]);
      return new PropertyAST(modifiers, type, name, null, assignAccess, assignBlock, null, null, phrase);
    }
    if (ASSIGN_RETRIEVE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[4];
      BlockAST assignBlock = (BlockAST) args[6];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[7];
      BlockAST retrieveBlock = (BlockAST) args[9];
      LexicalPhrase phrase = LexicalPhrase.combine(header.getLexicalPhrase(),
                                         (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(),
                                         assignAccess != null ? assignAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[5],
                                         assignBlock != null ? assignBlock.getLexicalPhrase() : null,
                                         retrieveAccess != null ? retrieveAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[8],
                                         retrieveBlock != null ? retrieveBlock.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[10]);
      return new PropertyAST(modifiers, type, name, null, assignAccess, assignBlock, retrieveAccess, retrieveBlock, phrase);
    }
    if (RETRIEVE_ASSIGN_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[4];
      BlockAST retrieveBlock = (BlockAST) args[6];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[7];
      BlockAST assignBlock = (BlockAST) args[9];
      LexicalPhrase phrase = LexicalPhrase.combine(header.getLexicalPhrase(),
                                         (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(),
                                         retrieveAccess != null ? retrieveAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[5],
                                         retrieveBlock != null ? retrieveBlock.getLexicalPhrase() : null,
                                         assignAccess != null ? assignAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[8],
                                         assignBlock != null ? assignBlock.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[10]);
      return new PropertyAST(modifiers, type, name, null, assignAccess, assignBlock, retrieveAccess, retrieveBlock, phrase);
    }
    if (EQUALS_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      ExpressionAST expression = (ExpressionAST) args[5];
      LexicalPhrase phrase = LexicalPhrase.combine(header.getLexicalPhrase(),
                                         (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(),
                                         (LexicalPhrase) args[4],
                                         expression.getLexicalPhrase(),
                                         (LexicalPhrase) args[6]);
      return new PropertyAST(modifiers, type, name, expression, null, null, null, null, phrase);
    }
    if (EQUALS_RETRIEVE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      ExpressionAST expression = (ExpressionAST) args[5];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[6];
      BlockAST retrieveBlock = (BlockAST) args[8];
      LexicalPhrase phrase = LexicalPhrase.combine(header.getLexicalPhrase(),
                                         (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(),
                                         (LexicalPhrase) args[4],
                                         expression.getLexicalPhrase(),
                                         retrieveAccess != null ? retrieveAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[7],
                                         retrieveBlock != null ? retrieveBlock.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[9]);
      return new PropertyAST(modifiers, type, name, expression, null, null, retrieveAccess, retrieveBlock, phrase);
    }
    if (EQUALS_ASSIGN_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      ExpressionAST expression = (ExpressionAST) args[5];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[6];
      BlockAST assignBlock = (BlockAST) args[8];
      LexicalPhrase phrase = LexicalPhrase.combine(header.getLexicalPhrase(),
                                         (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(),
                                         (LexicalPhrase) args[4],
                                         expression.getLexicalPhrase(),
                                         assignAccess != null ? assignAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[7],
                                         assignBlock != null ? assignBlock.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[9]);
      return new PropertyAST(modifiers, type, name, expression, assignAccess, assignBlock, null, null, phrase);
    }
    if (EQUALS_ASSIGN_RETRIEVE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      ExpressionAST expression = (ExpressionAST) args[5];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[6];
      BlockAST assignBlock = (BlockAST) args[8];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[9];
      BlockAST retrieveBlock = (BlockAST) args[11];
      LexicalPhrase phrase = LexicalPhrase.combine(header.getLexicalPhrase(),
                                         (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(),
                                         (LexicalPhrase) args[4],
                                         expression.getLexicalPhrase(),
                                         assignAccess != null ? assignAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[7],
                                         assignBlock != null ? assignBlock.getLexicalPhrase() : null,
                                         retrieveAccess != null ? retrieveAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[10],
                                         retrieveBlock != null ? retrieveBlock.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[12]);
      return new PropertyAST(modifiers, type, name, expression, assignAccess, assignBlock, retrieveAccess, retrieveBlock, phrase);
    }
    if (EQUALS_RETRIEVE_ASSIGN_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      ExpressionAST expression = (ExpressionAST) args[5];
      AccessSpecifierAST retrieveAccess = (AccessSpecifierAST) args[6];
      BlockAST retrieveBlock = (BlockAST) args[8];
      AccessSpecifierAST assignAccess = (AccessSpecifierAST) args[9];
      BlockAST assignBlock = (BlockAST) args[11];
      LexicalPhrase phrase = LexicalPhrase.combine(header.getLexicalPhrase(),
                                         (LexicalPhrase) args[1], type.getLexicalPhrase(), name.getLexicalPhrase(),
                                         (LexicalPhrase) args[4],
                                         expression.getLexicalPhrase(),
                                         retrieveAccess != null ? retrieveAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[7],
                                         retrieveBlock != null ? retrieveBlock.getLexicalPhrase() : null,
                                         assignAccess != null ? assignAccess.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[10],
                                         assignBlock != null ? assignBlock.getLexicalPhrase() : null,
                                         (LexicalPhrase) args[12]);
      return new PropertyAST(modifiers, type, name, expression, assignAccess, assignBlock, retrieveAccess, retrieveBlock, phrase);
    }
    throw badTypeList();
  }

}
