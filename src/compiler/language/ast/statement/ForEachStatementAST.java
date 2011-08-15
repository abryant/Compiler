package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.TypeAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ForEachStatementAST extends StatementAST
{

  private TypeAST type;
  private NameAST name;
  private ExpressionAST expression;
  private BlockAST block;

  /**
   * Creates a new ForEachStatementAST which iterates a variable over all of the values that are stored by the result of the expression
   * @param type - the type of the loop variable
   * @param name - the name of the loop variable
   * @param expression - the expression to iterate over
   * @param block - the block to execute for each iteration of the loop
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ForEachStatementAST(TypeAST type, NameAST name, ExpressionAST expression, BlockAST block, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.type = type;
    this.name = name;
    this.expression = expression;
    this.block = block;
  }

  /**
   * @return the type
   */
  public TypeAST getType()
  {
    return type;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @return the block
   */
  public BlockAST getBlock()
  {
    return block;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "for " + type + " " + name + " : " + expression + "\n" + block;
  }
}
