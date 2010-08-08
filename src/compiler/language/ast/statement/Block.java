package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;


/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class Block extends Statement
{

  private Statement[] statements;

  /**
   * Creates a new Block with the specified list of statements.
   * @param statements - the statements in this block
   * @param parseInfo - the parsing information
   */
  public Block(Statement[] statements, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.statements = statements;
  }

  /**
   * @return the statements
   */
  public Statement[] getStatements()
  {
    return statements;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("{");
    for (Statement statement : statements)
    {
      buffer.append("\n");
      String statementStr = String.valueOf(statement);
      buffer.append(statementStr.replaceAll("(?m)^", "   "));
    }
    buffer.append("\n}");
    return buffer.toString();
  }

}
