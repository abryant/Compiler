package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualEnum;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InnerEnumTypeInstance extends TypeInstance
{

  private TypeInstance outerTypeInstance;
  private ConceptualEnum innerEnum;
  private TypeParameter[] typeParameters;

}
