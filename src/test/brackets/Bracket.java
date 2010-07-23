package test.brackets;

/*
 * Created on 15 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class Bracket
{

  private Bracket[] subList;

  public Bracket(Bracket[] subList)
  {
    this.subList = subList;
  }

  /**
   * @return the subList
   */
  public Bracket[] getSubList()
  {
    return subList;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("(");
    for (Bracket subBracket : subList)
    {
      buffer.append(subBracket);
    }
    buffer.append(")");
    return buffer.toString();
  }
}
