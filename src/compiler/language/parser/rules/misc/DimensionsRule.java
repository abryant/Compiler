package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DIMENSIONS;
import static compiler.language.parser.ParseType.LSQUARE;
import static compiler.language.parser.ParseType.RSQUARE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Dimensions;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DimensionsRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION = new Production(LSQUARE, RSQUARE);
  private static final Production CONTINUATION_PRODUCTION = new Production(DIMENSIONS, LSQUARE, RSQUARE);

  public DimensionsRule()
  {
    super(DIMENSIONS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      return new Dimensions(1, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      Dimensions oldDimensions = (Dimensions) args[0];
      return new Dimensions(oldDimensions.getDimensions() + 1, ParseInfo.combine(oldDimensions.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
