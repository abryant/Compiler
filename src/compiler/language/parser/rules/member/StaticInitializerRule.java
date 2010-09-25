package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.STATIC_INITIALIZER;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.MemberHeaderAST;
import compiler.language.ast.member.StaticInitializerAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.ModifierTypeAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.parser.LanguageParseException;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class StaticInitializerRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  // this uses MEMBER_HEADER instead of STATIC_KEYWORD because using STATIC_KEYWORD causes shift-reduce conflicts
  // the match() method checks that there is one modifier (static) and that there are no access specifiers
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(MEMBER_HEADER, BLOCK);

  @SuppressWarnings("unchecked")
  public StaticInitializerRule()
  {
    super(STATIC_INITIALIZER, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      if (header.getAccessSpecifier() != null)
      {
        throw new LanguageParseException("A static initializer cannot have an access specifier.", header.getAccessSpecifier().getParseInfo());
      }
      ModifierAST[] modifiers = header.getModifiers();
      BlockAST block = (BlockAST) args[1];
      if (modifiers.length == 0)
      {
        throw new LanguageParseException("A static initializer must have a \"static\" modifier", block.getParseInfo());
      }
      if (modifiers.length > 1 || modifiers[0].getType() != ModifierTypeAST.STATIC)
      {
        ParseInfo[] modifierInfo = new ParseInfo[modifiers.length];
        for (int i = 0; i < modifiers.length; i++)
        {
          modifierInfo[i] = modifiers[i].getParseInfo();
        }
        throw new LanguageParseException("A static initializer can only have one modifier, and it must be \"static\"", ParseInfo.combine(modifierInfo));
      }
      return new StaticInitializerAST(block, ParseInfo.combine(header.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}
