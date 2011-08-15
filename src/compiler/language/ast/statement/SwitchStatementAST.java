package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SwitchStatementAST extends StatementAST
{

  private ExpressionAST switchExpression;
  private SwitchCaseAST[] cases;

  /**
   * Creates a new SwitchStatementAST with the specified switch expression and list of cases.
   * @param switchExpression - the expression to switch on
   * @param cases - the list of switch cases in this switch statement
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public SwitchStatementAST(ExpressionAST switchExpression, SwitchCaseAST[] cases, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.switchExpression = switchExpression;
    this.cases = cases;
  }

  /**
   * @return the switchExpression
   */
  public ExpressionAST getSwitchExpression()
  {
    return switchExpression;
  }

  /**
   * @return the cases
   */
  public SwitchCaseAST[] getCases()
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
    for (SwitchCaseAST switchCase : cases)
    {
      buffer.append("\n");
      buffer.append(switchCase);
    }
    buffer.append("\n}");
    return buffer.toString();
  }
}
