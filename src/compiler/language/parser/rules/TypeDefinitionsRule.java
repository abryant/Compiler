package compiler.language.parser.rules;

import compiler.language.ast.TypeDefinition;
import compiler.language.parser.Type;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeDefinitionsRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {Type.TYPE_DEFINITIONS, Type.TYPE_DEFINITION};

  public TypeDefinitionsRule()
  {
    super(Type.TYPE_DEFINITIONS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new TypeDefinition[0];
    }
    if (types == PRODUCTION)
    {
      TypeDefinition[] oldDefinitions = (TypeDefinition[]) args[0];
      TypeDefinition[] newDefinitions = new TypeDefinition[oldDefinitions.length + 1];
      System.arraycopy(oldDefinitions, 0, newDefinitions, 0, oldDefinitions.length);
      newDefinitions[oldDefinitions.length] = (TypeDefinition) args[1];
      return newDefinitions;
    }
    throw new IllegalArgumentException("Invalid type list passed to match()");
  }

}
