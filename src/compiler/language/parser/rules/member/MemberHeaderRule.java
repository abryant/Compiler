package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.MODIFIERS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.AccessSpecifier;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.misc.Modifier;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberHeaderRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(ACCESS_SPECIFIER, MODIFIERS);
  private static final Production NO_MODIFIERS_PRODUCTION = new Production(ACCESS_SPECIFIER);

  public MemberHeaderRule()
  {
    super(MEMBER_HEADER, PRODUCTION, NO_MODIFIERS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
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
    if (NO_MODIFIERS_PRODUCTION.equals(production))
    {
      AccessSpecifier access = (AccessSpecifier) args[0];
      ParseInfo parseInfo = access != null ? access.getParseInfo() : null;
      return new MemberHeader(access, new Modifier[0], parseInfo);
    }
    throw badTypeList();
  }

}
