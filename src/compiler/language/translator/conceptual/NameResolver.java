package compiler.language.translator.conceptual;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.UnresolvableException;
import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.conceptual.type.ClassTypeInstance;
import compiler.language.conceptual.type.EnumTypeInstance;
import compiler.language.conceptual.type.InnerClassTypeInstance;
import compiler.language.conceptual.type.InnerEnumTypeInstance;
import compiler.language.conceptual.type.InnerInterfaceTypeInstance;
import compiler.language.conceptual.type.InterfaceTypeInstance;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.TypeArgument;
import compiler.language.conceptual.type.TypeArgumentInstance;
import compiler.language.conceptual.type.TypeInstance;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 20 Feb 2011
 */

/**
 * @author Anthony Bryant
 */
public final class NameResolver
{

  private Map<Object, Object> conceptualASTNodes;

  private Queue<ConceptualInterface> interfacesToResolve = new LinkedList<ConceptualInterface>();
  private Queue<ConceptualClass> classesToResolve = new LinkedList<ConceptualClass>();
  private Queue<ConceptualEnum> enumsToResolve = new LinkedList<ConceptualEnum>();

  /**
   * Creates a new NameResolver with the specified mapping from Conceptual node to AST node.
   * @param conceptualASTNodes - the mapping which stores the AST node of each conceptual node which has been converted
   */
  public NameResolver(Map<Object, Object> conceptualASTNodes)
  {
    this.conceptualASTNodes = conceptualASTNodes;
  }

  /**
   * Adds all of the type definitions in the specified ConceptualFile to this resolver's queues
   * @param file - the file to add the type definitions of
   */
  public void addFile(ConceptualFile file)
  {
    for (ConceptualClass conceptualClass : file.getClasses())
    {
      classesToResolve.add(conceptualClass);
    }
    for (ConceptualInterface conceptualInterface : file.getInterfaces())
    {
      interfacesToResolve.add(conceptualInterface);
    }
    for (ConceptualEnum conceptualEnum : file.getEnums())
    {
      enumsToResolve.add(conceptualEnum);
    }
  }

  public void resolve()
  {
    // resolve base types
    boolean changed = true;
    while (changed && !(interfacesToResolve.isEmpty() && classesToResolve.isEmpty() && enumsToResolve.isEmpty()))
    {
      while (!interfacesToResolve.isEmpty())
      {
        ConceptualInterface toResolve = interfacesToResolve.poll();
        InterfaceDefinitionAST astNode = (InterfaceDefinitionAST) conceptualASTNodes.get(toResolve);
        PointerTypeAST[] parentInterfaces = astNode.getInterfaces();
        PointerType[] pointerTypes = new PointerType[parentInterfaces.length]; // TODO: leave nulls in here if one can't be resolved yet? in this case, interface's resolve() method would have to cope with the nulls properly
        for (PointerTypeAST parentInterface : parentInterfaces)
        {
          // TODO: resolve parentInterface into a PointerType
        }
      }
    }
  }

  /**
   * Resolves the specified PointerTypeAST into a PointerType
   * @param pointerTypeAST - the PointerTypeAST to resolve
   * @param startScope - the starting scope
   * @return the PointerType converted
   * @throws NameConflictException - if a name conflict is detected while resolving this pointer type
   * @throws ConceptualException - if a conceptual problem occurs while resolving the pointer type
   * @throws UnresolvableException - if further initialisation must be done before it can be known whether one of the names can be resolved
   */
  public PointerType resolvePointerType(PointerTypeAST pointerTypeAST, Resolvable startScope) throws NameConflictException, ConceptualException, UnresolvableException
  {
    NameAST[] names = pointerTypeAST.getNames();
    TypeParameterAST[][] typeParameterLists = pointerTypeAST.getTypeParameterLists();

    // go through the names, and look them up in current each iteration to find the new current resolvable
    // also build a type instance hierarchy while doing this, to give to the PointerType
    Resolvable current = startScope.resolve(new QName(names[0].getName()), true);
    TypeInstance currentTypeInstance = null;
    for (int i = 0; i < names.length; i++)
    {
      if (i > 0) // the first name is looked up separately
      {
        // lookup this name in the current Resolvable object
        // this can fail if the Resolvable has not had all of its data filled in yet,
        // for example if current is a class and does not contain this name itself but must fall back on a not-yet-filled-in parent class/interface
        current = current.resolve(names[i].getName());
      }
      if (current == null)
      {
        throw new ConceptualException("Could not resolve name", names[i].getParseInfo());
      }
      switch (current.getType())
      {
      case PACKAGE:
        if (typeParameterLists[i].length > 0)
        {
          ParseInfo[] parseInfo = new ParseInfo[typeParameterLists[i].length];
          for (int j = 0; j < typeParameterLists[i].length; j++)
          {
            parseInfo[j] = typeParameterLists[i][j].getParseInfo();
          }
          throw new ConceptualException("A package cannot have type parameters", parseInfo);
        }
        break;
      case OUTER_CLASS:
        if (currentTypeInstance != null)
        {
          throw new IllegalStateException();
        }
        currentTypeInstance = new ClassTypeInstance((ConceptualClass) current,
                                                    ASTConverter.convert(typeParameterLists[i], this, startScope));
        break;
      case INNER_CLASS:
        if (currentTypeInstance == null)
        {
          throw new IllegalStateException();
        }
        currentTypeInstance = new InnerClassTypeInstance((ConceptualClass) current,
                                                         ASTConverter.convert(typeParameterLists[i], this, startScope),
                                                         currentTypeInstance);
        break;
      case OUTER_INTERFACE:
        if (currentTypeInstance != null)
        {
          throw new IllegalStateException();
        }
        currentTypeInstance = new InterfaceTypeInstance((ConceptualInterface) current,
                                                        ASTConverter.convert(typeParameterLists[i], this, startScope));
        break;
      case INNER_INTERFACE:
        if (currentTypeInstance == null)
        {
          throw new IllegalStateException();
        }
        currentTypeInstance = new InnerInterfaceTypeInstance((ConceptualInterface) current,
                                                             ASTConverter.convert(typeParameterLists[i], this, startScope),
                                                             currentTypeInstance);
        break;
      case OUTER_ENUM:
        if (currentTypeInstance != null)
        {
          throw new IllegalStateException();
        }
        if (typeParameterLists[i].length > 0)
        {
          ParseInfo[] parseInfo = new ParseInfo[typeParameterLists[i].length];
          for (int j = 0; j < typeParameterLists[i].length; j++)
          {
            parseInfo[j] = typeParameterLists[i][j].getParseInfo();
          }
          throw new ConceptualException("An enum cannot have type parameters", parseInfo);
        }
        currentTypeInstance = new EnumTypeInstance((ConceptualEnum) current);
        break;
      case INNER_ENUM:
        if (currentTypeInstance == null)
        {
          throw new IllegalStateException();
        }
        if (typeParameterLists[i].length > 0)
        {
          ParseInfo[] parseInfo = new ParseInfo[typeParameterLists[i].length];
          for (int j = 0; j < typeParameterLists[i].length; j++)
          {
            parseInfo[j] = typeParameterLists[i][j].getParseInfo();
          }
          throw new ConceptualException("An enum cannot have type parameters", parseInfo);
        }
        currentTypeInstance = new InnerEnumTypeInstance((ConceptualEnum) current, currentTypeInstance);
        break;
      case TYPE_ARGUMENT:
        // TODO: check this
        currentTypeInstance = new TypeArgumentInstance((TypeArgument) current);
        break;
      default:
        throw new ConceptualException("Cannot reference a " + current.getType() + " in a pointer type", names[i].getParseInfo());
      }
    }
    if (currentTypeInstance == null)
    {
      // the type instance can only be null if the last name processed was a package
      throw new ConceptualException("Cannot create a pointer type to represent a package", pointerTypeAST.getParseInfo());
    }
    return new PointerType(pointerTypeAST.isImmutable(), currentTypeInstance);
  }
}
