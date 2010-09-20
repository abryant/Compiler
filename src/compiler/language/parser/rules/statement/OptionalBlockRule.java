package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.OPTIONAL_BLOCK;

import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class OptionalBlockRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production();
  private static final Production PRODUCTION = new Production(BLOCK);

  public OptionalBlockRule()
  {
    super(OPTIONAL_BLOCK, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
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
