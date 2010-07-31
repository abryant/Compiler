package compiler.language.parser.rules;

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

import compiler.language.ast.member.Member;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.typeDefinition.EnumConstant;
import compiler.language.ast.typeDefinition.EnumDefinition;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumDefinitionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {MEMBER_HEADER, ENUM_KEYWORD, NAME, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, ENUM_CONSTANTS, RBRACE};
  private static final Object[] MEMBERS_PRODUCTION = new Object[] {MEMBER_HEADER, ENUM_KEYWORD, NAME, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, ENUM_CONSTANTS, SEMICOLON, MEMBER_LIST, RBRACE};

  public EnumDefinitionRule()
  {
    super(ENUM_DEFINITION, PRODUCTION, MEMBERS_PRODUCTION);
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
      return new EnumDefinition(header.getAccessSpecifier(), header.getModifiers(), (Name) args[2], (PointerType) args[3], (PointerType[]) args[4], (EnumConstant[]) args[6], new Member[0]);
    }
    if (types == MEMBERS_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      return new EnumDefinition(header.getAccessSpecifier(), header.getModifiers(), (Name) args[2], (PointerType) args[3], (PointerType[]) args[4], (EnumConstant[]) args[6], (Member[]) args[8]);
    }
    throw badTypeList();
  }

}
