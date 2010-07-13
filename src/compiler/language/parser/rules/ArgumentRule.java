package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ARGUMENT;
import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.AT;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.DefaultArgument;
import compiler.language.ast.Expression;
import compiler.language.ast.Name;
import compiler.language.ast.SingleArgument;
import compiler.language.ast.Type;
import compiler.parser.Rule;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArgumentRule extends Rule
{

  private static final Object[] SINGLE_PRODUCTION = new Object[] {TYPE, NAME};
  private static final Object[] DEFAULT_PRODUCTION = new Object[] {TYPE, AT, NAME, EQUALS, EXPRESSION};
  private static final Object[] MULTIPLE_PRODUCTION = new Object[] {ARGUMENTS};

  public ArgumentRule()
  {
    super(ARGUMENT, SINGLE_PRODUCTION, DEFAULT_PRODUCTION, MULTIPLE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == SINGLE_PRODUCTION)
    {
      return new SingleArgument((Type) args[0], (Name) args[1]);
    }
    if (types == DEFAULT_PRODUCTION)
    {
      return new DefaultArgument((Type) args[0], (Name) args[2], (Expression) args[4]);
    }
    if (types == MULTIPLE_PRODUCTION)
    {
      // ArgumentList is a subclass of Argument, so just return what has been passed in
      return args[0];
    }
    throw badTypeList();
  }

}