package compiler.language.ast.type;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterAST
{

  private LexicalPhrase lexicalPhrase;

  private NameAST name;
  private PointerTypeAST[] superTypes; // the types that this type parameter extends
  private PointerTypeAST[] subTypes;   // the types that this type parameter is a superclass of

  /**
   * Creates a new TypeParameterAST with the specified name, super type and sub type.
   * @param name - the name of this type parameter
   * @param superTypes - the types that this parameter must extend
   * @param subTypes - the types that this parameter must be a superclass of
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public TypeParameterAST(NameAST name, PointerTypeAST[] superTypes, PointerTypeAST[] subTypes, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.name = name;
    this.superTypes = superTypes;
    this.subTypes = subTypes;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the types that this parameter must extend
   */
  public PointerTypeAST[] getSuperTypes()
  {
    return superTypes;
  }

  /**
   * @return the types that this parameter must be a superclass of
   */
  public PointerTypeAST[] getSubTypes()
  {
    return subTypes;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append(name);
    if (superTypes.length > 0)
    {
      buffer.append(" extends ");
      for (int i = 0; i < superTypes.length; i++)
      {
        buffer.append(superTypes[i]);
        if (i != superTypes.length - 1)
        {
          buffer.append(" & ");
        }
      }
    }
    if (subTypes.length > 0)
    {
      buffer.append(" super ");
      for (int i = 0; i < subTypes.length; i++)
      {
        buffer.append(subTypes[i]);
        if (i != subTypes.length - 1)
        {
          buffer.append(" & ");
        }
      }
    }
    return buffer.toString();
  }

}
