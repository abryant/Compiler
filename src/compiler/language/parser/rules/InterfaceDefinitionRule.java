package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.INTERFACE_DEFINITION;
import static compiler.language.parser.ParseType.INTERFACE_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.INTERFACE_KEYWORD;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;

import compiler.language.ast.InterfaceDefinition;
import compiler.language.ast.Member;
import compiler.language.ast.MemberHeader;
import compiler.language.ast.Name;
import compiler.language.ast.PointerType;
import compiler.language.ast.TypeArgument;
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
      return new InterfaceDefinition(header.getAccessSpecifier(), header.getModifiers(), (Name) args[2], new TypeArgument[0], (PointerType[]) args[3], (Member[]) args[5]);
    }
    if (types == TYPE_ARGUMENTS_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      return new InterfaceDefinition(header.getAccessSpecifier(), header.getModifiers(), (Name) args[2], (TypeArgument[]) args[3], (PointerType[]) args[4], (Member[]) args[6]);
    }
    throw badTypeList();
  }

}
