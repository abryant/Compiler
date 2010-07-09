package compiler.language.ast;

/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class Block
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
    buffer.append("{\n");
    for (Statement statement : statements)
    {
      String statementStr = String.valueOf(statement);
      buffer.append(statementStr.replaceAll("(?m)^", "   "));
      buffer.append("\n");
    }
    buffer.append("}\n");
    return buffer.toString();
  }

}
