package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.CLASS_DEFINITION;
import static compiler.language.parser.ParseType.CLASS_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.CLASS_KEYWORD;
import static compiler.language.parser.ParseType.IMPLEMENTS_CLAUSE;
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
import compiler.language.ast.typeDefinition.ClassDefinition;
import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClassDefinitionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {MEMBER_HEADER, CLASS_KEYWORD, NAME, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE};
  private static final Object[] TYPE_ARGUMENTS_PRODUCTION = new Object[] {MEMBER_HEADER, CLASS_KEYWORD, NAME, TYPE_ARGUMENTS, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE};

  public ClassDefinitionRule()
  {
    super(CLASS_DEFINITION, PRODUCTION, TYPE_ARGUMENTS_PRODUCTION);
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
      PointerType superType = (PointerType) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> interfaces = (ParseList<PointerType>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<Member> members = (ParseList<Member>) args[6];
      return new ClassDefinition(header.getAccessSpecifier(), header.getModifiers(), name, new TypeArgument[0], superType, interfaces.toArray(new PointerType[0]), members.toArray(new Member[0]),
                                 ParseInfo.combine(header.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(),
                                                   superType != null ? superType.getParseInfo() : null,
                                                   interfaces.getParseInfo(),
                                                   (ParseInfo) args[5], members.getParseInfo(), (ParseInfo) args[7]));
    }
    if (types == TYPE_ARGUMENTS_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      Name name = (Name) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> typeArguments = (ParseList<TypeArgument>) args[3];
      PointerType superType = (PointerType) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> interfaces = (ParseList<PointerType>) args[5];
      @SuppressWarnings("unchecked")
      ParseList<Member> members = (ParseList<Member>) args[7];
      return new ClassDefinition(header.getAccessSpecifier(), header.getModifiers(), name, typeArguments.toArray(new TypeArgument[0]), superType, interfaces.toArray(new PointerType[0]), members.toArray(new Member[0]),
                                 ParseInfo.combine(header.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(), typeArguments.getParseInfo(),
                                                   superType != null ? superType.getParseInfo() : null,
                                                   interfaces.getParseInfo(),
                                                   (ParseInfo) args[6], members.getParseInfo(), (ParseInfo) args[8]));
    }
    throw badTypeList();
  }

}
