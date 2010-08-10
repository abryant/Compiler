package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.MODIFIERS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.AccessSpecifier;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.member.Modifier;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberHeaderRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {ACCESS_SPECIFIER, MODIFIERS};
  private static final Object[] NO_MODIFIERS_PRODUCTION = new Object[] {ACCESS_SPECIFIER};

  public MemberHeaderRule()
  {
    super(MEMBER_HEADER, PRODUCTION, NO_MODIFIERS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      AccessSpecifier access = (AccessSpecifier) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Modifier> modifiers = (ParseList<Modifier>) args[1];
      ParseInfo parseInfo = null;
      if (access == null)
      {
        parseInfo = modifiers.getParseInfo();
      }
      else
      {
        parseInfo = ParseInfo.combine(access.getParseInfo(), modifiers.getParseInfo());
      }
      return new MemberHeader(access, modifiers.toArray(new Modifier[0]), parseInfo);
    }
    if (types == NO_MODIFIERS_PRODUCTION)
    {
      AccessSpecifier access = (AccessSpecifier) args[0];
      ParseInfo parseInfo = access != null ? access.getParseInfo() : null;
      return new MemberHeader(access, new Modifier[0], parseInfo);
    }
    throw badTypeList();
  }

}
