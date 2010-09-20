package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.LOCAL_DECLARATION;
import static compiler.language.parser.ParseType.MODIFIERS_NOT_SYNCHRONIZED;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.misc.DeclarationAssignee;
import compiler.language.ast.misc.Modifier;
import compiler.language.ast.statement.LocalDeclarationStatement;
import compiler.language.ast.type.Type;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 16 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class LocalDeclarationRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION                      = new Production<ParseType>(                            TYPE, DECLARATION_ASSIGNEE_LIST);
  private static final Production<ParseType> ASSIGNMENT_PRODUCTION           = new Production<ParseType>(                            TYPE, DECLARATION_ASSIGNEE_LIST, EQUALS, EXPRESSION);
  private static final Production<ParseType> MODIFIERS_PRODUCTION            = new Production<ParseType>(MODIFIERS_NOT_SYNCHRONIZED, TYPE, DECLARATION_ASSIGNEE_LIST);
  private static final Production<ParseType> MODIFIERS_ASSIGNMENT_PRODUCTION = new Production<ParseType>(MODIFIERS_NOT_SYNCHRONIZED, TYPE, DECLARATION_ASSIGNEE_LIST, EQUALS, EXPRESSION);

  @SuppressWarnings("unchecked")
  public LocalDeclarationRule()
  {
    super(LOCAL_DECLARATION, PRODUCTION, ASSIGNMENT_PRODUCTION, MODIFIERS_PRODUCTION, MODIFIERS_ASSIGNMENT_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      Type type = (Type) args[0];
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssignee> assignees = (ParseList<DeclarationAssignee>) args[1];
      return new LocalDeclarationStatement(new Modifier[0], type, assignees.toArray(new DeclarationAssignee[0]), null,
                                           ParseInfo.combine(type.getParseInfo(), assignees.getParseInfo()));
    }
    if (ASSIGNMENT_PRODUCTION.equals(production))
    {
      Type type = (Type) args[0];
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssignee> assignees = (ParseList<DeclarationAssignee>) args[1];
      Expression expression = (Expression) args[3];
      return new LocalDeclarationStatement(new Modifier[0], type, assignees.toArray(new DeclarationAssignee[0]), expression,
                                           ParseInfo.combine(type.getParseInfo(), assignees.getParseInfo(), (ParseInfo) args[2], expression.getParseInfo()));
    }
    if (MODIFIERS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Modifier> modifiers = (ParseList<Modifier>) args[0];
      Type type = (Type) args[1];
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssignee> assignees = (ParseList<DeclarationAssignee>) args[2];
      return new LocalDeclarationStatement(modifiers.toArray(new Modifier[0]), type, assignees.toArray(new DeclarationAssignee[0]), null,
                                           ParseInfo.combine(modifiers.getParseInfo(), type.getParseInfo(), assignees.getParseInfo()));
    }
    if (MODIFIERS_ASSIGNMENT_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Modifier> modifiers = (ParseList<Modifier>) args[0];
      Type type = (Type) args[1];
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssignee> assignees = (ParseList<DeclarationAssignee>) args[2];
      Expression expression = (Expression) args[4];
      return new LocalDeclarationStatement(modifiers.toArray(new Modifier[0]), type, assignees.toArray(new DeclarationAssignee[0]), expression,
                                           ParseInfo.combine(modifiers.getParseInfo(), type.getParseInfo(), assignees.getParseInfo(), (ParseInfo) args[3], expression.getParseInfo()));
    }
    throw badTypeList();
  }

}
