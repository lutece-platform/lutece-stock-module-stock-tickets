/*
 * Copyright (c) 2002-2018, Mairie de Paris
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

import javax.validation.constraints.NotEmpty;

import fr.paris.lutece.plugins.stock.commons.validator.annotation.ListEmail;

/**
 * DTO for Notification
 *
 * @author nmoitry
 */
public class NotificationDTO
{
    @ListEmail
    @NotEmpty
    private String _recipientsTo;
    @NotEmpty
    private String _subject;
    @NotEmpty
    private String _message;
    private String _notificationAction;
    private Integer _idOffer;

    /**
     * @param strRecipientsTo
     *            the strRecipientsTo to set
     */
    public void setRecipientsTo( String strRecipientsTo )
    {
        this._recipientsTo = strRecipientsTo;
    }

    /**
     * @return the strRecipientsTo
     */
    public String getRecipientsTo( )
    {
        return _recipientsTo;
    }

    /**
     * @param strSubject
     *            the strSubject to set
     */
    public void setSubject( String strSubject )
    {
        this._subject = strSubject;
    }

    /**
     * @return the strSubject
     */
    public String getSubject( )
    {
        return _subject;
    }

    /**
     * @param strMessage
     *            the strMessage to set
     */
    public void setMessage( String strMessage )
    {
        this._message = strMessage;
    }

    /**
     * @return the strMessage
     */
    public String getMessage( )
    {
        return _message;
    }

    /**
     * @param notificationAction
     *            the notificationAction to set
     */
    public void setNotificationAction( String notificationAction )
    {
        this._notificationAction = notificationAction;
    }

    /**
     * @return the notificationAction
     */
    public String getNotificationAction( )
    {
        return _notificationAction;
    }

    /**
     * @param idOffer
     *            the idOffer to set
     */
    public void setIdOffer( Integer idOffer )
    {
        this._idOffer = idOffer;
    }

    /**
     * @return the idOffer
     */
    public Integer getIdOffer( )
    {
        return _idOffer;
    }
}
