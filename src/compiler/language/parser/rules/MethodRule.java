package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.METHOD;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;

import compiler.language.ast.ArgumentList;
import compiler.language.ast.Block;
import compiler.language.ast.MemberHeader;
import compiler.language.ast.Method;
import compiler.language.ast.Name;
import compiler.language.ast.PointerType;
import compiler.language.ast.Type;
import compiler.language.ast.TypeArgument;
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
      return new Method(header.getAccessSpecifier(), header.getModifiers(), new TypeArgument[0], (Type) args[1], (Name) args[2], (ArgumentList) args[3], (PointerType[]) args[4], null);
    }
    if (types == DEFINITION_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      return new Method(header.getAccessSpecifier(), header.getModifiers(), new TypeArgument[0], (Type) args[1], (Name) args[2], (ArgumentList) args[3], (PointerType[]) args[4], (Block) args[5]);
    }
    if (types == TYPE_ARGUMENTS_DECLARATION_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      return new Method(header.getAccessSpecifier(), header.getModifiers(), (TypeArgument[]) args[1], (Type) args[2], (Name) args[3], (ArgumentList) args[4], (PointerType[]) args[5], null);
    }
    if (types == TYPE_ARGUMENTS_DEFINITION_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      return new Method(header.getAccessSpecifier(), header.getModifiers(), (TypeArgument[]) args[1], (Type) args[2], (Name) args[3], (ArgumentList) args[4], (PointerType[]) args[5], (Block) args[6]);
    }
    throw badTypeList();
  }

}
