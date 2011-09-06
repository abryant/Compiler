package compiler.language.conceptual.type;

import java.util.LinkedList;
import java.util.Queue;

import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InterfacePointerType extends PointerType
{

  private ConceptualInterface interfaceType;
  private TypeArgument[] typeArguments;

  /**
   * Creates a new InterfacePointerType with the specified conceptual interface, type arguments and immutability
   * @param interfaceType - the interface type
   * @param typeArguments - the type arguments
   * @param immutable - true if this PointerType should be immutable, false otherwise
   */
  public InterfacePointerType(ConceptualInterface interfaceType, TypeArgument[] typeArguments, boolean immutable)
  {
    super(immutable);
    this.interfaceType = interfaceType;
    this.typeArguments = typeArguments;
  }

  /**
   * @return the interfaceType
   */
  public ConceptualInterface getInterfaceType()
  {
    return interfaceType;
  }
  /**
   * @return the typeArguments
   */
  public TypeArgument[] getTypeArguments()
  {
    return typeArguments;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canAssign(Type type)
  {
    if (type instanceof InterfacePointerType)
    {
      InterfacePointerType other = (InterfacePointerType) type;
      if (!isImmutable() && other.isImmutable())
      {
        return false;
      }
      // We need to maintain two queues as we traverse the interface hierarchy, one for the type
      // and the other for immutability. This is because the immutability is inherited if an interface
      // extends #SomeInterface and not just SomeInterface
      Queue<ConceptualInterface> typeQueue = new LinkedList<ConceptualInterface>();
      Queue<Boolean> immutabilityQueue = new LinkedList<Boolean>();
      typeQueue.add(other.getInterfaceType());
      immutabilityQueue.add(other.isImmutable());
      while (!typeQueue.isEmpty())
      {
        ConceptualInterface currentType = typeQueue.poll();
        boolean currentImmutability = immutabilityQueue.poll();
        if (currentType.equals(interfaceType) && (isImmutable() || !currentImmutability))
        {
          return true;
        }
        for (InterfacePointerType parent : currentType.getSuperInterfaces())
        {
          typeQueue.add(parent.getInterfaceType());
          immutabilityQueue.add(currentImmutability || parent.isImmutable());
        }
      }
      return false;
    }
    if (type instanceof EnumPointerType)
    {
      EnumPointerType other = (EnumPointerType) type;
      if (!isImmutable() && other.isImmutable())
      {
        return false;
      }
      // We need to maintain two queues as we traverse the interface hierarchy, one for the type
      // and the other for immutability. This is because the immutability is inherited if an interface
      // extends #SomeInterface and not just SomeInterface
      Queue<ConceptualInterface> typeQueue = new LinkedList<ConceptualInterface>();
      Queue<Boolean> immutabilityQueue = new LinkedList<Boolean>();

      // traverse the class hierarchy first so that we can build up the initial interfaces to check
      ClassPointerType currentClass = other.getEnumType().getBaseClass();
      boolean currentImmutability = other.isImmutable();
      while (currentClass != null)
      {
        ConceptualClass conceptualClass = currentClass.getClassType();
        for (InterfacePointerType parentInterface : conceptualClass.getInterfaces())
        {
          typeQueue.add(parentInterface.getInterfaceType());
          immutabilityQueue.add(currentImmutability || parentInterface.isImmutable());
        }
        currentClass = conceptualClass.getBaseClass();
      }

      while (!typeQueue.isEmpty())
      {
        ConceptualInterface currentType = typeQueue.poll();
        boolean currentTypeImmutability = immutabilityQueue.poll();
        if (currentType.equals(interfaceType) && (isImmutable() || !currentTypeImmutability))
        {
          return true;
        }
        for (InterfacePointerType parent : currentType.getSuperInterfaces())
        {
          typeQueue.add(parent.getInterfaceType());
          immutabilityQueue.add(currentTypeImmutability || parent.isImmutable());
        }
      }
      return false;
    }
    return false;
  }

}
