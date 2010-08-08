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

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.member.Method;
import compiler.language.ast.misc.ArgumentList;
import compiler.language.ast.statement.Block;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.Rule;

/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MethodRule extends Rule
{
  // methods can either be a declaration or a definition
  // declarations end in a semicolon, definitions end in a block

  // without type arguments
  private static final Object[] DECLARATION_PRODUCTION = new Object[] {MEMBER_HEADER, TYPE, NAME, ARGUMENTS, THROWS_CLAUSE, SEMICOLON};
  private static final Object[] DEFINITION_PRODUCTION  = new Object[] {MEMBER_HEADER, TYPE, NAME, ARGUMENTS, THROWS_CLAUSE, BLOCK};

  // with type arguments
  private static final Object[] TYPE_ARGUMENTS_DECLARATION_PRODUCTION = new Object[] {MEMBER_HEADER, TYPE_ARGUMENTS, TYPE, NAME, ARGUMENTS, THROWS_CLAUSE, SEMICOLON};
  private static final Object[] TYPE_ARGUMENTS_DEFINITION_PRODUCTION  = new Object[] {MEMBER_HEADER, TYPE_ARGUMENTS, TYPE, NAME, ARGUMENTS, THROWS_CLAUSE, BLOCK};

  public MethodRule()
  {
    super(METHOD, DECLARATION_PRODUCTION,                DEFINITION_PRODUCTION,
                  TYPE_ARGUMENTS_DECLARATION_PRODUCTION, TYPE_ARGUMENTS_DEFINITION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == DECLARATION_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      ArgumentList arguments = (ArgumentList) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> throwsClause = (ParseList<PointerType>) args[4];
      return new Method(header.getAccessSpecifier(), header.getModifiers(), new TypeArgument[0], type, name, arguments, throwsClause.toArray(new PointerType[0]), null,
                        ParseInfo.combine(header.getParseInfo(), type.getParseInfo(), name.getParseInfo(), arguments.getParseInfo(), throwsClause.getParseInfo(), (ParseInfo) args[5]));
    }
    if (types == DEFINITION_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      Type type = (Type) args[1];
      Name name = (Name) args[2];
      ArgumentList arguments = (ArgumentList) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> throwsClause = (ParseList<PointerType>) args[4];
      Block block = (Block) args[5];
      return new Method(header.getAccessSpecifier(), header.getModifiers(), new TypeArgument[0], type, name, arguments, throwsClause.toArray(new PointerType[0]), block,
                        ParseInfo.combine(header.getParseInfo(), type.getParseInfo(), name.getParseInfo(), arguments.getParseInfo(), throwsClause.getParseInfo(), block.getParseInfo()));
    }
    if (types == TYPE_ARGUMENTS_DECLARATION_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> typeArguments = (ParseList<TypeArgument>) args[1];
      Type type = (Type) args[2];
      Name name = (Name) args[3];
      ArgumentList arguments = (ArgumentList) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> throwsClause = (ParseList<PointerType>) args[5];
      return new Method(header.getAccessSpecifier(), header.getModifiers(), typeArguments.toArray(new TypeArgument[0]), type, name, arguments, throwsClause.toArray(new PointerType[0]), null,
                        ParseInfo.combine(header.getParseInfo(), typeArguments.getParseInfo(), type.getParseInfo(), name.getParseInfo(), arguments.getParseInfo(), throwsClause.getParseInfo(), (ParseInfo) args[6]));
    }
    if (types == TYPE_ARGUMENTS_DEFINITION_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> typeArguments = (ParseList<TypeArgument>) args[1];
      Type type = (Type) args[2];
      Name name = (Name) args[3];
      ArgumentList arguments = (ArgumentList) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> throwsClause = (ParseList<PointerType>) args[5];
      Block block = (Block) args[6];
      return new Method(header.getAccessSpecifier(), header.getModifiers(), typeArguments.toArray(new TypeArgument[0]), type, name, arguments, throwsClause.toArray(new PointerType[0]), block,
                        ParseInfo.combine(header.getParseInfo(), typeArguments.getParseInfo(), type.getParseInfo(), name.getParseInfo(), arguments.getParseInfo(), throwsClause.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}
