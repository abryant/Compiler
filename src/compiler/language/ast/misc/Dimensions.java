package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;

/*
 * Created on 12 Aug 2010
 */

/**
 * A parse-time only type for storing the number of dimensions in an array instanciation expression.
 *
 * This should NEVER be used as an AST node, as it just stores an integer.
 *
 * @author Anthony Bryant
 */
public class Dimensions
{

  private ParseInfo parseInfo;

  private int dimensions;

  /**
   * Creates a new Dimensions object representing the specified number of dimensions
   * @param dimensions - the number of dimensions to store
   * @param parseInfo - the parsing information
   */
  public Dimensions(int dimensions, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.dimensions = dimensions;
  }

  /**
   * @return the dimensions
   */
  public int getDimensions()
  {
    return dimensions;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

}
