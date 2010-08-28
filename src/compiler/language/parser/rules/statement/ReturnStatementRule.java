package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.RETURN_KEYWORD;
import static compiler.language.parser.ParseType.RETURN_STATEMENT;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.ReturnStatement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ReturnStatementRule extends Rule
{

  private static final Object[] PRODUCTION            = new Object[] {RETURN_KEYWORD,             SEMICOLON};
  private static final Object[] EXPRESSION_PRODUCTION = new Object[] {RETURN_KEYWORD, EXPRESSION, SEMICOLON};

  public ReturnStatementRule()
  {
    super(RETURN_STATEMENT, PRODUCTION, EXPRESSION_PRODUCTION);
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
      return new ReturnStatement(null, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == EXPRESSION_PRODUCTION)
    {
      Expression expression = (Expression) args[1];
      return new ReturnStatement(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
