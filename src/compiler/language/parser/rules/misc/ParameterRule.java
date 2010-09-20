package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.AT;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.PARAMETER;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.misc.DefaultParameter;
import compiler.language.ast.misc.NormalParameter;
import compiler.language.ast.terminal.Name;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ParameterRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production NORMAL_PRODUCTION = new Production(EXPRESSION_NO_TUPLE);
  private static final Production DEFAULT_PRODUCTION = new Production(AT, NAME, EQUALS, EXPRESSION_NO_TUPLE);

  public ParameterRule()
  {
    super(PARAMETER, NORMAL_PRODUCTION, DEFAULT_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (NORMAL_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[0];
      return new NormalParameter(expression, expression.getParseInfo());
    }
    if (DEFAULT_PRODUCTION.equals(production))
    {
      Name name = (Name) args[1];
      Expression expression = (Expression) args[3];
      return new DefaultParameter(name, expression, ParseInfo.combine((ParseInfo) args[0], name.getParseInfo(), (ParseInfo) args[2], expression.getParseInfo()));
    }
    throw badTypeList();
  }

}
