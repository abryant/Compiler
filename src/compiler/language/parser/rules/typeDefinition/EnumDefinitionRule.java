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
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.member.MemberHeaderAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.typeDefinition.EnumConstantAST;
import compiler.language.ast.typeDefinition.EnumDefinitionAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class EnumDefinitionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(MEMBER_HEADER, ENUM_KEYWORD, NAME, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, ENUM_CONSTANTS, RBRACE);
  private static final Production<ParseType> MEMBERS_PRODUCTION = new Production<ParseType>(MEMBER_HEADER, ENUM_KEYWORD, NAME, CLASS_EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, ENUM_CONSTANTS, SEMICOLON, MEMBER_LIST, RBRACE);

  @SuppressWarnings("unchecked")
  public EnumDefinitionRule()
  {
    super(ENUM_DEFINITION, PRODUCTION, MEMBERS_PRODUCTION);
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
      NameAST name = (NameAST) args[2];
      PointerTypeAST superType = (PointerTypeAST) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> interfaces = (ParseList<PointerTypeAST>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<EnumConstantAST> enumConstants = (ParseList<EnumConstantAST>) args[6];
      return new EnumDefinitionAST(header.getAccessSpecifier(), header.getModifiers(), name, superType, interfaces.toArray(new PointerTypeAST[0]), enumConstants.toArray(new EnumConstantAST[0]), new MemberAST[0],
                                LexicalPhrase.combine(header.getLexicalPhrase(), (LexicalPhrase) args[1], name.getLexicalPhrase(),
                                                  superType != null ? superType.getLexicalPhrase() : null,
                                                  interfaces.getLexicalPhrase(),
                                                  (LexicalPhrase) args[5], enumConstants.getLexicalPhrase(), (LexicalPhrase) args[7]));
    }
    if (MEMBERS_PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      NameAST name = (NameAST) args[2];
      PointerTypeAST superType = (PointerTypeAST) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> interfaces = (ParseList<PointerTypeAST>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<EnumConstantAST> enumConstants = (ParseList<EnumConstantAST>) args[6];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[8];
      return new EnumDefinitionAST(header.getAccessSpecifier(), header.getModifiers(), name, superType, interfaces.toArray(new PointerTypeAST[0]), enumConstants.toArray(new EnumConstantAST[0]), members.toArray(new MemberAST[0]),
                                LexicalPhrase.combine(header.getLexicalPhrase(), (LexicalPhrase) args[1], name.getLexicalPhrase(),
                                                  superType != null ? superType.getLexicalPhrase() : null,
                                                  interfaces.getLexicalPhrase(),
                                                  (LexicalPhrase) args[5], enumConstants.getLexicalPhrase(), (LexicalPhrase) args[7], members.getLexicalPhrase(), (LexicalPhrase) args[9]));
    }
    throw badTypeList();
  }

}
