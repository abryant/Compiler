package compiler.language.ast.misc;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public enum AssignmentOperator
{
  EQUALS("="),
  PLUS_EQUALS("+="),
  MINUS_EQUALS("-="),
  TIMES_EQUALS("*="),
  DIVIDE_EQUALS("/="),
  MODULUS_EQUALS("%="),
  BOOLEAN_AND_EQUALS("&&="),
  BOOLEAN_OR_EQUALS("||="),
  BOOLEAN_XOR_EQUALS("^^="),
  BITWISE_AND_EQUALS("&="),
  BITWISE_OR_EQUALS("|="),
  BITWISE_XOR_EQUALS("^="),
  LEFT_SHIFT_EQUALS("<<"),
  ARITHMETIC_RIGHT_SHIFT_EQUALS(">>"),
  LOGICAL_RIGHT_SHIFT_EQUALS(">>>");

  private String tokenString;

  /**
   * Creates a new AssignmentOperator with the specified token string
   * @param tokenString - the String representation of this assignment operator's token
   */
  private AssignmentOperator(String tokenString)
  {
    this.tokenString = tokenString;
  }

  /**
   * @return the String representation of this assignment operator's token
   */
  public String getTokenString()
  {
    return tokenString;
  }


}
