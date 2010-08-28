package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.THROW_KEYWORD;
import static compiler.language.parser.ParseType.THROW_STATEMENT;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.ThrowStatement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThrowStatementRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {THROW_KEYWORD, EXPRESSION, SEMICOLON};

  public ThrowStatementRule()
  {
    super(THROW_STATEMENT, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      Expression expression = (Expression) args[1];
      return new ThrowStatement(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
