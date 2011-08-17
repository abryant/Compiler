package compiler.language.translator.conceptual;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.UnresolvableException;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.type.ClassPointerType;
import compiler.language.conceptual.type.InterfacePointerType;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
import compiler.language.conceptual.typeDefinition.InnerClass;
import compiler.language.conceptual.typeDefinition.InnerEnum;
import compiler.language.conceptual.typeDefinition.InnerInterface;
import compiler.language.conceptual.typeDefinition.OuterClass;
import compiler.language.conceptual.typeDefinition.OuterEnum;
import compiler.language.conceptual.typeDefinition.OuterInterface;
import compiler.language.conceptual.typeDefinition.TypeDefinition;

/*
 * Created on 29 Jun 2011
 */

/**
 * A class to facilitate checking whether access to certain members is allowed, based on access specifiers.
 * @author Anthony Bryant
 */
public class AccessSpecifierChecker
{
  private ConceptualClass universalBaseClass;

  /**
   * Creates a new AccessSpecifierChecker to check whether AccessSpecifiers are valid
   * @param universalBaseClass - the universal base class to use to determine whether the end of a class hierarchy has been reached
   */
  public AccessSpecifierChecker(ConceptualClass universalBaseClass)
  {
    this.universalBaseClass = universalBaseClass;
  }

  /**
   * Resolves the specified QName, while checking that all of the names along the way are accessible according to their access specifiers.
   * @param name - the QName to resolve
   * @param nameLexicalPhrases - the LexicalPhrase objects corresponding to each of the names in the QName being resolved
   * @param startScope - the scope to start the resolution from
   * @return the Resolvable resolved
   * @throws NameConflictException - if a name conflict is detected while resolving this QName
   * @throws UnresolvableException - if further initialisation must be done before it can be known whether one of the names can be resolved and is accessible from this startScope
   * @throws ConceptualException - if an access specifier is invalid
   */
  public Resolvable resolve(QName name, Resolvable startScope) throws NameConflictException, UnresolvableException, ConceptualException
  {
    String[] names = name.getNames();
    LexicalPhrase[] lexicalPhrases = name.getLexicalPhrases();

    Resolvable current = startScope;
    while (current != null)
    {
      // try starting with current
      for (int i = 0; i < names.length; i++)
      {
        Resolvable next = current.resolve(names[i]);
        if (next == null)
        {
          if (i == 0)
          {
            // try starting with the parent of current
            break;
          }
          return null;
        }

        // check that the access specifier is valid before going onto the next name
        checkAccess(next, startScope, lexicalPhrases[i]);

        if (i == names.length - 1)
        {
          // the whole name has been resolved, so return the result
          return next;
        }
        current = next;
      }

      // try again, this time starting with current's parent
      current = current.getParent();
    }
    return null;
  }

  /**
   * Checks that accessing the specified Resolvable is valid from the specified usage scope. If it is not valid, then a ConceptualException is thrown.
   * If accessed does not have an access specifier, then this method does not check anything.
   * @param accessed - the Resolvable being accessed
   * @param usageScope - the Resolvable representing the scope that the access is coming from
   * @param usageLexicalPhrase - the LexicalPhrase of the name that has been resolved to point to accessed
   * @throws ConceptualException - if the access is invalid
   * @throws UnresolvableException - if it is impossible to determine whether the access is valid due to another unresolved name
   */
  public void checkAccess(Resolvable accessed, Resolvable usageScope, LexicalPhrase usageLexicalPhrase) throws ConceptualException, UnresolvableException
  {
    AccessSpecifier accessSpecifier;
    String memberName;
    switch (accessed.getType())
    {
    case INNER_CLASS:
      accessSpecifier = ((InnerClass) accessed).getAccessSpecifier();
      memberName      = ((InnerClass) accessed).getName();
      break;
    case INNER_ENUM:
      accessSpecifier = ((InnerEnum) accessed).getAccessSpecifier();
      memberName      = ((InnerEnum) accessed).getName();
      break;
    case INNER_INTERFACE:
      accessSpecifier = ((InnerInterface) accessed).getAccessSpecifier();
      memberName      = ((InnerInterface) accessed).getName();
      break;
    case OUTER_CLASS:
      accessSpecifier = ((OuterClass) accessed).getAccessSpecifier();
      memberName      = ((OuterClass) accessed).getName();
      break;
    case OUTER_ENUM:
      accessSpecifier = ((OuterEnum) accessed).getAccessSpecifier();
      memberName      = ((OuterEnum) accessed).getName();
      break;
    case OUTER_INTERFACE:
      accessSpecifier = ((OuterInterface) accessed).getAccessSpecifier();
      memberName      = ((OuterInterface) accessed).getName();
      break;
    default:
      return;
    }

    checkAccess(accessed.getParent(), usageScope, accessSpecifier, usageLexicalPhrase, memberName);
  }

  /**
   * Checks whether the specified access specifier allows something at usageScope to access something at containingScope.
   * @param containingScope - the scope containing the thing being accessed
   * @param usageScope - the scope containing the thing doing the accessing
   * @param accessSpecifier - the access specifier to check
   * @param usageLexicalPhrase - the LexicalPhrase to add to any Exceptions thrown
   * @param memberName - the name of the member, to include in error messages
   * @throws ConceptualException - if the member is not accessible
   * @throws UnresolvableException - if it is unknown whether the name can be resolved
   */
  public void checkAccess(Resolvable containingScope, Resolvable usageScope, AccessSpecifier accessSpecifier, LexicalPhrase usageLexicalPhrase, String memberName) throws ConceptualException, UnresolvableException
  {
    if (!checkAccess(containingScope, usageScope, accessSpecifier, usageLexicalPhrase))
    {
      throw new ConceptualException(memberName + " is not accessible from this scope", usageLexicalPhrase);
    }
  }

  /**
   * Checks whether the specified access specifier allows something at usageScope to access something at containingScope.
   * @param containingScope - the scope containing the thing being accessed
   * @param usageScope - the scope containing the thing doing the accessing
   * @param accessSpecifier - the access specifier to check
   * @param usageLexicalPhrase - the LexicalPhrase to add to any Exceptions thrown
   * @return true if the access is valid, false if it is not valid
   * @throws UnresolvableException - if it is impossible to determine whether or not the access is allowed, due to a lack of information about parent types
   */
  public boolean checkAccess(Resolvable containingScope, Resolvable usageScope, AccessSpecifier accessSpecifier, LexicalPhrase usageLexicalPhrase) throws UnresolvableException
  {
    if (accessSpecifier == AccessSpecifier.PUBLIC)
    {
      return true;
    }
    if (accessSpecifier == AccessSpecifier.PRIVATE)
    {
      // traverse up the hierarchy until we find the first type definition for containingScope
      Resolvable containingTypeDefinition = containingScope;
      ScopeType containingType = containingTypeDefinition.getType();
      while (containingType != ScopeType.OUTER_CLASS     && containingType != ScopeType.INNER_CLASS     &&
             containingType != ScopeType.OUTER_INTERFACE && containingType != ScopeType.INNER_INTERFACE &&
             containingType != ScopeType.OUTER_ENUM      && containingType != ScopeType.INNER_ENUM)
      {
        containingTypeDefinition = containingTypeDefinition.getParent();
        containingType = containingTypeDefinition.getType();
      }
      // find out whether usageScope is inside containingTypeDefinition
      Resolvable usageTypeDefinition = usageScope;
      while (usageTypeDefinition != null && !usageTypeDefinition.equals(containingTypeDefinition))
      {
        usageTypeDefinition = usageTypeDefinition.getParent();
      }
      if (usageTypeDefinition != null)
      {
        return true;
      }
    }
    if (accessSpecifier == AccessSpecifier.PACKAGE || accessSpecifier == AccessSpecifier.PACKAGE_PROTECTED)
    {
      // traverse up the hierarchy until we find the first package for containingScope
      Resolvable containingPackage = containingScope;
      while (containingPackage.getType() != ScopeType.PACKAGE)
      {
        containingPackage = containingPackage.getParent();
      }
      // check whether endScope is inside this package
      Resolvable usagePackage = usageScope;
      while (usagePackage != null && !usagePackage.equals(containingPackage))
      {
        usagePackage = usagePackage.getParent();
      }
      if (usagePackage != null)
      {
        return true;
      }
    }
    if (accessSpecifier == AccessSpecifier.PROTECTED || accessSpecifier == AccessSpecifier.PACKAGE_PROTECTED)
    {
      // traverse up the hierarchy until we find the first type definition for containingScope
      Resolvable containingTypeDefinition = containingScope;
      ScopeType containingType = containingTypeDefinition.getType();
      while (containingType != ScopeType.OUTER_CLASS     && containingType != ScopeType.INNER_CLASS     &&
             containingType != ScopeType.OUTER_INTERFACE && containingType != ScopeType.INNER_INTERFACE &&
             containingType != ScopeType.OUTER_ENUM      && containingType != ScopeType.INNER_ENUM)
      {
        containingTypeDefinition = containingTypeDefinition.getParent();
        containingType = containingTypeDefinition.getType();
      }
      // traverse up the hierarchy finding type definitions that contain usageScope, and add them to the processing queue so that we can check every level
      Queue<TypeDefinition> queue = new LinkedList<TypeDefinition>();
      Resolvable usageTypeDefinition = usageScope;
      while (usageTypeDefinition.getType() != ScopeType.PACKAGE)
      {
        usageTypeDefinition = usageTypeDefinition.getParent();
        ScopeType usageType = usageTypeDefinition.getType();
        if (usageType == ScopeType.OUTER_CLASS     || usageType == ScopeType.INNER_CLASS     ||
            usageType == ScopeType.OUTER_INTERFACE || usageType == ScopeType.INNER_INTERFACE ||
            usageType == ScopeType.OUTER_ENUM      || usageType == ScopeType.INNER_ENUM)
        {
          queue.add((TypeDefinition) usageTypeDefinition);
        }
      }

      boolean unresolvable = false;
      Set<TypeDefinition> processed = new HashSet<TypeDefinition>();
      while (!queue.isEmpty())
      {
        TypeDefinition current = queue.poll();
        // skip types that we have already processed
        if (processed.contains(current))
        {
          continue;
        }
        processed.add(current);

        // check whether this type is the one we are looking for, if it is then the usage is definitely accessible
        if (containingTypeDefinition.equals(current))
        {
          return true;
        }

        // find all of the parent types of this type, and add them to the queue for checking
        ScopeType currentType = current.getType();
        if (currentType == ScopeType.OUTER_CLASS || currentType == ScopeType.INNER_CLASS)
        {
          ConceptualClass currentClass = (ConceptualClass) current;
          ClassPointerType baseClass = currentClass.getBaseClass();
          if (baseClass == null && !currentClass.equals(universalBaseClass))
          {
            unresolvable = true;
          }
          else if (baseClass != null)
          {
            queue.add(baseClass.getClassType());
          }
          InterfacePointerType[] superInterfaces = currentClass.getInterfaces();
          if (superInterfaces == null)
          {
            unresolvable = true;
          }
          else
          {
            for (InterfacePointerType superInterface : superInterfaces)
            {
              if (superInterface == null)
              {
                unresolvable = true;
                continue;
              }
              queue.add(superInterface.getInterfaceType());
            }
          }
        }
        else if (currentType == ScopeType.OUTER_INTERFACE || currentType == ScopeType.INNER_INTERFACE)
        {
          ConceptualInterface currentInterface = (ConceptualInterface) current;
          InterfacePointerType[] superInterfaces = currentInterface.getSuperInterfaces();
          if (superInterfaces == null)
          {
            unresolvable = true;
          }
          else
          {
            for (InterfacePointerType superInterface : superInterfaces)
            {
              if (superInterface == null)
              {
                unresolvable = true;
                continue;
              }
              queue.add(superInterface.getInterfaceType());
            }
          }
        }
        else if (currentType == ScopeType.OUTER_ENUM || currentType == ScopeType.INNER_ENUM)
        {
          ConceptualEnum currentEnum = (ConceptualEnum) current;
          ClassPointerType baseClass = currentEnum.getBaseClass();
          if (baseClass == null)
          {
            unresolvable = true;
          }
          else
          {
            queue.add(baseClass.getClassType());
          }
          InterfacePointerType[] superInterfaces = currentEnum.getInterfaces();
          if (superInterfaces == null)
          {
            unresolvable = true;
          }
          else
          {
            for (InterfacePointerType superInterface : superInterfaces)
            {
              if (superInterface == null)
              {
                unresolvable = true;
                continue;
              }
              queue.add(superInterface.getInterfaceType());
            }
          }
        }
      }
      if (unresolvable)
      {
        // we could not find the containing type definition as an ancestor of any of the type definitions containing usageScope
        // so throw an UnresolveableException to indicate this
        throw new UnresolvableException("Unable to determine whether this protected member is accessible in this context", usageLexicalPhrase);
      }
    }
    return false;
  }
}
