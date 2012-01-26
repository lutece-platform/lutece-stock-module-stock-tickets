package fr.paris.lutece.plugins.stock.modules.tickets.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.util.url.UrlItem;

/**
 *	KiosqueUtils
 */
public final class TicketsUtils
{
	
	/**
	 * private constructor
	 */
	private TicketsUtils()
	{
	}
	
	/**
     * Copy the request's parameters into the UrlItem object and the map.
     * If a parameter key has many values, they will be added after the url. Otherwise, the parameter will be added into the map.
     * @param url the url to add parameters. Must be not null.
     * @param urlParam the map to put paramters with one value. Must be not null.
     * @param request the http request.
     */
    public static void copyParameters( UrlItem url, Map<String, Object> urlParam, HttpServletRequest request )
    {
    	Map<String, Object> mapParameters = request.getParameterMap(  );
        for ( String strParamName : mapParameters.keySet() )
		{
        	String[] parameterValues = ( String[] ) mapParameters.get( strParamName ); 
			if( parameterValues.length > 1 )
			{
				// There is more than one value for this parameter (exemple : checkboxes ...).
				// So we put them in the URL
				for( String strValue : parameterValues )
				{
					url.addParameter( strParamName, strValue );
				}
			}
			else
			{
				// Only one parameter. So we put it in the map parameter
				urlParam.put( strParamName, parameterValues[0] );
			}
		}
    }
}
