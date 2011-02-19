package compiler.language.conceptual.topLevel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import compiler.language.ast.ParseInfo;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.ScopedResult;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
import compiler.language.translator.conceptual.ConceptualTranslator;

/*
 * Created on 13 Feb 2011
 */

/**
 * Represents a package in the conceptual hierarchy.
 * Contains mappings from the names of type definitions it contains to the conceptual files they are defined in.
 * @author Anthony Bryant
 */
public final class ConceptualPackage
{
  private static final String FILE_EXTENSION = ".x"; // TODO: move this constant to a more sensible place

  private ConceptualTranslator translator;

  // the root of the package hierarchy, or null if we are the root package
  private ConceptualPackage rootPackage;
  private ConceptualPackage parentPackage;

  private QName name;

  // this stores the directory that the source for this package is stored in
  // if there is no source in this package or any of the packages above it then this is not set,
  // which allows this ConceptualPackage to be used as a generic parent of any packages which have this somewhere in their hierarchy
  private File packageDirectory;

  // the sub-packages of this package - this is not populated until addSubPackages() is called
  private Map<String, ConceptualPackage> subPackages;

  // stores a mapping from type definition name to the conceptual file it is declared in
  private Map<String, ConceptualFile> typeFiles = new HashMap<String, ConceptualFile>();

  /**
   * Creates a new ConceptualPackage to act as a root package.
   * @param translator - the ConceptualTranslator to use to create any ConceptualFile in this package and its sub-packages
   */
  public ConceptualPackage(ConceptualTranslator translator)
  {
    this.translator = translator;
    name = new QName();
  }

  /**
   * Creates a new ConceptualPackage that will take files from the specified directory.
   * @param translator - the ConceptualTranslator to use to create any ConceptualFile in this package and its sub-packages
   * @param rootPackage - the root package
   * @param parentPackage - the package directly above this one in the hierarchy
   * @param packageName - the name of this package inside its parent
   * @param directory - the directory to read the files in this package from
   */
  public ConceptualPackage(ConceptualTranslator translator, ConceptualPackage rootPackage, ConceptualPackage parentPackage, String packageName)
  {
    this.translator = translator;
    this.rootPackage = rootPackage;
    this.parentPackage = parentPackage;
    if (parentPackage != null && packageName != null)
    {
      this.name = new QName(parentPackage.getName(), packageName);
    }
  }

  /**
   * Sets the directory of this package to the specified directory, and loads all of the files inside it
   * @param directory - the directory to load
   */
  public void loadFiles(File directory)
  {
    // populate the unparsed files map
    if (!directory.isDirectory())
    {
      throw new IllegalArgumentException("Cannot create a ConceptualPackage without a valid directory to read from.");
    }
    boolean parsedFiles = false;
    for (File file : directory.listFiles())
    {
      if (!file.isFile())
      {
        continue;
      }
      String name =  file.getName();
      if (!name.endsWith(FILE_EXTENSION))
      {
        continue;
      }
      ConceptualFile conceptualFile = translator.parseFile(file);
      if (conceptualFile == null)
      {
        continue;
      }
      parsedFiles = true;
      for (String typeName : conceptualFile.getDeclaredTypeNames())
      {
        typeFiles.put(typeName, conceptualFile);
      }
    }
    if (parsedFiles || parentPackage.hasDirectory())
    {
      packageDirectory = directory;
    }
  }

  /**
   * @return the name of this ConceptualPackage
   */
  public QName getName()
  {
    return name;
  }

  /**
   * @return true if this package is associated with a directory, false otherwise
   */
  public boolean hasDirectory()
  {
    return packageDirectory != null;
  }

  /**
   * @return the directory of this ConceptualPackage, or null if it does not have a specific directory
   */
  public File getDirectory()
  {
    return packageDirectory;
  }

  /**
   * Adds all sub-packages in the specified directory to this package, if they do not already exist.
   * If this ConceptualPackage has an associated directory, then adding packages from any directory but that one will cause an IllegalStateException.
   * Also if there is an associated directory, it is only scanned once no matter how many times this is called.
   * @param directory - a directory that this package could represent
   * @throws NameConflictException - if an existing sub-package has the same name as a new one, and they do not refer to the same directory
   */
  public void addSubPackages(File directory) throws NameConflictException
  {
    if (hasDirectory() && !packageDirectory.equals(directory))
    {
      throw new IllegalStateException("Cannot add subpackages to a ConceptualPackage which already has a directory");
    }
    if (hasDirectory() && subPackages != null)
    {
      // we have already scanned the only possible directory for sub-packages
      return;
    }

    if (subPackages == null)
    {
      subPackages = new HashMap<String, ConceptualPackage>();
    }

    for (File file : directory.listFiles())
    {
      if (!file.isDirectory())
      {
        if (file.isFile() && file.getName().endsWith(FILE_EXTENSION))
        {
          throw new IllegalArgumentException("Cannot add subpackages from a package that contains source files.");
        }
        continue;
      }
      String name = file.getName();
      if (typeFiles.containsKey(name))
      {
        // TODO: fill in this ParseInfo object with references to both the package directory and the type definition
        throw new NameConflictException("Package name \"" + name + "\" conflicts with a type definition.", (ParseInfo[]) null);
      }
      ConceptualPackage existingPackage = subPackages.get(name);
      ConceptualPackage newPackage = new ConceptualPackage(translator, rootPackage == null ? this : rootPackage, this, name);
      if (existingPackage == null)
      {
        // no existing subpackage, add it
        subPackages.put(name, newPackage);
        newPackage.loadFiles(file);
      }
      else
      {
        File existingDir = existingPackage.getDirectory();
        File newDir = newPackage.getDirectory(); // this will either be equal to file or null
        if (existingDir == null ? newDir != null : !existingDir.equals(newDir))
        {
          // either existingDir != null, or newDir != null, or both are non-null but not equal
          // this means one of them is a duplicate package
          throw new NameConflictException("Duplicate package found.", (ParseInfo[]) null);
          // TODO: fill in this ParseInfo object (above), it should contain a reference to both of the directories
        }

        // existingDir and newDir are either both null or both point to the same directory
        // so there is no point replacing existingPackage with newPackage, doing it could only lose us data
      }
    }
  }

  /**
   * Finds the sub-package with the specified name.
   * @param name - the name of the sub-package to find
   * @return the ConceptualPackage under this one with the specified name, or null if none could be found
   * @throws NameConflictException - if there was a package conflict (two different directories referring to a single package, with at least one containing files)
   */
  public ConceptualPackage getSubPackage(String name) throws NameConflictException
  {
    if (hasDirectory())
    {
      addSubPackages(packageDirectory);
    }
    else
    {
      // TODO: find a list of directories from the classpath to scan from, and add them with addSubPackages() until the subpackage is found
    }
    return subPackages.get(name);
  }

  /**
   * Resolves the specified name in this package.
   * @param name - the name to resolve
   * @return the result of resolving the specified name, or null if the name could not be resolved
   * @throws NameConflictException - if a name conflict was detected while resolving the name
   */
  public ScopedResult resolve(String name) throws NameConflictException
  {
    ConceptualPackage subPackage = getSubPackage(name);
    if (subPackage != null)
    {
      return new ScopedResult(ScopeType.PACKAGE, subPackage);
    }
    ConceptualFile file = typeFiles.get(name);
    if (file == null)
    {
      return null;
    }
    ConceptualClass conceptualClass = file.getClass(name);
    if (conceptualClass != null)
    {
      return new ScopedResult(ScopeType.CLASS, conceptualClass);
    }
    ConceptualInterface conceptualInterface = file.getInterface(name);
    if (conceptualInterface != null)
    {
      return new ScopedResult(ScopeType.INTERFACE, conceptualInterface);
    }
    ConceptualEnum conceptualEnum = file.getEnum(name);
    if (conceptualEnum != null)
    {
      return new ScopedResult(ScopeType.ENUM, conceptualEnum);
    }
    return null;
  }

  /**
   * Resolves the specified QName from this package.
   * @param name - the QName to resolve
   * @param recurseUpwards - true to recurse back to the parent package if there are no results in this package, false to just return null in this scenario
   * @return the result of resolving the QName, as a ScopedResult, or null if the name could not be resolved
   * @throws NameConflictException - if a name conflict was detected while resolving the name
   */
  public ScopedResult resolve(QName name, boolean recurseUpwards) throws NameConflictException
  {
    String first = name.getFirstName();
    ScopedResult result = resolve(first);
    if (result != null)
    {
      if (name.getLength() == 1)
      {
        return result;
      }
      return result.resolve(name.getTrailingNames());
    }
    if (recurseUpwards && parentPackage != null)
    {
      return parentPackage.resolve(name, true);
    }
    return null;
  }

}
