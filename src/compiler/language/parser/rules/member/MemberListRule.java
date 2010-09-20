package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.MEMBER;
import static compiler.language.parser.ParseType.MEMBER_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.Member;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production();
  private static final Production PRODUCTION = new Production(MEMBER_LIST, MEMBER);

  public MemberListRule()
  {
    super(MEMBER_LIST, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
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
