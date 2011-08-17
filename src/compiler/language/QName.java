package compiler.language;


/*
 * Created on 13 Feb 2011
 */

/**
 * Represents an immutable qualified name, consisting of a list of Strings.
 * This also stores a LexicalPhrase for each of the constituent names.
 * NOTE: equals() and hashCode() are not defined in terms of the LexicalPhrases that this object stores.
 * @author Anthony Bryant
 */
public class QName
{

  private String[] names;
  private LexicalPhrase[] lexicalPhrases;

  /**
   * Creates a new QName with the specified list of Strings
   * @param names - the names to represent
   *//*
  public QName(String... names)
  {
    this.names = names;
    lexicalPhrases = new LexicalPhrase[names.length];
  }*/

  /**
   *
   * @param name
   * @param lexicalPhrase
   */
  public QName(String name, LexicalPhrase lexicalPhrase)
  {
    names = new String[] {name};
    lexicalPhrases = new LexicalPhrase[] {lexicalPhrase};
  }

  /**
   * Creates a new QName with the specified list of Strings and LexicalPhrases.
   * @param names - the names to represent
   * @param lexicalPhrases - the lexicalPhrases to represent
   */
  public QName(String[] names, LexicalPhrase[] lexicalPhrases)
  {
    this.names = names;
    this.lexicalPhrases = lexicalPhrases;
    if (names.length != lexicalPhrases.length)
    {
      throw new IllegalArgumentException("The number of names must be equal to the number of lexical phrases provided.");
    }
  }

  /**
   * Creates a new QName with the names from the specified QName, plus another name
   * @param startName - the QName containing the names to start with
   * @param name - the final name for the QName
   * @param lexicalPhrase - the final LexicalPhrase for the QName
   */
  public QName(QName startName, String name, LexicalPhrase lexicalPhrase)
  {
    int oldLength = startName.names.length;
    names = new String[oldLength + 1];
    lexicalPhrases = new LexicalPhrase[oldLength + 1];
    System.arraycopy(startName.names, 0, names, 0, oldLength);
    System.arraycopy(startName.lexicalPhrases, 0, lexicalPhrases, 0, oldLength);
    names[oldLength] = name;
    lexicalPhrases[oldLength] = lexicalPhrase;
  }

  /**
   * @return the length, in names, of this QName
   */
  public int getLength()
  {
    return names.length;
  }

  /**
   * @return a copy of the names stored in this QName (this is a copy so that the immutability is preserved)
   */
  public String[] getNames()
  {
    return names.clone();
  }

  /**
   * @return a copy of the lexical phrases stored in this QName (this is a copy so that the immutability is preserved)
   */
  public LexicalPhrase[] getLexicalPhrases()
  {
    return lexicalPhrases.clone();
  }

  /**
   * @return the combined LexicalPhrases for each of the constituent QNames
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return LexicalPhrase.combine(lexicalPhrases);
  }

  /**
   * @return the first name in this QName
   * @throws ArrayIndexOutOfBoundsException - if this QName contains no names
   */
  public String getFirstName()
  {
    return names[0];
  }

  /**
   * @return the last name in this QName
   * @throws ArrayIndexOutOfBoundsException - if this QName contains no names
   */
  public String getLastName()
  {
    return names[names.length - 1];
  }

  /**
   * @return a QName representing all of the names after the first name
   */
  public QName getTrailingNames()
  {
    String[] otherNames = new String[names.length - 1];
    LexicalPhrase[] otherPhrases = new LexicalPhrase[lexicalPhrases.length - 1];
    System.arraycopy(names, 1, otherNames, 0, otherNames.length);
    System.arraycopy(lexicalPhrases, 1, otherPhrases, 0, otherPhrases.length);
    return new QName(otherNames, otherPhrases);
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < names.length; i++)
    {
      buffer.append(names[i]);
      if (i != names.length - 1)
      {
        buffer.append('.');
      }
    }
    return buffer.toString();
  }

  /**
   * NOTE: this does not depend on the LexicalPhrases of the constituent names, only on the names themselves
   * {@inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof QName))
    {
      return false;
    }
    QName other = (QName) o;
    if (names.length != other.names.length)
    {
      return false;
    }
    for (int i = 0; i < names.length; i++)
    {
      if (!names[i].equals(other.names[i]))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * NOTE: this does not depend on the LexicalPhrases of the constituent names, only on the names themselves
   * {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    int code = 0;
    for (int i = 0; i < names.length; i++)
    {
      code = 31 * code + names[i].hashCode();
    }
    return code;
  }

}
