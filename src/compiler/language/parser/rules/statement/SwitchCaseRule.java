package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.CASE_KEYWORD;
import static compiler.language.parser.ParseType.COLON;
import static compiler.language.parser.ParseType.DEFAULT_KEYWORD;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.STATEMENTS;
import static compiler.language.parser.ParseType.SWITCH_CASE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.Statement;
import compiler.language.ast.statement.SwitchCase;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SwitchCaseRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION                    = new Production(CASE_KEYWORD,    EXPRESSION, COLON);
  private static final Production STATEMENTS_PRODUCTION         = new Production(CASE_KEYWORD,    EXPRESSION, COLON, STATEMENTS);
  private static final Production DEFAULT_PRODUCTION            = new Production(DEFAULT_KEYWORD,             COLON);
  private static final Production DEFAULT_STATEMENTS_PRODUCTION = new Production(DEFAULT_KEYWORD,             COLON, STATEMENTS);

  public SwitchCaseRule()
  {
    super(SWITCH_CASE, PRODUCTION, STATEMENTS_PRODUCTION, DEFAULT_PRODUCTION, DEFAULT_STATEMENTS_PRODUCTION);
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
      return new SwitchCase(expression, new Statement[0],
                            ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    if (STATEMENTS_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Statement> statements = (ParseList<Statement>) args[3];
      return new SwitchCase(expression, statements.toArray(new Statement[0]),
                            ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2], statements.getParseInfo()));
    }
    if (DEFAULT_PRODUCTION.equals(production))
    {
      return new SwitchCase(null, new Statement[0],
                            ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (DEFAULT_STATEMENTS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Statement> statements = (ParseList<Statement>) args[2];
      return new SwitchCase(null, statements.toArray(new Statement[0]),
                            ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], statements.getParseInfo()));
    }
    throw badTypeList();
  }

}
