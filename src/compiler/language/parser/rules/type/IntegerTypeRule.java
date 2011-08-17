package compiler.language.parser.rules.type;

import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.IntegerTypeAST;
import compiler.language.ast.type.IntegerTypeLengthAST;
import compiler.language.parser.ParseType;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class IntegerTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> UNSPECIFIED_BYTE_PRODUCTION = new Production<ParseType>(ParseType.BYTE_KEYWORD);
  private static final Production<ParseType> SIGNED_BYTE_PRODUCTION = new Production<ParseType>(ParseType.SIGNED_KEYWORD, ParseType.BYTE_KEYWORD);
  private static final Production<ParseType> UNSIGNED_BYTE_PRODUCTION = new Production<ParseType>(ParseType.UNSIGNED_KEYWORD, ParseType.BYTE_KEYWORD);

  private static final Production<ParseType> UNSPECIFIED_SHORT_PRODUCTION = new Production<ParseType>(ParseType.SHORT_KEYWORD);
  private static final Production<ParseType> SIGNED_SHORT_PRODUCTION = new Production<ParseType>(ParseType.SIGNED_KEYWORD, ParseType.SHORT_KEYWORD);
  private static final Production<ParseType> UNSIGNED_SHORT_PRODUCTION = new Production<ParseType>(ParseType.UNSIGNED_KEYWORD, ParseType.SHORT_KEYWORD);

  private static final Production<ParseType> UNSPECIFIED_INT_PRODUCTION = new Production<ParseType>(ParseType.INT_KEYWORD);
  private static final Production<ParseType> SIGNED_INT_PRODUCTION = new Production<ParseType>(ParseType.SIGNED_KEYWORD, ParseType.INT_KEYWORD);
  private static final Production<ParseType> UNSIGNED_INT_PRODUCTION = new Production<ParseType>(ParseType.UNSIGNED_KEYWORD, ParseType.INT_KEYWORD);

  private static final Production<ParseType> UNSPECIFIED_LONG_PRODUCTION = new Production<ParseType>(ParseType.LONG_KEYWORD);
  private static final Production<ParseType> SIGNED_LONG_PRODUCTION = new Production<ParseType>(ParseType.SIGNED_KEYWORD, ParseType.LONG_KEYWORD);
  private static final Production<ParseType> UNSIGNED_LONG_PRODUCTION = new Production<ParseType>(ParseType.UNSIGNED_KEYWORD, ParseType.LONG_KEYWORD);

  @SuppressWarnings("unchecked")
  public IntegerTypeRule()
  {
    super(ParseType.INTEGER_TYPE, UNSPECIFIED_BYTE_PRODUCTION,  SIGNED_BYTE_PRODUCTION,  UNSIGNED_BYTE_PRODUCTION,
                                  UNSPECIFIED_SHORT_PRODUCTION, SIGNED_SHORT_PRODUCTION, UNSIGNED_SHORT_PRODUCTION,
                                  UNSPECIFIED_INT_PRODUCTION,   SIGNED_INT_PRODUCTION,   UNSIGNED_INT_PRODUCTION,
                                  UNSPECIFIED_LONG_PRODUCTION,  SIGNED_LONG_PRODUCTION,  UNSIGNED_LONG_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (UNSPECIFIED_BYTE_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(null, IntegerTypeLengthAST.BYTE, (LexicalPhrase) args[0]);
    }
    if (SIGNED_BYTE_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(true, IntegerTypeLengthAST.BYTE, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }
    if (UNSIGNED_BYTE_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(false, IntegerTypeLengthAST.BYTE, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }

    if (UNSPECIFIED_SHORT_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(null, IntegerTypeLengthAST.SHORT, (LexicalPhrase) args[0]);
    }
    if (SIGNED_SHORT_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(true, IntegerTypeLengthAST.SHORT, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }
    if (UNSIGNED_SHORT_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(false, IntegerTypeLengthAST.SHORT, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }

    if (UNSPECIFIED_INT_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(null, IntegerTypeLengthAST.INT, (LexicalPhrase) args[0]);
    }
    if (SIGNED_INT_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(true, IntegerTypeLengthAST.INT, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }
    if (UNSIGNED_INT_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(false, IntegerTypeLengthAST.INT, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }

    if (UNSPECIFIED_LONG_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(null, IntegerTypeLengthAST.LONG, (LexicalPhrase) args[0]);
    }
    if (SIGNED_LONG_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(true, IntegerTypeLengthAST.LONG, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }
    if (UNSIGNED_LONG_PRODUCTION.equals(production))
    {
      return new IntegerTypeAST(false, IntegerTypeLengthAST.LONG, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1]));
    }
    throw badTypeList();
  }

}
