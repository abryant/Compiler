package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.AT;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.PARAMETER;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.DefaultParameterAST;
import compiler.language.ast.misc.NormalParameterAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.parser.ParseType;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ParameterRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NORMAL_PRODUCTION = new Production<ParseType>(EXPRESSION_NO_TUPLE);
  private static final Production<ParseType> DEFAULT_PRODUCTION = new Production<ParseType>(AT, NAME, EQUALS, EXPRESSION_NO_TUPLE);

  @SuppressWarnings("unchecked")
  public ParameterRule()
  {
    super(PARAMETER, NORMAL_PRODUCTION, DEFAULT_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (NORMAL_PRODUCTION.equals(production))
    {
      ExpressionAST expression = (ExpressionAST) args[0];
      return new NormalParameterAST(expression, expression.getParseInfo());
    }
    if (DEFAULT_PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[1];
      ExpressionAST expression = (ExpressionAST) args[3];
      return new DefaultParameterAST(name, expression, ParseInfo.combine((ParseInfo) args[0], name.getParseInfo(), (ParseInfo) args[2], expression.getParseInfo()));
    }
    throw badTypeList();
  }

}
