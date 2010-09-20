package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.CONSTRUCTOR;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.Constructor;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.misc.ArgumentList;
import compiler.language.ast.statement.Block;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ConstructorRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(MEMBER_HEADER, NAME, ARGUMENTS, THROWS_CLAUSE, BLOCK);

  @SuppressWarnings("unchecked")
  public ConstructorRule()
  {
    super(CONSTRUCTOR, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      MemberHeader header = (MemberHeader) args[0];
      Name name = (Name) args[1];
      ArgumentList arguments = (ArgumentList) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> throwsClause = (ParseList<PointerType>) args[3];
      Block block = (Block) args[4];
      return new Constructor(header.getAccessSpecifier(), header.getModifiers(),
                             name, arguments, throwsClause.toArray(new PointerType[0]), block,
                             ParseInfo.combine(header.getParseInfo(), name.getParseInfo(), arguments.getParseInfo(), throwsClause.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}
