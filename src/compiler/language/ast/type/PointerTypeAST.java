package compiler.language.ast.type;

import compiler.language.LexicalPhrase;
import compiler.language.QName;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeAST extends TypeAST
{

  private boolean immutable;

  private QName names;
  private TypeArgumentAST[][] typeArgumentLists;

  /**
   * Creates a new PointerTypeAST that consists only of the specified QName
   * @param qname - the qualified name of the type
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public PointerTypeAST(QName qname, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    immutable = false;
    names = qname;
    typeArgumentLists = new TypeArgumentAST[names.getLength()][];
    for (int i = 0; i < typeArgumentLists.length; i++)
    {
      typeArgumentLists[i] = null;
    }
  }

  /**
   * Creates a new PointerTypeAST with the specified immutability, qualifying names and type argument lists.
   * @param immutable - true if this type should be immutable, false otherwise
   * @param qname - the qualified name of this PointerTypeAST, ending in the actual type name
   * @param typeArgumentLists - the type argument list for each name in this pointer type, with empty arrays for names that do not have type arguments
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public PointerTypeAST(boolean immutable, QName qname, TypeArgumentAST[][] typeArgumentLists, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.immutable = immutable;

    if (qname.getLength() != typeArgumentLists.length)
    {
      throw new IllegalArgumentException("A PointerTypeAST must have an equal number of names and lists of type arguments");
    }
    this.names = qname;
    this.typeArgumentLists = typeArgumentLists;
  }

  /**
   * Creates a new PointerTypeAST with the specified immutability, qualifying names and type argument lists.
   * @param baseType - the base PointerTypeAST to copy the old qualifying names and type arguments from
   * @param immutable - true if this type should be immutable, false otherwise
   * @param addedNames - the list of added (qualifier) names in this PointerTypeAST, ending in the actual type name, this should be provided as a QName
   * @param addedTypeArgumentLists - the type argument list for each added name in this pointer type, with empty arrays for names that do not have type arguments
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public PointerTypeAST(PointerTypeAST baseType, boolean immutable, QName addedNames, TypeArgumentAST[][] addedTypeArgumentLists, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.immutable = immutable;
    if (addedNames.getLength() != addedTypeArgumentLists.length)
    {
      throw new IllegalArgumentException("A PointerTypeAST must have an equal number of names and lists of type arguments");
    }
    QName oldNames = baseType.getQualifiedName();
    TypeArgumentAST[][] oldTypeArgumentLists = baseType.getTypeArgumentLists();

    String[] newNames = new String[oldNames.getLength() + addedNames.getLength()];
    LexicalPhrase[] newLexicalPhrases = new LexicalPhrase[oldNames.getLength() + addedNames.getLength()];
    typeArgumentLists = new TypeArgumentAST[oldTypeArgumentLists.length + addedTypeArgumentLists.length][];
    System.arraycopy(oldNames.getNames(),            0, newNames,          0,                           oldNames.getLength());
    System.arraycopy(addedNames.getNames(),          0, newNames,          oldNames.getLength(),        addedNames.getLength());
    System.arraycopy(oldNames.getLexicalPhrases(),   0, newLexicalPhrases, 0,                           oldNames.getLength());
    System.arraycopy(addedNames.getLexicalPhrases(), 0, newLexicalPhrases, oldNames.getLength(),        addedNames.getLength());
    System.arraycopy(oldTypeArgumentLists,           0, typeArgumentLists, 0,                           oldTypeArgumentLists.length);
    System.arraycopy(addedTypeArgumentLists,         0, typeArgumentLists, oldTypeArgumentLists.length, addedTypeArgumentLists.length);

    names = new QName(newNames, newLexicalPhrases);
  }

  /**
   * @return true if this type is immutable, false otherwise
   */
  public boolean isImmutable()
  {
    return immutable;
  }

  /**
   * @return the qualified name of this PointerTypeAST
   */
  public QName getQualifiedName()
  {
    return names;
  }

  /**
   * @return the typeArgumentLists
   */
  public TypeArgumentAST[][] getTypeArgumentLists()
  {
    return typeArgumentLists;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    if (immutable)
    {
      buffer.append("#");
    }
    String[] nameStrings = names.getNames();
    for (int i = 0; i < nameStrings.length; i++)
    {
      buffer.append(nameStrings[i]);
      TypeArgumentAST[] typeArgumentList = typeArgumentLists[i];
      if (typeArgumentList != null && typeArgumentList.length > 0)
      {
        buffer.append("<");
        for (int j = 0; j < typeArgumentList.length; j++)
        {
          buffer.append(typeArgumentList[j]);
          if (j != typeArgumentList.length - 1)
          {
            buffer.append(", ");
          }
        }
        buffer.append(">");
      }
      if (i != nameStrings.length - 1)
      {
        buffer.append(".");
      }
    }
    return buffer.toString();
  }
}
