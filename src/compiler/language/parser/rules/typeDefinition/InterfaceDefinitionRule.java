package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.INTERFACE_DEFINITION;
import static compiler.language.parser.ParseType.INTERFACE_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.INTERFACE_KEYWORD;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.Member;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeArgument;
import compiler.language.ast.typeDefinition.InterfaceDefinition;
import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class InterfaceDefinitionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {MEMBER_HEADER, INTERFACE_KEYWORD, NAME, INTERFACE_EXTENDS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE};
  private static final Object[] TYPE_ARGUMENTS_PRODUCTION = new Object[] {MEMBER_HEADER, INTERFACE_KEYWORD, NAME, TYPE_ARGUMENTS, INTERFACE_EXTENDS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE};

  public InterfaceDefinitionRule()
  {
    super(INTERFACE_DEFINITION, PRODUCTION);
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
      Name name = (Name) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superInterfaces = (ParseList<PointerType>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<Member> members = (ParseList<Member>) args[5];
      return new InterfaceDefinition(header.getAccessSpecifier(), header.getModifiers(), name, new TypeArgument[0], superInterfaces.toArray(new PointerType[0]), members.toArray(new Member[0]),
                                     ParseInfo.combine(header.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(), superInterfaces.getParseInfo(),
                                                       (ParseInfo) args[4], members.getParseInfo(), (ParseInfo) args[6]));
    }
    if (types == TYPE_ARGUMENTS_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      Name name = (Name) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> typeArguments = (ParseList<TypeArgument>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superInterfaces = (ParseList<PointerType>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<Member> members = (ParseList<Member>) args[6];
      return new InterfaceDefinition(header.getAccessSpecifier(), header.getModifiers(), name, typeArguments.toArray(new TypeArgument[0]), superInterfaces.toArray(new PointerType[0]), members.toArray(new Member[0]),
                                     ParseInfo.combine(header.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(), typeArguments.getParseInfo(), superInterfaces.getParseInfo(),
                                                       (ParseInfo) args[5], members.getParseInfo(), (ParseInfo) args[7]));
    }
    throw badTypeList();
  }

}
