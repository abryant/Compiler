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
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.member.MemberHeaderAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class InterfaceDefinitionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(MEMBER_HEADER, INTERFACE_KEYWORD, NAME, INTERFACE_EXTENDS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE);
  private static final Production<ParseType> TYPE_ARGUMENTS_PRODUCTION = new Production<ParseType>(MEMBER_HEADER, INTERFACE_KEYWORD, NAME, TYPE_ARGUMENTS, INTERFACE_EXTENDS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE);

  @SuppressWarnings("unchecked")
  public InterfaceDefinitionRule()
  {
    super(INTERFACE_DEFINITION, PRODUCTION);
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
      NameAST name = (NameAST) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superInterfaces = (ParseList<PointerTypeAST>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[5];
      return new InterfaceDefinitionAST(header.getAccessSpecifier(), header.getModifiers(), name, new TypeArgumentAST[0], superInterfaces.toArray(new PointerTypeAST[0]), members.toArray(new MemberAST[0]),
                                     ParseInfo.combine(header.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(), superInterfaces.getParseInfo(),
                                                       (ParseInfo) args[4], members.getParseInfo(), (ParseInfo) args[6]));
    }
    if (TYPE_ARGUMENTS_PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      NameAST name = (NameAST) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superInterfaces = (ParseList<PointerTypeAST>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[6];
      return new InterfaceDefinitionAST(header.getAccessSpecifier(), header.getModifiers(), name, typeArguments.toArray(new TypeArgumentAST[0]), superInterfaces.toArray(new PointerTypeAST[0]), members.toArray(new MemberAST[0]),
                                     ParseInfo.combine(header.getParseInfo(), (ParseInfo) args[1], name.getParseInfo(), typeArguments.getParseInfo(), superInterfaces.getParseInfo(),
                                                       (ParseInfo) args[5], members.getParseInfo(), (ParseInfo) args[7]));
    }
    throw badTypeList();
  }

}
