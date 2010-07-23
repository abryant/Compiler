package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.CONSTRUCTOR;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;

import compiler.language.ast.ArgumentList;
import compiler.language.ast.Block;
import compiler.language.ast.Constructor;
import compiler.language.ast.MemberHeader;
import compiler.language.ast.Name;
import compiler.language.ast.PointerType;
import compiler.parser.Rule;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConstructorRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {MEMBER_HEADER, NAME, ARGUMENTS, THROWS_CLAUSE, BLOCK};

  public ConstructorRule()
  {
    super(CONSTRUCTOR, PRODUCTION);
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
      return new Constructor(header.getAccessSpecifier(), header.getModifiers(), (Name) args[1], (ArgumentList) args[2], (PointerType[]) args[3], (Block) args[4]);
    }
    throw badTypeList();
  }

}
