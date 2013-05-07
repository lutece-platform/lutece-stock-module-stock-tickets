/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
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
