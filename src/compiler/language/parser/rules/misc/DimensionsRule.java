package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DIMENSIONS;
import static compiler.language.parser.ParseType.LSQUARE;
import static compiler.language.parser.ParseType.RSQUARE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Dimensions;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DimensionsRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {LSQUARE, RSQUARE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {DIMENSIONS, LSQUARE, RSQUARE};

  public DimensionsRule()
  {
    super(DIMENSIONS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      return new Dimensions(1, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      Dimensions oldDimensions = (Dimensions) args[0];
      return new Dimensions(oldDimensions.getDimensions() + 1, ParseInfo.combine(oldDimensions.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
