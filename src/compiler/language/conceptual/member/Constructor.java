package compiler.language.conceptual.member;

import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.Argument;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.statement.Statement;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.typeDefinition.TypeDefinition;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class Constructor extends Resolvable
{
  private TypeDefinition enclosingTypeDefinition;

  private AccessSpecifier accessSpecifier;
  private SinceSpecifier sinceSpecifier;
  private String name;
  private Argument[] arguments;
  private PointerType[] thrownTypes;
  private Statement[] statements;

  /**
   * Creates a new Constructor with the specified enclosing type definition and other properties.
   * @param enclosingTypeDefinition - the enclosing type definition
   * @param accessSpecifier
   * @param sinceSpecifier
   * @param name
   */
  public Constructor(TypeDefinition enclosingTypeDefinition, AccessSpecifier accessSpecifier, SinceSpecifier sinceSpecifier, String name)
  {
    this.enclosingTypeDefinition = enclosingTypeDefinition;
    this.accessSpecifier = accessSpecifier;
    this.sinceSpecifier = sinceSpecifier;
    this.name = name;
  }

  /**
   * @return the accessSpecifier
   */
  public AccessSpecifier getAccessSpecifier()
  {
    return accessSpecifier;
  }
  /**
   * @return the sinceSpecifier
   */
  public SinceSpecifier getSinceSpecifier()
  {
    return sinceSpecifier;
  }
  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }
  /**
   * @return the arguments
   */
  public Argument[] getArguments()
  {
    return arguments;
  }
  /**
   * @return the thrownTypes
   */
  public PointerType[] getThrownTypes()
  {
    return thrownTypes;
  }
  /**
   * @return the statements
   */
  public Statement[] getStatements()
  {
    return statements;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.CONSTRUCTOR;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resolvable resolve(String name) throws NameConflictException
  {
    // TODO: implement
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Resolvable getParent()
  {
    return enclosingTypeDefinition;
  }

}
