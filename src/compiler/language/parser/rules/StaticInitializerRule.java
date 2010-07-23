package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.STATIC_INITIALIZER;

import compiler.language.ast.Block;
import compiler.language.ast.MemberHeader;
import compiler.language.ast.Modifier;
import compiler.language.ast.StaticInitializer;
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
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      if (header.getAccessSpecifier() != null)
      {
        throw new IllegalStateException("A static initializer cannot have an access specifier.");
      }
      Modifier[] modifiers = header.getModifiers();
      if (modifiers.length != 1 || modifiers[0] != Modifier.STATIC)
      {
        throw new IllegalStateException("A static initializer must have exactly one modifier: \"static\"");
      }
      return new StaticInitializer((Block) args[1]);
    }
    throw badTypeList();
  }

}
