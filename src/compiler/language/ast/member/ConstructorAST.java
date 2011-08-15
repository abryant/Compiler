package compiler.language.ast.member;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.ParameterListAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;



/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConstructorAST extends MemberAST
{

  private AccessSpecifierAST accessSpecifier;
  private ModifierAST[] modifiers;
  private NameAST name; // to be checked against the class name later in compilation
  private ParameterListAST parameters;
  private PointerTypeAST[] thrownTypes;
  private BlockAST block;

  /**
   * Creates a new ConstructorAST with the specified parameters
   * @param accessSpecifier - the access specifier of the constructor
   * @param modifiers - the modifiers for this constructor
   * @param name - the name of the constructor (this must be equal to the class name, but is checked later in compilation)
   * @param parameters - the constructor's parameters
   * @param thrownTypes - the list of types that the constructor is declared to throw
   * @param block - the block that will be executed when calling the constructor
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ConstructorAST(AccessSpecifierAST accessSpecifier, ModifierAST[] modifiers, NameAST name, ParameterListAST parameters, PointerTypeAST[] thrownTypes, BlockAST block, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.accessSpecifier = accessSpecifier;
    this.modifiers = modifiers;
    this.name = name;
    this.parameters = parameters;
    this.thrownTypes = thrownTypes;
    this.block = block;
  }

  /**
   * @return the access specifier
   */
  public AccessSpecifierAST getAccessSpecifier()
  {
    return accessSpecifier;
  }

  /**
   * @return the modifiers
   */
  public ModifierAST[] getModifiers()
  {
    return modifiers;
  }

  /**
   * @return the name
   */
  public NameAST getName()
  {
    return name;
  }

  /**
   * @return the parameters
   */
  public ParameterListAST getParameters()
  {
    return parameters;
  }

  /**
   * @return the thrown types
   */
  public PointerTypeAST[] getThrownTypes()
  {
    return thrownTypes;
  }

  /**
   * @return the block
   */
  public BlockAST getBlock()
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
    buffer.append(parameters);
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
