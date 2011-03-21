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
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.DefaultArgumentAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.SingleArgumentAST;
import compiler.language.ast.misc.VariadicArgumentAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ArgumentRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> SINGLE_PRODUCTION = new Production<ParseType>(MODIFIERS, TYPE, NAME);
  private static final Production<ParseType> DEFAULT_PRODUCTION = new Production<ParseType>(MODIFIERS, TYPE, AT, NAME, EQUALS, EXPRESSION_NO_TUPLE);
  private static final Production<ParseType> VARIADIC_PRODUCTION = new Production<ParseType>(MODIFIERS, TYPE, ELLIPSIS, NAME);
  private static final Production<ParseType> SINGLE_NO_MODIFIERS_PRODUCTION = new Production<ParseType>(TYPE, NAME);
  private static final Production<ParseType> DEFAULT_NO_MODIFIERS_PRODUCTION = new Production<ParseType>(TYPE, AT, NAME, EQUALS, EXPRESSION_NO_TUPLE);
  private static final Production<ParseType> VARIADIC_NO_MODIFIERS_PRODUCTION = new Production<ParseType>(TYPE, ELLIPSIS, NAME);
  private static final Production<ParseType> MULTIPLE_PRODUCTION = new Production<ParseType>(ARGUMENTS);

  // TODO: variadic default arguments? they would apply when the length of the passed in array is 0

  @SuppressWarnings("unchecked")
  public ArgumentRule()
  {
    super(ARGUMENT, SINGLE_PRODUCTION, DEFAULT_PRODUCTION, VARIADIC_PRODUCTION,
                    SINGLE_NO_MODIFIERS_PRODUCTION, DEFAULT_NO_MODIFIERS_PRODUCTION, VARIADIC_NO_MODIFIERS_PRODUCTION,
                    MULTIPLE_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (SINGLE_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ModifierAST> modifiers = (ParseList<ModifierAST>) args[0];
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      return new SingleArgumentAST(modifiers.toArray(new ModifierAST[0]), type, name, ParseInfo.combine(modifiers.getParseInfo(), type.getParseInfo(), name.getParseInfo()));
    }
    if (DEFAULT_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ModifierAST> modifiers = (ParseList<ModifierAST>) args[0];
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[3];
      ExpressionAST expression = (ExpressionAST) args[5];
      return new DefaultArgumentAST(modifiers.toArray(new ModifierAST[0]), type, name, expression,
                                 ParseInfo.combine(modifiers.getParseInfo(), type.getParseInfo(), (ParseInfo) args[2], name.getParseInfo(), (ParseInfo) args[4], expression.getParseInfo()));
    }
    if (VARIADIC_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ModifierAST> modifiers = (ParseList<ModifierAST>) args[0];
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[3];
      return new VariadicArgumentAST(modifiers.toArray(new ModifierAST[0]), type, name,
                                  ParseInfo.combine(modifiers.getParseInfo(), type.getParseInfo(), (ParseInfo) args[2], name.getParseInfo()));
    }
    if (SINGLE_NO_MODIFIERS_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      NameAST name = (NameAST) args[1];
      return new SingleArgumentAST(new ModifierAST[0], type, name,
                                ParseInfo.combine(type.getParseInfo(), name.getParseInfo()));
    }
    if (DEFAULT_NO_MODIFIERS_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      NameAST name = (NameAST) args[2];
      ExpressionAST expression = (ExpressionAST) args[4];
      return new DefaultArgumentAST(new ModifierAST[0], type, name, expression,
                                 ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(), (ParseInfo) args[3], expression.getParseInfo()));
    }
    if (VARIADIC_NO_MODIFIERS_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      NameAST name = (NameAST) args[2];
      return new VariadicArgumentAST(new ModifierAST[0], type, name,
                                  ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1], name.getParseInfo()));
    }
    if (MULTIPLE_PRODUCTION.equals(production))
    {
      // ArgumentListAST is a subclass of ArgumentAST, so just return what has been passed in
      return args[0];
    }
    throw badTypeList();
  }

}
