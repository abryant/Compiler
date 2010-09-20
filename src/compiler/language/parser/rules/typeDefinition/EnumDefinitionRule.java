package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.CLASS_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.ENUM_CONSTANTS;
import static compiler.language.parser.ParseType.ENUM_DEFINITION;
import static compiler.language.parser.ParseType.ENUM_KEYWORD;
import static compiler.language.parser.ParseType.IMPLEMENTS_CLAUSE;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.SEMICOLON;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.Member;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.typeDefinition.EnumConstant;
import compiler.language.ast.typeDefinition.EnumDefinition;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumDefinitionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(MEMBER_HEADER, ENUM_KEYWORD, NAME, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, ENUM_CONSTANTS, RBRACE);
  private static final Production MEMBERS_PRODUCTION = new Production(MEMBER_HEADER, ENUM_KEYWORD, NAME, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, ENUM_CONSTANTS, SEMICOLON, MEMBER_LIST, RBRACE);

  public EnumDefinitionRule()
  {
    super(ENUM_DEFINITION, PRODUCTION, MEMBERS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      MemberHeader header = (MemberHeader) args[0];
      Name name = (Name) args[2];
      PointerType superType = (PointerType) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> interfaces = (ParseList<PointerType>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<EnumConstant> enumConstants = (ParseList<EnumConstant>) args[6];
      return new EnumDefinition(header.getAccessSpecifier(), header.getModifiers(), name, superType, interfaces.toArray(new PointerType[0]), enumConstants.toArray(new EnumConstant[0]), new Member[0],
                                ParseInfo.combine(header.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(),
                                                  superType != null ? superType.getParseInfo() : null,
                                                  interfaces.getParseInfo(),
                                                  (ParseInfo) args[5], enumConstants.getParseInfo(), (ParseInfo) args[7]));
    }
    if (MEMBERS_PRODUCTION.equals(production))
    {
      MemberHeader header = (MemberHeader) args[0];
      Name name = (Name) args[2];
      PointerType superType = (PointerType) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> interfaces = (ParseList<PointerType>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<EnumConstant> enumConstants = (ParseList<EnumConstant>) args[6];
      @SuppressWarnings("unchecked")
      ParseList<Member> members = (ParseList<Member>) args[8];
      return new EnumDefinition(header.getAccessSpecifier(), header.getModifiers(), name, superType, interfaces.toArray(new PointerType[0]), enumConstants.toArray(new EnumConstant[0]), members.toArray(new Member[0]),
                                ParseInfo.combine(header.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(),
                                                  superType != null ? superType.getParseInfo() : null,
                                                  interfaces.getParseInfo(),
                                                  (ParseInfo) args[5], enumConstants.getParseInfo(), (ParseInfo) args[7], members.getParseInfo(), (ParseInfo) args[9]));
    }
    throw badTypeList();
  }

}
