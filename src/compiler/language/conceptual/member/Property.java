package compiler.language.conceptual.member;

import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.type.Type;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class Property
{

  private boolean isSealed;
  private boolean isMutable;
  private boolean isFinal;
  private boolean isStatic;
  private boolean isSynchronized;
  private boolean isTransient;
  // TODO: volatile?
  private SinceSpecifier sinceSpecifier;
  private Type type;
  private String name;
  private AccessSpecifier retrieveAccessSpecifier;
  private AccessSpecifier assignAccessSpecifier;

  // TODO: work out how this will work
  // it will definitely need:
  // modifiers (list of booleans, plus native and since specifiers)
  // name
  // retrieve access specifier
  // assign access specifier
  // retrieve statements
  // assign statements TODO: how will this work with taking a parameter called "value"?

}
