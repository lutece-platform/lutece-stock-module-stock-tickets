package fr.paris.lutece.plugins.stock.modules.tickets.business;

import fr.paris.lutece.plugins.stock.business.offer.IOfferDAO;
import fr.paris.lutece.plugins.stock.business.offer.Offer;

import java.sql.Timestamp;
import java.util.List;


/**
 * Interface for seance dao
 * 
 * @author abataille
 */
public interface ISeanceDAO extends IOfferDAO
{

    /**
     * Returns list of available seance types (not cancelled) for given date and
     * hour.
     * 
     * @param offerId the offer id
     * @param dateHour date and hour
     * @return seance list
     */
    List<Offer> findAvailableSeanceByDate( Integer offerId, Timestamp dateHour );

    /**
     * Returns list of available seance types (not cancelled) for given offer id
     * @param offerId offer id
     * @return seance list
     */
    List<Offer> findAvailableSeance( Integer offerId );

}