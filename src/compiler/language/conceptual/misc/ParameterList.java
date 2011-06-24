package compiler.language.conceptual.misc;

/*
 * Created on 23 Jun 2011
 */

/**
 * @author Anthony Bryant
 */
public class ParameterList extends Parameter
{

  private Parameter[] parameters;

  /**
   * Creates a new ParameterList with the specified parameters
   * @param parameters - the list of parameters that this list contains
   */
  public ParameterList(Parameter[] parameters)
  {
    this.parameters = parameters;
  }

  /**
   * @return the parameters
   */
  public Parameter[] getParameters()
  {
    return parameters;
  }

}
