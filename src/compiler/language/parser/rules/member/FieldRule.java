package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.FIELD;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.expression.Expression;
import compiler.language.ast.member.Field;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.misc.Assignee;
import compiler.language.ast.type.Type;
import compiler.parser.Rule;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldRule extends Rule
{

  private static final Object[] DECLARE_PRODUCTION = new Object[] {MEMBER_HEADER, TYPE, ASSIGNEE_LIST, SEMICOLON};
  private static final Object[] ASSIGN_PRODUCTION = new Object[] {MEMBER_HEADER, TYPE, ASSIGNEE_LIST, EQUALS, EXPRESSION, SEMICOLON};

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
      MemberHeader header = (MemberHeader) args[0];
      return new Field(header.getAccessSpecifier(), header.getModifiers(), (Type) args[1], (Assignee[]) args[2]);
    }
    if (types == ASSIGN_PRODUCTION)
    {
      MemberHeader header = (MemberHeader) args[0];
      return new Field(header.getAccessSpecifier(), header.getModifiers(), (Type) args[1], (Assignee[]) args[2], (Expression) args[4]);
    }
    throw badTypeList();
  }

}
