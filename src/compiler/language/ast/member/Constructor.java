package compiler.language.ast.member;

import compiler.language.ast.misc.ArgumentList;
import compiler.language.ast.statement.Block;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;



/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class Constructor extends Member
{
  private AccessSpecifier accessSpecifier;
  private Modifier[] modifiers;
  private Name name; // to be checked against the class name later in compilation
  private ArgumentList arguments;
  private PointerType[] thrownTypes;
  private Block block;

  /**
   * Creates a new Constructor with the specified parameters
   * @param accessSpecifier - the access specifier of the constructor
   * @param modifiers - the modifiers for this constructor
   * @param name - the name of the constructor (this must be equal to the class name, but is checked later in compilation)
   * @param arguments - the constructor's arguments
   * @param thrownTypes - the list of types that the constructor is declared to throw
   * @param block - the block that will be executed when calling the constructor
   */
  public Constructor(AccessSpecifier accessSpecifier, Modifier[] modifiers, Name name, ArgumentList arguments, PointerType[] thrownTypes, Block block)
  {
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.name = name;
    this.arguments = arguments;
    this.thrownTypes = thrownTypes;
    this.block = block;
  }

  /**
   * @return the access specifier
   */
  public AccessSpecifier getAccessSpecifier()
  {
    return accessSpecifier;
  }

  /**
   * @return the modifiers
   */
  public Modifier[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the arguments
   */
  public ArgumentList getArguments()
  {
    return arguments;
  }

  /**
   * @return the thrown types
   */
  public PointerType[] getThrownTypes()
  {
    return thrownTypes;
  }

  /**
   * @return the block
   */
  public Block getBlock()
  {
    return block;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    if (accessSpecifier != null)
    {
      buffer.append(accessSpecifier);
      buffer.append(" ");
    }
    buffer.append(name);
    buffer.append(arguments);
    if (thrownTypes.length > 0)
    {
      buffer.append(" throws ");
      for (int i = 0; i < thrownTypes.length; i++)
      {
        buffer.append(thrownTypes[i]);
        if (i != thrownTypes.length - 1)
        {
          buffer.append(", ");
        }
      }
    }
    buffer.append("\n");
    buffer.append(block);
    return buffer.toString();
  }
}
