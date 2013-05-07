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
package fr.paris.lutece.plugins.stock.modules.tickets.service;

import fr.paris.lutece.plugins.stock.modules.tickets.business.NotificationDTO;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * Service for sending notifications
 * 
 * @author abataille
 */
public class NotificationService implements INotificationService
{

    private static final String PROPERTY_MAIL_SENDER_NAME = "stock-billetterie.mail.senderName";
    private static final String PROPERTY_MAIL_SENDER_EMAIL = "stock-billetterie.mail.senderEmail";

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.stock.modules.tickets.service.INotificationService#send(fr.paris.lutece.plugins.stock.modules.tickets.business.NotificationDTO)
     */
    /**
     * {@inheritDoc}
     */
    public void send( NotificationDTO notification )
    {
        MailService.sendMailHtml( notification.getRecipientsTo( ),
                AppPropertiesService.getProperty( PROPERTY_MAIL_SENDER_NAME ),
                AppPropertiesService.getProperty( PROPERTY_MAIL_SENDER_EMAIL ), notification.getSubject( ),
                notification.getMessage( ).replace( "\r\n", "<br/>" ) );
    }
}
