package compiler.language.parser.rules;

import compiler.language.ast.Argument;
import compiler.language.ast.ArgumentList;
import compiler.language.parser.ParseType;
import compiler.parser.Rule;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArgumentsRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {ParseType.LPAREN, ParseType.RPAREN};
  private static final Object[] PRODUCTION = new Object[] {ParseType.LPAREN, ParseType.ARGUMENT_LIST, ParseType.RPAREN};

  public ArgumentsRule()
  {
    super(ParseType.ARGUMENTS, EMPTY_PRODUCTION, PRODUCTION);
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
