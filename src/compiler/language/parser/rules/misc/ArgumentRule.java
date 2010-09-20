package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ARGUMENT;
import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.AT;
import static compiler.language.parser.ParseType.ELLIPSIS;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;
import static compiler.language.parser.ParseType.MODIFIERS;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.misc.DefaultArgument;
import compiler.language.ast.misc.Modifier;
import compiler.language.ast.misc.SingleArgument;
import compiler.language.ast.misc.VariadicArgument;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArgumentRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production SINGLE_PRODUCTION = new Production(MODIFIERS, TYPE, NAME);
  private static final Production DEFAULT_PRODUCTION = new Production(MODIFIERS, TYPE, AT, NAME, EQUALS, EXPRESSION_NO_TUPLE);
  private static final Production VARIADIC_PRODUCTION = new Production(MODIFIERS, TYPE, ELLIPSIS, NAME);
  private static final Production SINGLE_NO_MODIFIERS_PRODUCTION = new Production(TYPE, NAME);
  private static final Production DEFAULT_NO_MODIFIERS_PRODUCTION = new Production(TYPE, AT, NAME, EQUALS, EXPRESSION_NO_TUPLE);
  private static final Production VARIADIC_NO_MODIFIERS_PRODUCTION = new Production(TYPE, ELLIPSIS, NAME);
  private static final Production MULTIPLE_PRODUCTION = new Production(ARGUMENTS);

  // TODO: variadic default arguments? they would apply when the length of the passed in array is 0

  public ArgumentRule()
  {
    super(ARGUMENT, SINGLE_PRODUCTION, DEFAULT_PRODUCTION, VARIADIC_PRODUCTION,
                    SINGLE_NO_MODIFIERS_PRODUCTION, DEFAULT_NO_MODIFIERS_PRODUCTION, VARIADIC_NO_MODIFIERS_PRODUCTION,
                    MULTIPLE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (SINGLE_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Modifier> modifiers = (ParseList<Modifier>) args[0];
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      return new SingleArgument(modifiers.toArray(new Modifier[0]), type, name, ParseInfo.combine(modifiers.getParseInfo(), type.getParseInfo(), name.getParseInfo()));
    }
    if (DEFAULT_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Modifier> modifiers = (ParseList<Modifier>) args[0];
      Type type = (Type) args[1];
      Name name = (Name) args[3];
      Expression expression = (Expression) args[5];
      return new DefaultArgument(modifiers.toArray(new Modifier[0]), type, name, expression,
                                 ParseInfo.combine(modifiers.getParseInfo(), type.getParseInfo(), (ParseInfo) args[2], name.getParseInfo(), (ParseInfo) args[4], expression.getParseInfo()));
    }
    if (VARIADIC_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Modifier> modifiers = (ParseList<Modifier>) args[0];
      Type type = (Type) args[1];
      Name name = (Name) args[3];
      return new VariadicArgument(modifiers.toArray(new Modifier[0]), type, name,
                                  ParseInfo.combine(modifiers.getParseInfo(), type.getParseInfo(), (ParseInfo) args[2], name.getParseInfo()));
    }
    if (SINGLE_NO_MODIFIERS_PRODUCTION.equals(production))
    {
      Type type = (Type) args[0];
      Name name = (Name) args[1];
      return new SingleArgument(new Modifier[0], type, name,
                                ParseInfo.combine(type.getParseInfo(), name.getParseInfo()));
    }
    if (DEFAULT_NO_MODIFIERS_PRODUCTION.equals(production))
    {
      Type type = (Type) args[0];
      Name name = (Name) args[2];
      Expression expression = (Expression) args[4];
      return new DefaultArgument(new Modifier[0], type, name, expression,
                                 ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(), (ParseInfo) args[3], expression.getParseInfo()));
    }
    if (VARIADIC_NO_MODIFIERS_PRODUCTION.equals(production))
    {
      Type type = (Type) args[0];
      Name name = (Name) args[2];
      return new VariadicArgument(new Modifier[0], type, name,
                                  ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1], name.getParseInfo()));
    }
    if (MULTIPLE_PRODUCTION.equals(production))
    {
      // ArgumentList is a subclass of Argument, so just return what has been passed in
      return args[0];
    }
    throw badTypeList();
  }

}
