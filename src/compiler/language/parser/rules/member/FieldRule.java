package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.FIELD;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.member.Field;
import compiler.language.ast.member.MemberHeader;
import compiler.language.ast.misc.DeclarationAssignee;
import compiler.language.ast.type.Type;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class FieldRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> DECLARE_PRODUCTION = new Production<ParseType>(MEMBER_HEADER, TYPE, DECLARATION_ASSIGNEE_LIST, SEMICOLON);
  private static final Production<ParseType> ASSIGN_PRODUCTION = new Production<ParseType>(MEMBER_HEADER, TYPE, DECLARATION_ASSIGNEE_LIST, EQUALS, EXPRESSION, SEMICOLON);

  @SuppressWarnings("unchecked")
  public FieldRule()
  {
    super(FIELD, DECLARE_PRODUCTION, ASSIGN_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (DECLARE_PRODUCTION.equals(production))
    {
      MemberHeader header = (MemberHeader) args[0];
      Type type = (Type) args[1];
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssignee> assignees = (ParseList<DeclarationAssignee>) args[2];
      return new Field(header.getAccessSpecifier(), header.getModifiers(), type, assignees.toArray(new DeclarationAssignee[0]),
                       ParseInfo.combine(header.getParseInfo(), type.getParseInfo(), assignees.getParseInfo(), (ParseInfo) args[3]));
    }
    if (ASSIGN_PRODUCTION.equals(production))
    {
      MemberHeader header = (MemberHeader) args[0];
      Type type = (Type) args[1];
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssignee> assignees = (ParseList<DeclarationAssignee>) args[2];
      Expression expression = (Expression) args[4];
      return new Field(header.getAccessSpecifier(), header.getModifiers(), type, assignees.toArray(new DeclarationAssignee[0]), expression,
                       ParseInfo.combine(header.getParseInfo(), type.getParseInfo(), assignees.getParseInfo(), (ParseInfo) args[3], expression.getParseInfo(), (ParseInfo) args[5]));
    }
    throw badTypeList();
  }

}
