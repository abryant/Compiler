package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.CLASS_DEFINITION;
import static compiler.language.parser.ParseType.CLASS_KEYWORD;
import static compiler.language.parser.ParseType.EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.IMPLEMENTS_CLAUSE;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.MODIFIERS;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.RBRACE;

import compiler.language.ast.AccessSpecifier;
import compiler.language.ast.ClassDefinition;
import compiler.language.ast.Member;
import compiler.language.ast.Modifier;
import compiler.language.ast.Name;
import compiler.language.ast.ReferenceType;
import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClassDefinitionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {ACCESS_SPECIFIER, MODIFIERS, CLASS_KEYWORD, NAME, EXTENDS_CLAUSE, IMPLEMENTS_CLAUSE, LBRACE, MEMBER_LIST, RBRACE};

  public ClassDefinitionRule()
  {
    super(CLASS_DEFINITION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new ClassDefinition((AccessSpecifier) args[0], (Modifier[]) args[1], (Name) args[3], (ReferenceType) args[4], (ReferenceType[]) args[5], (Member[]) args[7]);
    }
    throw badTypeList();
  }

}
