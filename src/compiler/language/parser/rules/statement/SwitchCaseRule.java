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
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SwitchCaseRule extends Rule
{

  private static final Object[] PRODUCTION                    = new Object[] {CASE_KEYWORD,    EXPRESSION, COLON};
  private static final Object[] STATEMENTS_PRODUCTION         = new Object[] {CASE_KEYWORD,    EXPRESSION, COLON, STATEMENTS};
  private static final Object[] DEFAULT_PRODUCTION            = new Object[] {DEFAULT_KEYWORD,             COLON};
  private static final Object[] DEFAULT_STATEMENTS_PRODUCTION = new Object[] {DEFAULT_KEYWORD,             COLON, STATEMENTS};

  public SwitchCaseRule()
  {
    super(SWITCH_CASE, PRODUCTION, STATEMENTS_PRODUCTION, DEFAULT_PRODUCTION, DEFAULT_STATEMENTS_PRODUCTION);
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
      return new SwitchCase(expression, new Statement[0],
                            ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    if (types == STATEMENTS_PRODUCTION)
    {
      Expression expression = (Expression) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Statement> statements = (ParseList<Statement>) args[3];
      return new SwitchCase(expression, statements.toArray(new Statement[0]),
                            ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2], statements.getParseInfo()));
    }
    if (types == DEFAULT_PRODUCTION)
    {
      return new SwitchCase(null, new Statement[0],
                            ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == DEFAULT_STATEMENTS_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Statement> statements = (ParseList<Statement>) args[2];
      return new SwitchCase(null, statements.toArray(new Statement[0]),
                            ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], statements.getParseInfo()));
    }
    throw badTypeList();
  }

}
