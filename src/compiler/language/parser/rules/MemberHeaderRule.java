package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.MODIFIERS;

import compiler.language.ast.AccessSpecifier;
import compiler.language.ast.MemberHeader;
import compiler.language.ast.Modifier;
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

  public MemberHeaderRule()
  {
    super(MEMBER_HEADER, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new MemberHeader((AccessSpecifier) args[0], (Modifier[]) args[1]);
    }
    throw badTypeList();
  }

}
