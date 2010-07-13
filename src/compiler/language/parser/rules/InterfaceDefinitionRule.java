package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.INTERFACE_DEFINITION;
import static compiler.language.parser.ParseType.INTERFACE_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.INTERFACE_KEYWORD;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.MODIFIERS;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;

import compiler.language.ast.AccessSpecifier;
import compiler.language.ast.InterfaceDefinition;
import compiler.language.ast.Member;
import compiler.language.ast.Modifier;
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

  private static final Object[] PRODUCTION = new Object[] {ACCESS_SPECIFIER, MODIFIERS, INTERFACE_KEYWORD, NAME, INTERFACE_EXTENDS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE};
  private static final Object[] TYPE_ARGUMENTS_PRODUCTION = new Object[] {ACCESS_SPECIFIER, MODIFIERS, INTERFACE_KEYWORD, NAME, TYPE_ARGUMENTS, INTERFACE_EXTENDS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE};

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
      return new InterfaceDefinition((AccessSpecifier) args[0], (Modifier[]) args[1], (Name) args[3], new TypeArgument[0], (PointerType[]) args[4], (Member[]) args[6]);
    }
    if (types == TYPE_ARGUMENTS_PRODUCTION)
    {
      return new InterfaceDefinition((AccessSpecifier) args[0], (Modifier[]) args[1], (Name) args[3], (TypeArgument[]) args[4], (PointerType[]) args[5], (Member[]) args[7]);
    }
    throw badTypeList();
  }

}
