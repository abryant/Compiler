package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.MEMBER;
import static compiler.language.parser.ParseType.MEMBER_LIST;

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
      return new Member[0];
    }
    if (types == PRODUCTION)
    {
      Member[] oldList = (Member[]) args[0];
      Member[] newList = new Member[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (Member) args[1];
      return newList;
    }
    throw badTypeList();
  }

}
