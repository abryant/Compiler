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

  private boolean isSealed; // this means that the assign and retrieve methods cannot be overridden
  private boolean isMutable;
  private boolean isFinal; // this means that the value cannot be changed (i.e. the same as the final modifier for a variable), or that the property has no setter
  private boolean isStatic;
  private boolean isSynchronized;
  private boolean isTransient;
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
