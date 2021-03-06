package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ASSIGNEE;
import static compiler.language.parser.ParseType.ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.misc.AssigneeAST;
import compiler.language.ast.misc.FieldAssigneeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class AssigneeListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  // this is a right-recursive list, so that the symbol after the list can be
  // used to decide between TypeList and AssigneeList
  private static final Production<ParseType> ASSIGNEE_END_PRODUCTION  = new Production<ParseType>(ASSIGNEE);
  private static final Production<ParseType> QNAME_END_PRODUCTION     = new Production<ParseType>(QNAME);
  private static final Production<ParseType> ASSIGNEE_LIST_PRODUCTION = new Production<ParseType>(ASSIGNEE, COMMA, ASSIGNEE_LIST);
  private static final Production<ParseType> QNAME_LIST_PRODUCTION    = new Production<ParseType>(QNAME,    COMMA, ASSIGNEE_LIST);

  @SuppressWarnings("unchecked")
  public AssigneeListRule()
  {
    super(ASSIGNEE_LIST, ASSIGNEE_END_PRODUCTION, QNAME_END_PRODUCTION, ASSIGNEE_LIST_PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (ASSIGNEE_END_PRODUCTION.equals(production))
    {
      AssigneeAST assignee = (AssigneeAST) args[0];
      return new ParseList<AssigneeAST>(assignee, assignee.getLexicalPhrase());
    }
    if (QNAME_END_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      FieldAccessExpressionAST fieldAccess = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      AssigneeAST assignee = new FieldAssigneeAST(fieldAccess, fieldAccess.getLexicalPhrase());
      return new ParseList<AssigneeAST>(assignee, assignee.getLexicalPhrase());
    }
    if (ASSIGNEE_LIST_PRODUCTION.equals(production))
    {
      AssigneeAST assignee = (AssigneeAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<AssigneeAST> list = (ParseList<AssigneeAST>) args[2];
      list.addFirst(assignee, LexicalPhrase.combine(assignee.getLexicalPhrase(), (LexicalPhrase) args[1], list.getLexicalPhrase()));
      return list;
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      FieldAccessExpressionAST fieldAccess = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      AssigneeAST assignee = new FieldAssigneeAST(fieldAccess, fieldAccess.getLexicalPhrase());
      @SuppressWarnings("unchecked")
      ParseList<AssigneeAST> list = (ParseList<AssigneeAST>) args[2];
      list.addFirst(assignee, LexicalPhrase.combine(assignee.getLexicalPhrase(), (LexicalPhrase) args[1], list.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}
