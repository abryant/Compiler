package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SwitchStatement extends Statement
{

  private Expression switchExpression;
  private SwitchCase[] cases;

  /**
   * Creates a new SwitchStatement with the specified switch expression and list of cases.
   * @param switchExpression - the expression to switch on
   * @param cases - the list of switch cases in this switch statement
   * @param parseInfo - the parsing information
   */
  public SwitchStatement(Expression switchExpression, SwitchCase[] cases, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.switchExpression = switchExpression;
    this.cases = cases;
  }

  /**
   * @return the switchExpression
   */
  public Expression getSwitchExpression()
  {
    return switchExpression;
  }

  /**
   * @return the cases
   */
  public SwitchCase[] getCases()
  {
    return cases;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("switch");
    if (switchExpression != null)
    {
      buffer.append(" ");
      buffer.append(switchExpression);
    }
    buffer.append("\n{");
    for (SwitchCase switchCase : cases)
    {
      buffer.append("\n");
      buffer.append(switchCase);
    }
    buffer.append("\n}");
    return buffer.toString();
  }
}
