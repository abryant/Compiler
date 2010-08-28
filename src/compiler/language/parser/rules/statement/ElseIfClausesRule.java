package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ELSE_IF_CLAUSE;
import static compiler.language.parser.ParseType.ELSE_IF_CLAUSES;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.statement.ElseIfClause;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ElseIfClausesRule extends Rule
{

  private static final Object[] START_PRODUCTION        = new Object[] {};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ELSE_IF_CLAUSES, ELSE_IF_CLAUSE};

  public ElseIfClausesRule()
  {
    super(ELSE_IF_CLAUSES, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      return new ParseList<ElseIfClause>(null);
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<ElseIfClause> list = (ParseList<ElseIfClause>) args[0];
      ElseIfClause clause = (ElseIfClause) args[1];
      list.addLast(clause, ParseInfo.combine(list.getParseInfo(), clause.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
