package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SwitchCase
{

  private Expression caseExpression; // null if this SwitchCase is actually a default case
  private Statement[] statements;

  private ParseInfo parseInfo;

  /**
   * Creates a new SwitchCase with the specified case expression and statements
   * @param caseExpression - the expression for this case, or null if this is a default case
   * @param statements - the statements in this switch case
   * @param parseInfo - the parsing information
   */
  public SwitchCase(Expression caseExpression, Statement[] statements, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.caseExpression = caseExpression;
    this.statements = statements;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * @return the caseExpression
   */
  public Expression getCaseExpression()
  {
    return caseExpression;
  }

  /**
   * @return the statements
   */
  public Statement[] getStatements()
  {
    return statements;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    if (caseExpression == null)
    {
      buffer.append("default:");
    }
    else
    {
      buffer.append("case " + caseExpression + ":");
    }
    for (Statement statement : statements)
    {
      buffer.append("\n");
      String statementStr = String.valueOf(statement);
      buffer.append(statementStr.replaceAll("(?m)^", "   "));
    }
    return buffer.toString();
  }

}
