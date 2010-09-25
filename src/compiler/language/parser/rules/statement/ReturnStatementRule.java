package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.RETURN_KEYWORD;
import static compiler.language.parser.ParseType.RETURN_STATEMENT;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.statement.ReturnStatementAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ReturnStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION            = new Production<ParseType>(RETURN_KEYWORD,             SEMICOLON);
  private static final Production<ParseType> EXPRESSION_PRODUCTION = new Production<ParseType>(RETURN_KEYWORD, EXPRESSION, SEMICOLON);

  @SuppressWarnings("unchecked")
  public ReturnStatementRule()
  {
    super(RETURN_STATEMENT, PRODUCTION, EXPRESSION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new ReturnStatementAST(null, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (EXPRESSION_PRODUCTION.equals(production))
    {
      ExpressionAST expression = (ExpressionAST) args[1];
      return new ReturnStatementAST(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
