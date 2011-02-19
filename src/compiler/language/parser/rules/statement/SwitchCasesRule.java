package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.SWITCH_CASE;
import static compiler.language.parser.ParseType.SWITCH_CASES;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.SwitchCaseAST;
import compiler.language.parser.ParseType;

/*
 * Created on 28 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class SwitchCasesRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> LIST_PRODUCTION  = new Production<ParseType>(SWITCH_CASES, SWITCH_CASE);

  @SuppressWarnings("unchecked")
  public SwitchCasesRule()
  {
    super(SWITCH_CASES, EMPTY_PRODUCTION, LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<SwitchCaseAST>(null);
    }
    if (LIST_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<SwitchCaseAST> list = (ParseList<SwitchCaseAST>) args[0];
      SwitchCaseAST switchCase = (SwitchCaseAST) args[1];
      list.addLast(switchCase, ParseInfo.combine(list.getParseInfo(), switchCase.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
