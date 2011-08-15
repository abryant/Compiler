package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.FIELD;
import static compiler.language.parser.ParseType.MEMBER_HEADER;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.TYPE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.member.FieldAST;
import compiler.language.ast.member.MemberHeaderAST;
import compiler.language.ast.misc.DeclarationAssigneeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (DECLARE_PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      TypeAST type = (TypeAST) args[1];
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssigneeAST> assignees = (ParseList<DeclarationAssigneeAST>) args[2];
      return new FieldAST(header.getAccessSpecifier(), header.getModifiers(), type, assignees.toArray(new DeclarationAssigneeAST[0]),
                       LexicalPhrase.combine(header.getLexicalPhrase(), type.getLexicalPhrase(), assignees.getLexicalPhrase(), (LexicalPhrase) args[3]));
    }
    if (ASSIGN_PRODUCTION.equals(production))
    {
      MemberHeaderAST header = (MemberHeaderAST) args[0];
      TypeAST type = (TypeAST) args[1];
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssigneeAST> assignees = (ParseList<DeclarationAssigneeAST>) args[2];
      ExpressionAST expression = (ExpressionAST) args[4];
      return new FieldAST(header.getAccessSpecifier(), header.getModifiers(), type, assignees.toArray(new DeclarationAssigneeAST[0]), expression,
                       LexicalPhrase.combine(header.getLexicalPhrase(), type.getLexicalPhrase(), assignees.getLexicalPhrase(), (LexicalPhrase) args[3], expression.getLexicalPhrase(), (LexicalPhrase) args[5]));
    }
    throw badTypeList();
  }

}
