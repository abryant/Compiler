package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.STATIC_INITIALIZER;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.member.MemberHeaderAST;
import compiler.language.ast.member.StaticInitializerAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.ModifierTypeAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.parser.LanguageParseException;
import compiler.language.parser.ParseType;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      if (header.getAccessSpecifier() != null)
      {
        throw new LanguageParseException("A static initializer cannot have an access specifier.", header.getAccessSpecifier().getLexicalPhrase());
      }
      ModifierAST[] modifiers = header.getModifiers();
      BlockAST block = (BlockAST) args[1];
      if (modifiers.length == 0)
      {
        throw new LanguageParseException("A static initializer must have a \"static\" modifier", block.getLexicalPhrase());
      }
      if (modifiers.length > 1 || modifiers[0].getType() != ModifierTypeAST.STATIC)
      {
        LexicalPhrase[] modifierInfo = new LexicalPhrase[modifiers.length];
        for (int i = 0; i < modifiers.length; i++)
        {
          modifierInfo[i] = modifiers[i].getLexicalPhrase();
        }
        throw new LanguageParseException("A static initializer can only have one modifier, and it must be \"static\"", LexicalPhrase.combine(modifierInfo));
      }
      return new StaticInitializerAST(block, LexicalPhrase.combine(header.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
