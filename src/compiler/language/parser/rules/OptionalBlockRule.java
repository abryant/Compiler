package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.OPTIONAL_BLOCK;

import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class OptionalBlockRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {BLOCK};

  public OptionalBlockRule()
  {
    super(OPTIONAL_BLOCK, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return null;
    }
    if (types == PRODUCTION)
    {
      // the block has already been built, so return it
      return args[0];
    }
    throw badTypeList();
  }

}
