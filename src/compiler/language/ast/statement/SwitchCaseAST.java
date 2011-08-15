package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SwitchCaseAST
{

  private ExpressionAST caseExpression; // null if this SwitchCaseAST is actually a default case
  private StatementAST[] statements;

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new SwitchCaseAST with the specified case expression and statements
   * @param caseExpression - the expression for this case, or null if this is a default case
   * @param statements - the statements in this switch case
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public SwitchCaseAST(ExpressionAST caseExpression, StatementAST[] statements, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.caseExpression = caseExpression;
    this.statements = statements;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

  /**
   * @return the expression for this case, or null if this is actually a default case
   */
  public ExpressionAST getCaseExpression()
  {
    return caseExpression;
  }

  /**
   * @return the statements
   */
  public StatementAST[] getStatements()
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
    for (StatementAST statement : statements)
    {
      buffer.append("\n");
      String statementStr = String.valueOf(statement);
      buffer.append(statementStr.replaceAll("(?m)^", "   "));
    }
    return buffer.toString();
  }

}
