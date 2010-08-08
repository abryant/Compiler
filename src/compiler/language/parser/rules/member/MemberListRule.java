package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.MEMBER;
import static compiler.language.parser.ParseType.MEMBER_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.Member;
import compiler.parser.Rule;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberListRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {MEMBER_LIST, MEMBER};

  public MemberListRule()
  {
    super(MEMBER_LIST, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new ParseList<Member>(null);
    }
    if (types == PRODUCTION)
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
