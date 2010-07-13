package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.BYTE_KEYWORD;
import static compiler.language.parser.ParseType.INTEGER_TYPE_LENGTH;
import static compiler.language.parser.ParseType.INT_KEYWORD;
import static compiler.language.parser.ParseType.LONG_KEYWORD;
import static compiler.language.parser.ParseType.SHORT_KEYWORD;

import compiler.language.ast.IntegerTypeLength;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerTypeLengthRule extends Rule
{

  private static final Object[] BYTE_PRODUCTION = new Object[] {BYTE_KEYWORD};
  private static final Object[] SHORT_PRODUCTION = new Object[] {SHORT_KEYWORD};
  private static final Object[] INT_PRODUCTION = new Object[] {INT_KEYWORD};
  private static final Object[] LONG_PRODUCTION = new Object[] {LONG_KEYWORD};

  public IntegerTypeLengthRule()
  {
    super(INTEGER_TYPE_LENGTH, BYTE_PRODUCTION, SHORT_PRODUCTION, INT_PRODUCTION, LONG_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == BYTE_PRODUCTION)
    {
      return IntegerTypeLength.BYTE;
    }
    if (types == SHORT_PRODUCTION)
    {
      return IntegerTypeLength.SHORT;
    }
    if (types == INT_PRODUCTION)
    {
      return IntegerTypeLength.INT;
    }
    if (types == LONG_PRODUCTION)
    {
      return IntegerTypeLength.LONG;
    }
    throw badTypeList();
  }

}
