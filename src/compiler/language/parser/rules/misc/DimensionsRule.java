package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DIMENSIONS;
import static compiler.language.parser.ParseType.LSQUARE;
import static compiler.language.parser.ParseType.RSQUARE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.DimensionsAST;
import compiler.language.parser.ParseType;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class DimensionsRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(LSQUARE, RSQUARE);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(DIMENSIONS, LSQUARE, RSQUARE);

  @SuppressWarnings("unchecked")
  public DimensionsRule()
  {
    super(DIMENSIONS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      return new DimensionsAST(1, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      DimensionsAST oldDimensions = (DimensionsAST) args[0];
      return new DimensionsAST(oldDimensions.getDimensions() + 1, ParseInfo.combine(oldDimensions.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
