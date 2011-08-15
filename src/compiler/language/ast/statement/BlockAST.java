package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;


/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BlockAST extends StatementAST
{

  private StatementAST[] statements;

  /**
   * Creates a new BlockAST with the specified list of statements.
   * @param statements - the statements in this block
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public BlockAST(StatementAST[] statements, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.statements = statements;
  }

  /**
   * @return the statements
   */
  public StatementAST[] getStatements()
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
    for (StatementAST statement : statements)
    {
      buffer.append("\n");
      String statementStr = String.valueOf(statement);
      buffer.append(statementStr.replaceAll("(?m)^", "   "));
    }
    buffer.append("\n}");
    return buffer.toString();
  }

}
