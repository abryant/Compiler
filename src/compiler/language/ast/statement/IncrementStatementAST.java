package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.AssigneeAST;

/*
 * Created on 14 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class IncrementStatementAST extends StatementAST
{

  private AssigneeAST assignee;

  /**
   * Creates a new IncrementStatementAST to increment the specified AssigneeAST
   * @param assignee - the AssigneeAST to increment
   * @param parseInfo - the parsing information
   */
  public IncrementStatementAST(AssigneeAST assignee, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.assignee = assignee;
  }

  /**
   * @return the assignee
   */
  public AssigneeAST getAssignee()
  {
    return assignee;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return assignee + "++;";
  }
}
