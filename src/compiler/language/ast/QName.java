package compiler.language.ast;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class QName
{

  private Name[] names;

  /**
   * Creates a new QName containing only one name
   * @param startName - the name to start the QName with
   */
  public QName(Name startName)
  {
    this.names = new Name[] {startName};
  }

  /**
   * Creates a new QName based on an old QName, but with an additional name
   * @param original - the QName to base this QName on
   * @param additional - the extra name to add
   */
  public QName(QName original, Name additional)
  {
    Name[] origNames = original.names;
    names = new Name[origNames.length + 1];
    System.arraycopy(origNames, 0, names, 0, origNames.length);
    names[origNames.length] = additional;
  }

  /**
   * @return the names
   */
  public Name[] getNames()
  {
    return names;
  }

  /**
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
        buffer.append(".");
      }
    }
    return buffer.toString();
  }
}
