package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InnerInterfaceTypeInstance extends TypeInstance
{

  private TypeInstance outerTypeInstance;
  private ConceptualInterface innerInterface;
  private TypeParameter[] typeParameters;

}
