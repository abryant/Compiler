package compiler.language.ast.statement;


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
   */
  public Block(Statement[] statements)
  {
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
