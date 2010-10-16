package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.METHOD;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.MemberHeaderAST;
import compiler.language.ast.member.MethodAST;
import compiler.language.ast.misc.ArgumentListAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseType;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class MethodRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  // methods can either be a declaration or a definition
  // declarations end in a semicolon, definitions end in a block

  // without type arguments
  private static final Production<ParseType> DECLARATION_PRODUCTION = new Production<ParseType>(MEMBER_HEADER, TYPE, NAME, ARGUMENTS, THROWS_CLAUSE, SEMICOLON);
  private static final Production<ParseType> DEFINITION_PRODUCTION  = new Production<ParseType>(MEMBER_HEADER, TYPE, NAME, ARGUMENTS, THROWS_CLAUSE, BLOCK);

  // with type arguments
  private static final Production<ParseType> TYPE_ARGUMENTS_DECLARATION_PRODUCTION = new Production<ParseType>(MEMBER_HEADER, TYPE_ARGUMENTS, TYPE, NAME, ARGUMENTS, THROWS_CLAUSE, SEMICOLON);
  private static final Production<ParseType> TYPE_ARGUMENTS_DEFINITION_PRODUCTION  = new Production<ParseType>(MEMBER_HEADER, TYPE_ARGUMENTS, TYPE, NAME, ARGUMENTS, THROWS_CLAUSE, BLOCK);

  @SuppressWarnings("unchecked")
  public MethodRule()
  {
    super(METHOD, DECLARATION_PRODUCTION,                DEFINITION_PRODUCTION,
                  TYPE_ARGUMENTS_DECLARATION_PRODUCTION, TYPE_ARGUMENTS_DEFINITION_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (DECLARATION_PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      ArgumentListAST arguments = (ArgumentListAST) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> throwsClause = (ParseList<PointerTypeAST>) args[4];
      return new MethodAST(header.getAccessSpecifier(), header.getModifiers(), new TypeArgumentAST[0], type, name, arguments, throwsClause.toArray(new PointerTypeAST[0]), null,
                        ParseInfo.combine(header.getParseInfo(), type.getParseInfo(), name.getParseInfo(), arguments.getParseInfo(), throwsClause.getParseInfo(), (ParseInfo) args[5]));
    }
    if (DEFINITION_PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      TypeAST type = (TypeAST) args[1];
      NameAST name = (NameAST) args[2];
      ArgumentListAST arguments = (ArgumentListAST) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> throwsClause = (ParseList<PointerTypeAST>) args[4];
      BlockAST block = (BlockAST) args[5];
      return new MethodAST(header.getAccessSpecifier(), header.getModifiers(), new TypeArgumentAST[0], type, name, arguments, throwsClause.toArray(new PointerTypeAST[0]), block,
                        ParseInfo.combine(header.getParseInfo(), type.getParseInfo(), name.getParseInfo(), arguments.getParseInfo(), throwsClause.getParseInfo(), block.getParseInfo()));
    }
    if (TYPE_ARGUMENTS_DECLARATION_PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[1];
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      ArgumentListAST arguments = (ArgumentListAST) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> throwsClause = (ParseList<PointerTypeAST>) args[5];
      return new MethodAST(header.getAccessSpecifier(), header.getModifiers(), typeArguments.toArray(new TypeArgumentAST[0]), type, name, arguments, throwsClause.toArray(new PointerTypeAST[0]), null,
                        ParseInfo.combine(header.getParseInfo(), typeArguments.getParseInfo(), type.getParseInfo(), name.getParseInfo(), arguments.getParseInfo(), throwsClause.getParseInfo(), (ParseInfo) args[6]));
    }
    if (TYPE_ARGUMENTS_DEFINITION_PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[1];
      TypeAST type = (TypeAST) args[2];
      NameAST name = (NameAST) args[3];
      ArgumentListAST arguments = (ArgumentListAST) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> throwsClause = (ParseList<PointerTypeAST>) args[5];
      BlockAST block = (BlockAST) args[6];
      return new MethodAST(header.getAccessSpecifier(), header.getModifiers(), typeArguments.toArray(new TypeArgumentAST[0]), type, name, arguments, throwsClause.toArray(new PointerTypeAST[0]), block,
                        ParseInfo.combine(header.getParseInfo(), typeArguments.getParseInfo(), type.getParseInfo(), name.getParseInfo(), arguments.getParseInfo(), throwsClause.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}
