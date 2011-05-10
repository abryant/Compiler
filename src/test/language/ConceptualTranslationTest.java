package test.language;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import compiler.language.ast.ParseInfo;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.parser.LanguageParser;
import compiler.language.translator.conceptual.ConceptualTranslator;

/*
 * Created on 30 Apr 2011
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualTranslationTest
{

  public static void main(String[] args)
  {
    if (args.length < 2)
    {
      System.err.println("Usage: java test.language.ConceptualTranslationTest <SourceFile> <ClasspathFolder>");
      System.exit(1);
    }

    File file = new File(args[0]);
    if (!file.isFile())
    {
      System.err.println("Could not find source file: " + file.getAbsolutePath());
      System.exit(1);
    }
    List<File> classpath = new LinkedList<File>();
    classpath.add(new File(args[1]));

    LanguageParser parser = new LanguageParser();

    ConceptualTranslator translator = new ConceptualTranslator(parser, classpath);

    ConceptualFile conceptualFile = translator.parseFile(file);

    if (conceptualFile != null)
    {
      System.out.println("Successfully parsed " + file.getAbsolutePath());
      System.out.println(conceptualFile);

      try
      {
        translator.translate();
      }
      catch (NameConflictException e)
      {
        printConceptualException(e.getMessage(), e.getParseInfo());
        return;
      }
      catch (ConceptualException e)
      {
        printConceptualException(e.getMessage(), e.getParseInfo());
        return;
      }

      System.out.println("Successfully translated");
      System.out.println("Classes: ");
      for (ConceptualClass conceptualClass : conceptualFile.getClasses())
      {
        System.out.println("  " + conceptualClass.getName());
        System.out.println("  extends: " + conceptualClass.getBaseClass().getTypeInstance());
      }

    }
  }

  private static void printConceptualException(String message, ParseInfo... parseInfos)
  {
    if (parseInfos == null || parseInfos.length < 1)
    {
      System.err.println(message);
      return;
    }
    // make a String representation of the ParseInfos' character ranges
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < parseInfos.length; i++)
    {
      String characterRange;
      int startLine = parseInfos[i].getStartLine();
      int endLine = parseInfos[i].getEndLine();
      if (startLine == endLine)
      {
        // line:start-end if it is all on one line
        characterRange = startLine + ":";
        int startPos = parseInfos[i].getStartPos();
        int endPos = parseInfos[i].getEndPos();
        characterRange += startPos;
        if (startPos < endPos - 1)
        {
          characterRange += "-" + (endPos - 1);
        }
      }
      else
      {
        // startLine-endLine if it spans multiple lines
        characterRange = startLine + "-" + endLine;
      }
      buffer.append(characterRange);
      if (i != parseInfos.length - 1)
      {
        buffer.append(", ");
      }
    }
    if (parseInfos.length == 1)
    {
      System.err.println(message + ": " + buffer);
    }
    else
    {
      System.err.println(message + "\nAt: " + buffer);
    }
  }

}
