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
package fr.paris.lutece.plugins.stock.modules.tickets.service;

import fr.paris.lutece.plugins.stock.business.purchase.Purchase;
import fr.paris.lutece.plugins.stock.business.purchase.PurchaseFilter;
import fr.paris.lutece.plugins.stock.business.purchase.exception.PurchaseException;
import fr.paris.lutece.plugins.stock.business.purchase.exception.PurchaseOutOfStock;
import fr.paris.lutece.plugins.stock.business.purchase.exception.PurchaseSessionExpired;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.commons.exception.BusinessException;
import fr.paris.lutece.plugins.stock.modules.tickets.business.IReservationDAO;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ReservationDTO;
import fr.paris.lutece.plugins.stock.modules.tickets.business.SeanceDTO;
import fr.paris.lutece.plugins.stock.service.IPurchaseRules;
import fr.paris.lutece.plugins.stock.service.IPurchaseSessionManager;
import fr.paris.lutece.plugins.stock.service.impl.AbstractService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * PurchaseService.
 */
@Transactional( rollbackFor = {
    Exception.class
} )
public final class PurchaseService extends AbstractService implements IPurchaseService
{
    /** The Constant RIGHT_MANAGE_OFFRES. */
    public static final String RIGHT_MANAGE_OFFRES = "PURCHASES_MANAGEMENT";

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "PURCHASE";

    // MESSAGE
    /** The Constant MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER. */
    public static final String MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER = "module.stock.billetterie.message.error.purchase.quantity.offer";

    /** The Constant MESSAGE_ERROR_PURCHASE_UNAVAILABLE. */
    public static final String MESSAGE_ERROR_PURCHASE_UNAVAILABLE = "module.stock.billetterie.message.error.purchase.unavailable";

    /** The Constant MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER_TYPE. */
    public static final String MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER_TYPE = "module.stock.billetterie.message.error.purchase.quantity.offer.type";

    /** The Constant MESSAGE_ERROR_PURCHASE_SESSION_EXPIRED. */
    public static final String MESSAGE_ERROR_PURCHASE_SESSION_EXPIRED = "module.stock.billetterie.message.error.purchase.session.expired";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger( PurchaseService.class );

    /** The _dao purchase. */
    @Inject
    @Named( "stock-tickets.reservationDAO" )
    private IReservationDAO _daoPurchase;

    /** The _service offer. */
    @Inject
    @Named( "stock-tickets.seanceService" )
    private ISeanceService _serviceOffer;

    /** The _purchase session manager. */
    @Inject
    private IPurchaseSessionManager _purchaseSessionManager;
    @Inject
    private IStatisticService _serviceStatistic;

    /**
     * Constructor.
     */
    private PurchaseService( )
    {
    }

    /**
     * {@inheritDoc}
     */
    public void init( )
    {
    }

    /**
     * {@inheritDoc}
     */
    public ReservationDTO doSavePurchase( ReservationDTO purchaseDTO, String sessionId )
    {
        try
        {
            // Load offer
            purchaseDTO.setOffer( this._serviceOffer.findSeanceById( purchaseDTO.getOffer( ).getId( ) ) );

            // Load business rules bean of offer type
            IPurchaseRules purchaseRules = (IPurchaseRules) SpringContextService
                    .getBean( "stock-tickets.purchaseRules." + purchaseDTO.getOffer( ).getIdGenre( ) );

            // Check business rules
            synchronized( purchaseDTO.getEmailAgent( ) )
            {
                purchaseRules.checkBeforePurchase( purchaseDTO, sessionId );

                // Update the seance quantity
                SeanceDTO seance = this._serviceOffer.findSeanceById( purchaseDTO.getOffer( ).getId( ) );
                seance.setQuantity( seance.getQuantity( ) - purchaseDTO.getQuantity( ) );
                this._serviceOffer.update( seance );

                Purchase purchase = purchaseDTO.convert( );

                if ( ( purchaseDTO.getId( ) != null ) && ( purchaseDTO.getId( ) > 0 ) )
                {
                    // get the actual purchase and update old offer
                    Purchase purchaseInDb = _daoPurchase.findById( purchaseDTO.getId( ) );
                    SeanceDTO oldSeance = this._serviceOffer.findSeanceById( purchaseInDb.getOffer( ).getId( ) );
                    oldSeance.setQuantity( oldSeance.getQuantity( ) + purchaseInDb.getQuantity( ) );
                    this._serviceOffer.update( oldSeance );

                    _daoPurchase.update( purchase );
                }
                else
                {
                    _daoPurchase.create( purchase );
                    purchaseDTO.setId( purchase.getId( ) );
                    // Statistic management
                    _serviceStatistic.doManagePurchaseSaving( purchaseDTO );
                }
            }
        }
        catch( PurchaseOutOfStock e )
        {
            LOGGER.debug( "Réservation impossible pour " + sessionId + " quantité épuisée.", e );
            throw new BusinessException( null, MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER );
        }
        catch( PurchaseSessionExpired e )
        {
            LOGGER.debug( "Réservation impossible pour " + sessionId + " session expirée.", e );
            throw new BusinessException( null, MESSAGE_ERROR_PURCHASE_SESSION_EXPIRED );
        }
        catch( PurchaseException e )
        {
            LOGGER.debug( "Réservation impossible pour " + sessionId + ".", e );
            throw new BusinessException( null, MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER );
        }
        finally
        {
            // Libère la rèservation
            _purchaseSessionManager.release( sessionId, purchaseDTO );
        }

        // Return persisted object
        return purchaseDTO;
    }

    /**
     * {@inheritDoc}
     */
    public List<ReservationDTO> doSavePurchaseList( List<ReservationDTO> bookingList, String sessionId )
    {
        if ( bookingList == null )
        {
            throw new BusinessException( null, MESSAGE_ERROR_PURCHASE_SESSION_EXPIRED );
        }

        int i = 0;

        for ( ReservationDTO reservation : bookingList )
        {
            bookingList.set( i, this.doSavePurchase( reservation, sessionId ) );
            i++;
        }

        return bookingList;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getNumberOfReservationByIdProductAndUserName( Integer id, Integer idOfferGenre, String userName )
    {
        return this._daoPurchase.getQuantityPurchasedByIdProductAndUserName( id, idOfferGenre, userName );
    }

    /**
     * {@inheritDoc}
     */
    public void doDeletePurchase( int nIdPurchase )
    {
        // Update the seance quantity
        ReservationDTO reservation = this.findById( nIdPurchase );

        if ( reservation != null )
        {
            SeanceDTO seance = this._serviceOffer.findSeanceById( reservation.getOffer( ).getId( ) );
            seance.setQuantity( seance.getQuantity( ) + reservation.getQuantity( ) );
            this._serviceOffer.update( seance );

            // On supprimes les statistiques de la reservation
            _serviceStatistic.doRemovePurchaseStatisticByIdPurchase( nIdPurchase );

            _daoPurchase.remove( nIdPurchase );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.stock.modules.tickets.service.IPurchaseService #update
     * (fr.paris.lutece.plugins.stock.modules.tickets.business.ReservationDTO)
     */

    /**
     * {@inheritDoc}
     */
    public void update( ReservationDTO purchase )
    {
        _daoPurchase.update( purchase.convert( ) );
    }

    @Override
    public ResultList<ReservationDTO> findByFilter( PurchaseFilter filter, PaginationProperties paginationProperties )
    {
        ResultList<Purchase> findByFilter = _daoPurchase.findByFilter( filter, paginationProperties );

        return ReservationDTO.convertEntityList( findByFilter );
    }

    @Override
    public ResultList<ReservationDTO> findByFilter( PurchaseFilter filter )
    {
        return findByFilter( filter, null );
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.stock.modules.tickets.service.IPurchaseService #findAll()
     */

    /**
     * {@inheritDoc}
     */
    public List<ReservationDTO> findAll( )
    {
        return ReservationDTO.convertEntityList( _daoPurchase.findAll( ) );
    }

    /**
     * {@inheritDoc}
     */
    public ReservationDTO findById( Integer nIdPurchase )
    {
        return ReservationDTO.convertEntity( _daoPurchase.findById( nIdPurchase ) );
    }

    /**
     * {@inheritDoc}
     */
    public void doCancelPurchaseList( List<ReservationDTO> bookingList, String sessionId )
    {
        for ( ReservationDTO reservation : bookingList )
        {
            // Libère la rèservation
            _purchaseSessionManager.release( sessionId, reservation );
        }
    }
}
