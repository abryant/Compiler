package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.THROW_KEYWORD;
import static compiler.language.parser.ParseType.THROW_STATEMENT;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.ThrowStatement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThrowStatementRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(THROW_KEYWORD, EXPRESSION, SEMICOLON);

  public ThrowStatementRule()
  {
    super(THROW_STATEMENT, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[1];
      return new ThrowStatement(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
