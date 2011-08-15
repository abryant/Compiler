package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;


/*
 * Created on 10 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ParameterListAST extends ParameterAST
{

  private ParameterAST[] parameters;

  /**
   * Creates a new ParameterListAST with the specified parameters.
   * @param parameters - the parameters to store
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ParameterListAST(ParameterAST[] parameters, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.parameters = parameters;
  }

  /**
   * @return the parameters
   */
  public ParameterAST[] getParameters()
  {
    return parameters;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("(");
    for (int i = 0; i < parameters.length; i++)
    {
      buffer.append(parameters[i]);
      if (i != parameters.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(")");
    return buffer.toString();
  }
}
