package compiler.language.parser.rules.type;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.IntegerType;
import compiler.language.ast.type.IntegerTypeLength;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerTypeRule extends Rule
{

  private static final Object[] UNSPECIFIED_BYTE_PRODUCTION = new Object[] {ParseType.BYTE_KEYWORD};
  private static final Object[] SIGNED_BYTE_PRODUCTION = new Object[] {ParseType.SIGNED_KEYWORD, ParseType.BYTE_KEYWORD};
  private static final Object[] UNSIGNED_BYTE_PRODUCTION = new Object[] {ParseType.UNSIGNED_KEYWORD, ParseType.BYTE_KEYWORD};

  private static final Object[] UNSPECIFIED_SHORT_PRODUCTION = new Object[] {ParseType.SHORT_KEYWORD};
  private static final Object[] SIGNED_SHORT_PRODUCTION = new Object[] {ParseType.SIGNED_KEYWORD, ParseType.SHORT_KEYWORD};
  private static final Object[] UNSIGNED_SHORT_PRODUCTION = new Object[] {ParseType.UNSIGNED_KEYWORD, ParseType.SHORT_KEYWORD};

  private static final Object[] UNSPECIFIED_INT_PRODUCTION = new Object[] {ParseType.INT_KEYWORD};
  private static final Object[] SIGNED_INT_PRODUCTION = new Object[] {ParseType.SIGNED_KEYWORD, ParseType.INT_KEYWORD};
  private static final Object[] UNSIGNED_INT_PRODUCTION = new Object[] {ParseType.UNSIGNED_KEYWORD, ParseType.INT_KEYWORD};

  private static final Object[] UNSPECIFIED_LONG_PRODUCTION = new Object[] {ParseType.LONG_KEYWORD};
  private static final Object[] SIGNED_LONG_PRODUCTION = new Object[] {ParseType.SIGNED_KEYWORD, ParseType.LONG_KEYWORD};
  private static final Object[] UNSIGNED_LONG_PRODUCTION = new Object[] {ParseType.UNSIGNED_KEYWORD, ParseType.LONG_KEYWORD};

  public IntegerTypeRule()
  {
    super(ParseType.INTEGER_TYPE, UNSPECIFIED_BYTE_PRODUCTION,  SIGNED_BYTE_PRODUCTION,  UNSIGNED_BYTE_PRODUCTION,
                                  UNSPECIFIED_SHORT_PRODUCTION, SIGNED_SHORT_PRODUCTION, UNSIGNED_SHORT_PRODUCTION,
                                  UNSPECIFIED_INT_PRODUCTION,   SIGNED_INT_PRODUCTION,   UNSIGNED_INT_PRODUCTION,
                                  UNSPECIFIED_LONG_PRODUCTION,  SIGNED_LONG_PRODUCTION,  UNSIGNED_LONG_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == UNSPECIFIED_BYTE_PRODUCTION)
    {
      return new IntegerType(null, IntegerTypeLength.BYTE, (ParseInfo) args[0]);
    }
    if (types == SIGNED_BYTE_PRODUCTION)
    {
      return new IntegerType(true, IntegerTypeLength.BYTE, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == UNSIGNED_BYTE_PRODUCTION)
    {
      return new IntegerType(false, IntegerTypeLength.BYTE, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }

    if (types == UNSPECIFIED_SHORT_PRODUCTION)
    {
      return new IntegerType(null, IntegerTypeLength.SHORT, (ParseInfo) args[0]);
    }
    if (types == SIGNED_SHORT_PRODUCTION)
    {
      return new IntegerType(true, IntegerTypeLength.SHORT, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == UNSIGNED_SHORT_PRODUCTION)
    {
      return new IntegerType(false, IntegerTypeLength.SHORT, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }

    if (types == UNSPECIFIED_INT_PRODUCTION)
    {
      return new IntegerType(null, IntegerTypeLength.INT, (ParseInfo) args[0]);
    }
    if (types == SIGNED_INT_PRODUCTION)
    {
      return new IntegerType(true, IntegerTypeLength.INT, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == UNSIGNED_INT_PRODUCTION)
    {
      return new IntegerType(false, IntegerTypeLength.INT, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }

    if (types == UNSPECIFIED_LONG_PRODUCTION)
    {
      return new IntegerType(null, IntegerTypeLength.LONG, (ParseInfo) args[0]);
    }
    if (types == SIGNED_LONG_PRODUCTION)
    {
      return new IntegerType(true, IntegerTypeLength.LONG, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == UNSIGNED_LONG_PRODUCTION)
    {
      return new IntegerType(false, IntegerTypeLength.LONG, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
