/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.stock.modules.tickets.business;

import fr.paris.lutece.plugins.stock.commons.validator.annotation.ListEmail;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * DTO for Notification
 *
 * @author nmoitry
 */
public class NotificationDTO
{
	@ListEmail
	@NotEmpty
    private String recipientsTo;
	@NotEmpty
    private String subject;
	@NotEmpty
    private String message;
    private String notificationAction;
    private Integer idOffer;
	
	/**
	 * @param strRecipientsTo the strRecipientsTo to set
	 */
	public void setRecipientsTo( String strRecipientsTo )
	{
        this.recipientsTo = strRecipientsTo;
	}
	
	/**
	 * @return the strRecipientsTo
	 */
	public String getRecipientsTo( )
	{
        return recipientsTo;
	}
	
	/**
	 * @param strSubject the strSubject to set
	 */
	public void setSubject( String strSubject )
	{
        this.subject = strSubject;
	}
	
	/**
	 * @return the strSubject
	 */
	public String getSubject( )
	{
        return subject;
	}
	
	/**
	 * @param strMessage the strMessage to set
	 */
	public void setMessage( String strMessage )
	{
        this.message = strMessage;
	}
	
	/**
	 * @return the strMessage
	 */
	public String getMessage( )
	{
        return message;
	}

	/**
	 * @param notificationAction the notificationAction to set
	 */
	public void setNotificationAction( String notificationAction )
	{
        this.notificationAction = notificationAction;
	}

	/**
	 * @return the notificationAction
	 */
	public String getNotificationAction( )
	{
        return notificationAction;
	}

	/**
	 * @param idOffer the idOffer to set
	 */
	public void setIdOffer( Integer idOffer )
	{
        this.idOffer = idOffer;
	}

	/**
	 * @return the idOffer
	 */
	public Integer getIdOffer( )
	{
        return idOffer;
	}
}
