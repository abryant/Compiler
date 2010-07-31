package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.ARGUMENT_LIST;
import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;

import compiler.language.ast.misc.Argument;
import compiler.language.ast.misc.ArgumentList;
import compiler.parser.Rule;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArgumentsRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {LPAREN, RPAREN};
  private static final Object[] PRODUCTION = new Object[] {LPAREN, ARGUMENT_LIST, RPAREN};

  public ArgumentsRule()
  {
    super(ARGUMENTS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new ArgumentList(new Argument[0]);
    }
    if (types == PRODUCTION)
    {
      return new ArgumentList((Argument[]) args[1]);
    }
    throw badTypeList();
  }

}
