package fr.paris.lutece.plugins.stock.modules.tickets.service.rules;

import fr.paris.lutece.plugins.stock.business.offer.Offer;
import fr.paris.lutece.plugins.stock.business.purchase.IPurchaseDTO;
import fr.paris.lutece.plugins.stock.business.purchase.exception.PurchaseOutOfStock;
import fr.paris.lutece.plugins.stock.business.purchase.exception.PurchaseSessionExpired;
import fr.paris.lutece.plugins.stock.commons.exception.BusinessException;
import fr.paris.lutece.plugins.stock.modules.tickets.service.IPurchaseService;
import fr.paris.lutece.plugins.stock.service.IOfferService;
import fr.paris.lutece.plugins.stock.service.IPurchaseRules;
import fr.paris.lutece.plugins.stock.service.PurchaseRules;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;


/**
 * Business rules for offer type Tarif Réduit
 * 
 * @author abataille
 */
public class PurchaseTarifReduitRules extends PurchaseRules implements IPurchaseRules
{
    public static final String MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER_TYPE = "module.stock.billetterie.message.error.purchase.quantity.offer.type";
    public static final Integer NB_PLACES_MAX_TARIF_REDUIT = AppPropertiesService.getPropertyInt(
            "stock-billetterie.nb_places_max.tarif_reduit", 2 );
    private static final Logger LOGGER = Logger.getLogger( PurchaseTarifReduitRules.class );


    @Inject
    private IPurchaseService _purchaseService;

    @Inject
    @Named( "stock-tickets.seanceService" )
    private IOfferService _offerService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.paris.lutece.plugins.stock.service.IPurchaseRules#checkBeforePurchase
     * (fr.paris.lutece.plugins.stock.business.purchase.Purchase)
     */
    /**
     * {@inheritDoc}
     */
    public void checkBeforePurchase( IPurchaseDTO purchase, String sessionId ) throws PurchaseOutOfStock,
            PurchaseSessionExpired
    {
        super.checkBeforePurchase( purchase, sessionId );

        LOGGER.debug( "Vérification des règles de gestion pour un tarif réduit, SID = " + sessionId );
        Offer offer = _offerService.findById( purchase.getOfferId( ) );
        Integer nbReservation = _purchaseService.getNumberOfReservationByIdProductAndUserName( offer.getProduct( )
                .getId( ), offer.getType( ).getId( ), purchase.getUserName( ) );
        if ( ( purchase.getQuantity( ) + nbReservation ) > NB_PLACES_MAX_TARIF_REDUIT )
        {
            throw new BusinessException( purchase, MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER_TYPE );
        }
        LOGGER.debug( "Vérification ok, SID = " + sessionId );
    }
}
