package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.AT;
import static compiler.language.parser.ParseType.ELLIPSIS;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;
import static compiler.language.parser.ParseType.MODIFIERS;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.PARAMETER;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.TYPE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.DefaultParameterAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.SingleParameterAST;
import compiler.language.ast.misc.VariadicParameterAST;
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
public final class ParameterRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> SINGLE_PRODUCTION = new Production<ParseType>(MODIFIERS, TYPE, NAME);
  private static final Production<ParseType> DEFAULT_PRODUCTION = new Production<ParseType>(MODIFIERS, TYPE, AT, NAME, EQUALS, EXPRESSION_NO_TUPLE);
  private static final Production<ParseType> VARIADIC_PRODUCTION = new Production<ParseType>(MODIFIERS, TYPE, ELLIPSIS, NAME);
  private static final Production<ParseType> SINGLE_NO_MODIFIERS_PRODUCTION = new Production<ParseType>(TYPE, NAME);
  private static final Production<ParseType> DEFAULT_NO_MODIFIERS_PRODUCTION = new Production<ParseType>(TYPE, AT, NAME, EQUALS, EXPRESSION_NO_TUPLE);
  private static final Production<ParseType> VARIADIC_NO_MODIFIERS_PRODUCTION = new Production<ParseType>(TYPE, ELLIPSIS, NAME);
  private static final Production<ParseType> MULTIPLE_PRODUCTION = new Production<ParseType>(PARAMETERS);

  // TODO: variadic default parameters? they would apply when the length of the passed in array is 0

  @SuppressWarnings("unchecked")
  public ParameterRule()
  {
    super(PARAMETER, SINGLE_PRODUCTION, DEFAULT_PRODUCTION, VARIADIC_PRODUCTION,
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
      return new SingleParameterAST(modifiers.toArray(new ModifierAST[0]), type, name, LexicalPhrase.combine(modifiers.getLexicalPhrase(), type.getLexicalPhrase(), name.getLexicalPhrase()));
    }
    if (DEFAULT_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ModifierAST> modifiers = (ParseList<ModifierAST>) args[0];
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[3];
      ExpressionAST expression = (ExpressionAST) args[5];
      return new DefaultParameterAST(modifiers.toArray(new ModifierAST[0]), type, name, expression,
                                 LexicalPhrase.combine(modifiers.getLexicalPhrase(), type.getLexicalPhrase(), (LexicalPhrase) args[2], name.getLexicalPhrase(), (LexicalPhrase) args[4], expression.getLexicalPhrase()));
    }
    if (VARIADIC_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ModifierAST> modifiers = (ParseList<ModifierAST>) args[0];
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[3];
      return new VariadicParameterAST(modifiers.toArray(new ModifierAST[0]), type, name,
                                  LexicalPhrase.combine(modifiers.getLexicalPhrase(), type.getLexicalPhrase(), (LexicalPhrase) args[2], name.getLexicalPhrase()));
    }
    if (SINGLE_NO_MODIFIERS_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      NameAST name = (NameAST) args[1];
      return new SingleParameterAST(new ModifierAST[0], type, name,
                                LexicalPhrase.combine(type.getLexicalPhrase(), name.getLexicalPhrase()));
    }
    if (DEFAULT_NO_MODIFIERS_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      NameAST name = (NameAST) args[2];
      ExpressionAST expression = (ExpressionAST) args[4];
      return new DefaultParameterAST(new ModifierAST[0], type, name, expression,
                                 LexicalPhrase.combine(type.getLexicalPhrase(), (LexicalPhrase) args[1], name.getLexicalPhrase(), (LexicalPhrase) args[3], expression.getLexicalPhrase()));
    }
    if (VARIADIC_NO_MODIFIERS_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      NameAST name = (NameAST) args[2];
      return new VariadicParameterAST(new ModifierAST[0], type, name,
                                  LexicalPhrase.combine(type.getLexicalPhrase(), (LexicalPhrase) args[1], name.getLexicalPhrase()));
    }
    if (MULTIPLE_PRODUCTION.equals(production))
    {
      // ParameterListAST is a subclass of ParameterAST, so just return what has been passed in
      return args[0];
    }
    throw badTypeList();
  }

}
