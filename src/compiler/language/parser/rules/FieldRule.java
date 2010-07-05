package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.FIELD;
import static compiler.language.parser.ParseType.MODIFIERS;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.AccessSpecifier;
import compiler.language.ast.Assignee;
import compiler.language.ast.Expression;
import compiler.language.ast.Field;
import compiler.language.ast.Modifier;
import compiler.language.ast.Type;
import compiler.parser.Rule;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldRule extends Rule
{

  private static final Object[] DECLARE_PRODUCTION = new Object[] {ACCESS_SPECIFIER, MODIFIERS, TYPE, ASSIGNEE_LIST, SEMICOLON};
  private static final Object[] ASSIGN_PRODUCTION = new Object[] {ACCESS_SPECIFIER, MODIFIERS, TYPE, ASSIGNEE_LIST, EQUALS, EXPRESSION, SEMICOLON};

  public FieldRule()
  {
    super(FIELD, DECLARE_PRODUCTION, ASSIGN_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == DECLARE_PRODUCTION)
    {
      return new Field((AccessSpecifier) args[0], (Modifier[]) args[1], (Type) args[2], (Assignee[]) args[3]);
    }
    if (types == ASSIGN_PRODUCTION)
    {
      return new Field((AccessSpecifier) args[0], (Modifier[]) args[1], (Type) args[2], (Assignee[]) args[3], (Expression) args[5]);
    }
    throw badTypeList();
  }

}
