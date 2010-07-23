package compiler.language.parser.rules;

import compiler.language.parser.ParseType;
import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StatementRule extends Rule
{

  private static final Object[][] PRODUCTIONS = new Object[][]
  {
    new Object[] {ParseType.BLOCK},
    new Object[] {ParseType.EMPTY_STATEMENT},
  };

  public StatementRule()
  {
    super(ParseType.STATEMENT, PRODUCTIONS);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    for (Object[] production : PRODUCTIONS)
    {
      if (types == production)
      {
        // just return the argument, as all Statements are actually subclasses of Statement
        return args[0];
      }
    }
    throw badTypeList();
  }

}
