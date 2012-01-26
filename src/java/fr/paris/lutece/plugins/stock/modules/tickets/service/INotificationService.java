package fr.paris.lutece.plugins.stock.modules.tickets.service;

import fr.paris.lutece.plugins.stock.modules.tickets.business.NotificationDTO;


/**
 * The Interface INotificationService.
 */
public interface INotificationService
{

    /**
     * Send a notification.
     * 
     * @param notification the notification
     */
    void send( NotificationDTO notification );

}