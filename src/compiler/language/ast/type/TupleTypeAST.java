package compiler.language.ast.type;

import compiler.language.LexicalPhrase;


/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleTypeAST extends TypeAST
{

  private TypeAST[] types;

  /**
   * Creates a new Tuple TypeAST with the specified list of types
   * @param types - the types contained in this tuple type
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public TupleTypeAST(TypeAST[] types, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.types = types;
  }

  /**
   * @return the types contained in this tuple type
   */
  public TypeAST[] getTypes()
  {
    return types;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("(");
    for (int i = 0; i < types.length; i++)
    {
      buffer.append(types[i]);
      if (i != types.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(")");
    return buffer.toString();
  }

}
