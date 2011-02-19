package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.OPTIONAL_BLOCK;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.parser.ParseType;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class OptionalBlockRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(BLOCK);

  @SuppressWarnings("unchecked")
  public OptionalBlockRule()
  {
    super(OPTIONAL_BLOCK, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return null;
    }
    if (PRODUCTION.equals(production))
    {
      // the block has already been built, so return it
      return args[0];
    }
    throw badTypeList();
  }

}
