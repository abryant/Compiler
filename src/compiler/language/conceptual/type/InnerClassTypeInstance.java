package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualClass;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InnerClassTypeInstance extends TypeInstance
{

  private TypeInstance outerTypeInstance;
  private ConceptualClass innerClass;
  private TypeParameter[] typeParameters;

}
