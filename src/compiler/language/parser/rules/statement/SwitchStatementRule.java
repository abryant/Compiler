package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.SWITCH_CASES;
import static compiler.language.parser.ParseType.SWITCH_KEYWORD;
import static compiler.language.parser.ParseType.SWITCH_STATEMENT;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.statement.SwitchCase;
import compiler.language.ast.statement.SwitchStatement;
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
public final class SwitchStatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION               = new Production<ParseType>(SWITCH_KEYWORD, EXPRESSION, LBRACE, SWITCH_CASES, RBRACE);
  private static final Production<ParseType> NO_EXPRESSION_PRODUCTION = new Production<ParseType>(SWITCH_KEYWORD,             LBRACE, SWITCH_CASES, RBRACE);

  @SuppressWarnings("unchecked")
  public SwitchStatementRule()
  {
    super(SWITCH_STATEMENT, PRODUCTION, NO_EXPRESSION_PRODUCTION);
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
      Expression expression = (Expression) args[1];
      @SuppressWarnings("unchecked")
      ParseList<SwitchCase> cases = (ParseList<SwitchCase>) args[3];
      return new SwitchStatement(expression, cases.toArray(new SwitchCase[0]),
                                 ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(),
                                                   (ParseInfo) args[2], cases.getParseInfo(), (ParseInfo) args[4]));
    }
    if (NO_EXPRESSION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<SwitchCase> cases = (ParseList<SwitchCase>) args[2];
      return new SwitchStatement(null, cases.toArray(new SwitchCase[0]),
                                 ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], cases.getParseInfo(), (ParseInfo) args[3]));
    }
    throw badTypeList();
  }

}
