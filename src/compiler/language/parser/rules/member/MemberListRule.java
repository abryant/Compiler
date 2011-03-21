package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.MEMBER;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.MemberAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class MemberListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(MEMBER_LIST, MEMBER);

  @SuppressWarnings("unchecked")
  public MemberListRule()
  {
    super(MEMBER_LIST, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<MemberAST>(null);
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> list = (ParseList<MemberAST>) args[0];
      MemberAST member = (MemberAST) args[1];
      list.addLast(member, ParseInfo.combine(list.getParseInfo(), member.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
