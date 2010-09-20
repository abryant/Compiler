package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.SWITCH_CASE;
import static compiler.language.parser.ParseType.SWITCH_CASES;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
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
public class SwitchCasesRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production();
  private static final Production LIST_PRODUCTION  = new Production(SWITCH_CASES, SWITCH_CASE);

  public SwitchCasesRule()
  {
    super(SWITCH_CASES, EMPTY_PRODUCTION, LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<SwitchCase>(null);
    }
    if (LIST_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<SwitchCase> list = (ParseList<SwitchCase>) args[0];
      SwitchCase switchCase = (SwitchCase) args[1];
      list.addLast(switchCase, ParseInfo.combine(list.getParseInfo(), switchCase.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
