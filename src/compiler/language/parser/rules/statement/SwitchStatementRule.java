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
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SwitchStatementRule extends Rule
{

  private static final Object[] PRODUCTION               = new Object[] {SWITCH_KEYWORD, EXPRESSION, LBRACE, SWITCH_CASES, RBRACE};
  private static final Object[] NO_EXPRESSION_PRODUCTION = new Object[] {SWITCH_KEYWORD,             LBRACE, SWITCH_CASES, RBRACE};

  public SwitchStatementRule()
  {
    super(SWITCH_STATEMENT, PRODUCTION, NO_EXPRESSION_PRODUCTION);
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
      @SuppressWarnings("unchecked")
      ParseList<SwitchCase> cases = (ParseList<SwitchCase>) args[3];
      return new SwitchStatement(expression, cases.toArray(new SwitchCase[0]),
                                 ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(),
                                                   (ParseInfo) args[2], cases.getParseInfo(), (ParseInfo) args[4]));
    }
    if (types == NO_EXPRESSION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<SwitchCase> cases = (ParseList<SwitchCase>) args[2];
      return new SwitchStatement(null, cases.toArray(new SwitchCase[0]),
                                 ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], cases.getParseInfo(), (ParseInfo) args[3]));
    }
    throw badTypeList();
  }

}
