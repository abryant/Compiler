package compiler.language.parser.rules.type;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.IntegerType;
import compiler.language.ast.type.IntegerTypeLength;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerTypeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production UNSPECIFIED_BYTE_PRODUCTION = new Production(ParseType.BYTE_KEYWORD);
  private static final Production SIGNED_BYTE_PRODUCTION = new Production(ParseType.SIGNED_KEYWORD, ParseType.BYTE_KEYWORD);
  private static final Production UNSIGNED_BYTE_PRODUCTION = new Production(ParseType.UNSIGNED_KEYWORD, ParseType.BYTE_KEYWORD);

  private static final Production UNSPECIFIED_SHORT_PRODUCTION = new Production(ParseType.SHORT_KEYWORD);
  private static final Production SIGNED_SHORT_PRODUCTION = new Production(ParseType.SIGNED_KEYWORD, ParseType.SHORT_KEYWORD);
  private static final Production UNSIGNED_SHORT_PRODUCTION = new Production(ParseType.UNSIGNED_KEYWORD, ParseType.SHORT_KEYWORD);

  private static final Production UNSPECIFIED_INT_PRODUCTION = new Production(ParseType.INT_KEYWORD);
  private static final Production SIGNED_INT_PRODUCTION = new Production(ParseType.SIGNED_KEYWORD, ParseType.INT_KEYWORD);
  private static final Production UNSIGNED_INT_PRODUCTION = new Production(ParseType.UNSIGNED_KEYWORD, ParseType.INT_KEYWORD);

  private static final Production UNSPECIFIED_LONG_PRODUCTION = new Production(ParseType.LONG_KEYWORD);
  private static final Production SIGNED_LONG_PRODUCTION = new Production(ParseType.SIGNED_KEYWORD, ParseType.LONG_KEYWORD);
  private static final Production UNSIGNED_LONG_PRODUCTION = new Production(ParseType.UNSIGNED_KEYWORD, ParseType.LONG_KEYWORD);

  public IntegerTypeRule()
  {
    super(ParseType.INTEGER_TYPE, UNSPECIFIED_BYTE_PRODUCTION,  SIGNED_BYTE_PRODUCTION,  UNSIGNED_BYTE_PRODUCTION,
                                  UNSPECIFIED_SHORT_PRODUCTION, SIGNED_SHORT_PRODUCTION, UNSIGNED_SHORT_PRODUCTION,
                                  UNSPECIFIED_INT_PRODUCTION,   SIGNED_INT_PRODUCTION,   UNSIGNED_INT_PRODUCTION,
                                  UNSPECIFIED_LONG_PRODUCTION,  SIGNED_LONG_PRODUCTION,  UNSIGNED_LONG_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (UNSPECIFIED_BYTE_PRODUCTION.equals(production))
    {
      return new IntegerType(null, IntegerTypeLength.BYTE, (ParseInfo) args[0]);
    }
    if (SIGNED_BYTE_PRODUCTION.equals(production))
    {
      return new IntegerType(true, IntegerTypeLength.BYTE, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (UNSIGNED_BYTE_PRODUCTION.equals(production))
    {
      return new IntegerType(false, IntegerTypeLength.BYTE, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }

    if (UNSPECIFIED_SHORT_PRODUCTION.equals(production))
    {
      return new IntegerType(null, IntegerTypeLength.SHORT, (ParseInfo) args[0]);
    }
    if (SIGNED_SHORT_PRODUCTION.equals(production))
    {
      return new IntegerType(true, IntegerTypeLength.SHORT, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (UNSIGNED_SHORT_PRODUCTION.equals(production))
    {
      return new IntegerType(false, IntegerTypeLength.SHORT, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }

    if (UNSPECIFIED_INT_PRODUCTION.equals(production))
    {
      return new IntegerType(null, IntegerTypeLength.INT, (ParseInfo) args[0]);
    }
    if (SIGNED_INT_PRODUCTION.equals(production))
    {
      return new IntegerType(true, IntegerTypeLength.INT, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (UNSIGNED_INT_PRODUCTION.equals(production))
    {
      return new IntegerType(false, IntegerTypeLength.INT, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }

    if (UNSPECIFIED_LONG_PRODUCTION.equals(production))
    {
      return new IntegerType(null, IntegerTypeLength.LONG, (ParseInfo) args[0]);
    }
    if (SIGNED_LONG_PRODUCTION.equals(production))
    {
      return new IntegerType(true, IntegerTypeLength.LONG, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (UNSIGNED_LONG_PRODUCTION.equals(production))
    {
      return new IntegerType(false, IntegerTypeLength.LONG, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
