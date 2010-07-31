package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ARGUMENT;
import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.AT;
import static compiler.language.parser.ParseType.ELLIPSIS;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;
import static compiler.language.parser.ParseType.MODIFIERS;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.expression.Expression;
import compiler.language.ast.member.Modifier;
import compiler.language.ast.misc.DefaultArgument;
import compiler.language.ast.misc.SingleArgument;
import compiler.language.ast.misc.VariadicArgument;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.Type;
import compiler.parser.Rule;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArgumentRule extends Rule
{

  private static final Object[] SINGLE_PRODUCTION = new Object[] {MODIFIERS, TYPE, NAME};
  private static final Object[] DEFAULT_PRODUCTION = new Object[] {MODIFIERS, TYPE, AT, NAME, EQUALS, EXPRESSION_NO_TUPLE};
  private static final Object[] VARIADIC_PRODUCTION = new Object[] {MODIFIERS, TYPE, ELLIPSIS, NAME};
  private static final Object[] SINGLE_NO_MODIFIERS_PRODUCTION = new Object[] {TYPE, NAME};
  private static final Object[] DEFAULT_NO_MODIFIERS_PRODUCTION = new Object[] {TYPE, AT, NAME, EQUALS, EXPRESSION_NO_TUPLE};
  private static final Object[] VARIADIC_NO_MODIFIERS_PRODUCTION = new Object[] {TYPE, ELLIPSIS, NAME};
  private static final Object[] MULTIPLE_PRODUCTION = new Object[] {ARGUMENTS};

  public ArgumentRule()
  {
    super(ARGUMENT, SINGLE_PRODUCTION, DEFAULT_PRODUCTION, VARIADIC_PRODUCTION,
                    SINGLE_NO_MODIFIERS_PRODUCTION, DEFAULT_NO_MODIFIERS_PRODUCTION, VARIADIC_NO_MODIFIERS_PRODUCTION,
                    MULTIPLE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == SINGLE_PRODUCTION)
    {
      return new SingleArgument((Modifier[]) args[0], (Type) args[1], (Name) args[2]);
    }
    if (types == DEFAULT_PRODUCTION)
    {
      return new DefaultArgument((Modifier[]) args[0], (Type) args[1], (Name) args[3], (Expression) args[5]);
    }
    if (types == VARIADIC_PRODUCTION)
    {
      return new VariadicArgument((Modifier[]) args[0], (Type) args[1], (Name) args[3]);
    }
    if (types == SINGLE_NO_MODIFIERS_PRODUCTION)
    {
      return new SingleArgument(new Modifier[0], (Type) args[0], (Name) args[1]);
    }
    if (types == DEFAULT_NO_MODIFIERS_PRODUCTION)
    {
      return new DefaultArgument(new Modifier[0], (Type) args[0], (Name) args[2], (Expression) args[4]);
    }
    if (types == VARIADIC_NO_MODIFIERS_PRODUCTION)
    {
      return new VariadicArgument(new Modifier[0], (Type) args[0], (Name) args[2]);
    }
    if (types == MULTIPLE_PRODUCTION)
    {
      // ArgumentList is a subclass of Argument, so just return what has been passed in
      return args[0];
    }
    throw badTypeList();
  }

}
