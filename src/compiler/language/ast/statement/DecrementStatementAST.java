package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.AssigneeAST;

/*
 * Created on 14 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DecrementStatementAST extends StatementAST
{

  private AssigneeAST assignee;

  /**
   * Creates a new DecrementStatementAST to decrement the specified AssigneeAST
   * @param assignee - the AssigneeAST to decrement
   * @param parseInfo - the parsing information
   */
  public DecrementStatementAST(AssigneeAST assignee, ParseInfo parseInfo)
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
    return assignee + "--;";
  }
}
