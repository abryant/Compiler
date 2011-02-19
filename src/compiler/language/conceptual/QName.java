package compiler.language.conceptual;

/*
 * Created on 13 Feb 2011
 */

/**
 * Represents an immutable qualified name, consisting of a list of Strings.
 * @author Anthony Bryant
 */
public class QName
{

  private String[] names;

  /**
   * Creates a new QName with the specified list of Strings
   * @param names - the names to represent
   */
  public QName(String... names)
  {
    this.names = names;
  }

  /**
   * Creates a new QName with the names from the specified QName, plus another name
   * @param startName - the QName containing the names to start with
   * @param name - the final name for the QName
   */
  public QName(QName startName, String name)
  {
    int oldLength = startName.names.length;
    names = new String[oldLength + 1];
    System.arraycopy(startName.names, 0, names, 0, oldLength);
    names[oldLength] = name;
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
    System.arraycopy(names, 1, otherNames, 0, otherNames.length);
    return new QName(otherNames);
  }

  /**
   * Subtracts the start of this QName and returns a QName containing the remainder.
   * @param start - the QName to subtract from the start of this QName
   * @return a QName containing all but the specified start of this QName, or null if this QName does not start with the specified QName
   */
  public QName subtractStart(QName start)
  {
    if (names.length < start.names.length)
    {
      // impossible for containing to contain us, as it is longer than us
      return null;
    }

    for (int i = 0; i < start.names.length; i++)
    {
      if (!names[i].equals(start.names[i]))
      {
        return null;
      }
    }
    String[] newNames = new String[start.names.length - names.length];
    for (int i = start.names.length; i < names.length; i++)
    {
      newNames[i - start.names.length] = names[i];
    }
    return new QName(newNames);
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
