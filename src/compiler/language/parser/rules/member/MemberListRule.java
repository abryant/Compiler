package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.MEMBER;
import static compiler.language.parser.ParseType.MEMBER_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.Member;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<Member>(null);
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Member> list = (ParseList<Member>) args[0];
      Member member = (Member) args[1];
      list.addLast(member, ParseInfo.combine(list.getParseInfo(), member.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
