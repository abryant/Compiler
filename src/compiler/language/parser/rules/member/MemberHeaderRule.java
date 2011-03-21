package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.MODIFIERS;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.AccessSpecifierAST;
import compiler.language.ast.member.MemberHeaderAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class MemberHeaderRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(ACCESS_SPECIFIER, MODIFIERS);
  private static final Production<ParseType> NO_MODIFIERS_PRODUCTION = new Production<ParseType>(ACCESS_SPECIFIER);

  @SuppressWarnings("unchecked")
  public MemberHeaderRule()
  {
    super(MEMBER_HEADER, PRODUCTION, NO_MODIFIERS_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      AccessSpecifierAST access = (AccessSpecifierAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<ModifierAST> modifiers = (ParseList<ModifierAST>) args[1];
      ParseInfo parseInfo = null;
      if (access == null)
      {
        parseInfo = modifiers.getParseInfo();
      }
      else
      {
        parseInfo = ParseInfo.combine(access.getParseInfo(), modifiers.getParseInfo());
      }
      return new MemberHeaderAST(access, modifiers.toArray(new ModifierAST[0]), parseInfo);
    }
    if (NO_MODIFIERS_PRODUCTION.equals(production))
    {
      AccessSpecifierAST access = (AccessSpecifierAST) args[0];
      ParseInfo parseInfo = access != null ? access.getParseInfo() : null;
      return new MemberHeaderAST(access, new ModifierAST[0], parseInfo);
    }
    throw badTypeList();
  }

}
