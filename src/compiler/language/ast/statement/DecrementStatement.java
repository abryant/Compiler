package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Assignee;

/*
 * Created on 14 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DecrementStatement extends Statement
{

  private Assignee assignee;

  /**
   * Creates a new DecrementStatement to decrement the specified Assignee
   * @param assignee - the Assignee to decrement
   * @param parseInfo - the parsing information
   */
  public DecrementStatement(Assignee assignee, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.assignee = assignee;
  }

  /**
   * @return the assignee
   */
  public Assignee getAssignee()
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
