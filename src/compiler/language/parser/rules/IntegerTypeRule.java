package compiler.language.parser.rules;

import compiler.language.ast.IntegerType;
import compiler.language.ast.IntegerTypeLength;
import compiler.language.parser.ParseType;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerTypeRule extends Rule
{

  private static final Object[] UNSPECIFIED_PRODUCTION = new Object[] {ParseType.INTEGER_TYPE_LENGTH};
  private static final Object[] SIGNED_PRODUCTION = new Object[] {ParseType.SIGNED_KEYWORD, ParseType.INTEGER_TYPE_LENGTH};
  private static final Object[] UNSIGNED_PRODUCTION = new Object[] {ParseType.UNSIGNED_KEYWORD, ParseType.INTEGER_TYPE_LENGTH};

  public IntegerTypeRule()
  {
    super(ParseType.INTEGER_TYPE, UNSPECIFIED_PRODUCTION, SIGNED_PRODUCTION, UNSIGNED_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == UNSPECIFIED_PRODUCTION)
    {
      return new IntegerType(null, (IntegerTypeLength) args[0]);
    }
    if (types == SIGNED_PRODUCTION)
    {
      return new IntegerType(true, (IntegerTypeLength) args[1]);
    }
    if (types == UNSIGNED_PRODUCTION)
    {
      return new IntegerType(false, (IntegerTypeLength) args[1]);
    }
    throw badTypeList();
  }

}
