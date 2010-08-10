package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.STATIC_INITIALIZER;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.member.Modifier;
import compiler.language.ast.member.ModifierType;
import compiler.language.ast.member.StaticInitializer;
import compiler.language.ast.statement.Block;
import compiler.language.parser.LanguageParseException;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StaticInitializerRule extends Rule
{
  // this uses MEMBER_HEADER instead of STATIC_KEYWORD because using STATIC_KEYWORD causes shift-reduce conflicts
  // the match() method checks that there is one modifier (static) and that there are no access specifiers
  private static final Object[] PRODUCTION = new Object[] {MEMBER_HEADER, BLOCK};

  public StaticInitializerRule()
  {
    super(STATIC_INITIALIZER, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      if (header.getAccessSpecifier() != null)
      {
        throw new LanguageParseException("A static initializer cannot have an access specifier.", header.getAccessSpecifier().getParseInfo());
      }
      Modifier[] modifiers = header.getModifiers();
      Block block = (Block) args[1];
      if (modifiers.length == 0)
      {
        throw new LanguageParseException("A static initializer must have a \"static\" modifier", block.getParseInfo());
      }
      if (modifiers.length > 1 || modifiers[0].getType() != ModifierType.STATIC)
      {
        ParseInfo[] modifierInfo = new ParseInfo[modifiers.length];
        for (int i = 0; i < modifiers.length; i++)
        {
          modifierInfo[i] = modifiers[i].getParseInfo();
        }
        throw new LanguageParseException("A static initializer can only have one modifier, and it must be \"static\"", ParseInfo.combine(modifierInfo));
      }
      return new StaticInitializer(block, ParseInfo.combine(header.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}
