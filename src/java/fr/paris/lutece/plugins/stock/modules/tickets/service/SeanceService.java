/*
 * Copyright (c) 2002-2008, Mairie de Paris
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

import fr.paris.lutece.plugins.stock.business.offer.IOfferGenreDAO;
import fr.paris.lutece.plugins.stock.business.offer.OfferFilter;
import fr.paris.lutece.plugins.stock.business.offer.OfferGenre;
import fr.paris.lutece.plugins.stock.business.purchase.IPurchaseDAO;
import fr.paris.lutece.plugins.stock.business.purchase.Purchase;
import fr.paris.lutece.plugins.stock.business.purchase.PurchaseFilter;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.dao.PaginationProperties;
import fr.paris.lutece.plugins.stock.commons.exception.BusinessException;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ISeanceDAO;
import fr.paris.lutece.plugins.stock.modules.tickets.business.SeanceDTO;
import fr.paris.lutece.plugins.stock.modules.tickets.business.SeanceFilter;
import fr.paris.lutece.plugins.stock.modules.tickets.utils.constants.TicketsConstants;
import fr.paris.lutece.plugins.stock.service.OfferService;
import fr.paris.lutece.plugins.stock.utils.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;


/**
 * OfferService.
 */
@Transactional( rollbackFor = { Exception.class } )
public class SeanceService extends OfferService implements ISeanceService
{

    /** The Constant RIGHT_MANAGE_OFFRES. */
    public static final String RIGHT_MANAGE_OFFRES = "OFFERS_MANAGEMENT";

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE = "OFFER";

    //MESSAGES
    /** The Constant MESSAGE_ERROR_OFFER_UNIQUE_BY_DATE_BY_SPECTACLE. */
    private static final String MESSAGE_ERROR_OFFER_UNIQUE_BY_DATE_BY_SPECTACLE = "module.stock.billetterie.save_offer.error.unique";

    /** The Constant MESSAGE_ERROR_OFFER_REDUCT_PRICE. */
    private static final String MESSAGE_ERROR_OFFER_REDUCT_PRICE = "module.stock.billetterie.save_offer.reductPrice.mandatory";
    
    /** The _dao offer. */
	@Inject
    @Named( "stock-tickets.seanceDAO" )
    private ISeanceDAO _daoOffer;

    /** The _dao offer genre. */
	@Inject
	private IOfferGenreDAO _daoOfferGenre;

    /** The _dao purchase. */
    @Inject
    @Named( "stock.purchaseDAO" )
    private IPurchaseDAO _daoPurchase;

    /**
     * {@inheritDoc}
     */
    public void init(  )
    {
    }


    /**
     * {@inheritDoc}
     */
    public void doSaveOffer( SeanceDTO offer )
    {
        // BO-E08-RGE01 : Il ne doit y avoir qu'une seule representation par
        // spectacle, type, date, heure.
        SeanceFilter filter = new SeanceFilter( );
        filter.setProductId( offer.getProduct( ).getId( ) );
    	filter.setIdGenre( offer.getIdGenre( ) );
    	filter.setDateOr( DateUtils.getDate( offer.getDate( ), false ) );
    	filter.setHour( DateUtils.getHour( offer.getHour( ) ) );
    	
    	ResultList<SeanceDTO> seanceList = this.findByFilter( filter, null );
    	if ( ! seanceList.isEmpty( ) )
    	{
            boolean otherOfferCancel = false;

            // Les représentations déjà existantes ne sont pas annulées
            for ( SeanceDTO seance : seanceList )
            {
                if ( !seance.getStatut( ).equals( TicketsConstants.OFFER_STATUT_CANCEL ) )
                {
                    otherOfferCancel = true;
                }
            }

            // Si modification, l'id de l'offre recuperee doit etre differente
            // de celle en cours de modification
            if ( otherOfferCancel
                    && ( offer.getId( ) == null || ( offer.getId( ) != null && seanceList.get( 0 ).getId( ) != offer
                            .getId( ) ) ) )
    		{
    			throw new BusinessException( offer, MESSAGE_ERROR_OFFER_UNIQUE_BY_DATE_BY_SPECTACLE );
    		}
    	}
    	
    	// BO-CU04-E01-RGE15 : Le prix en tarif réduit est obligatoire si le type de la représentation est « Tarifs réduits ».
    	if ( offer.getIdGenre( ).equals( TicketsConstants.OFFER_TYPE_REDUCT_ID ) )
    	{
    		if ( offer.getReductPrice( ) == null || offer.getReductPrice( ) <= 0 )
    		{
    			throw new BusinessException( offer, MESSAGE_ERROR_OFFER_REDUCT_PRICE );
    		}
    	}
    	
        if ( offer.getId( ) != null && offer.getId( ) > 0 )
        {
        	_daoOffer.update( offer.convert( ) );
        }
        else
        {
            _daoOffer.create( offer.convert( ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    public void doDeleteOffer( int nIdOffer )
    {
        // delete all purchase for this offer
        PurchaseFilter purchaseFilter = new PurchaseFilter( );
        purchaseFilter.setIdOffer( nIdOffer );
        List<Purchase> purchaseList = _daoPurchase.findByFilter( purchaseFilter, null );

        for ( Purchase purchase : purchaseList )
        {
            this._daoPurchase.remove( purchase.getId( ) );
        }

		_daoOffer.remove( nIdOffer );
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.paris.lutece.plugins.stock.modules.tickets.service.ISeanceService#
     * update(fr.paris.lutece.plugins.stock.modules.tickets.business.SeanceDTO)
     */
    /**
     * {@inheritDoc}
     */
    public void update( SeanceDTO offer )
    {
        _daoOffer.update( offer.convert( ) );
    }

	/**
	 * {@inheritDoc}
	 */
    public ResultList<SeanceDTO> findByFilter( OfferFilter filter, PaginationProperties paginationProperties )
    {
        return SeanceDTO.convertEntityList( _daoOffer.findByFilter( filter, paginationProperties ) );
	}

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.paris.lutece.plugins.stock.modules.tickets.service.ISeanceService#
     * findAll()
     */
    /**
     * {@inheritDoc}
     */
    public List<SeanceDTO> findAll( )
    {
        return SeanceDTO.convertEntityList( _daoOffer.findAll( ) );
    }

	/**
	 * {@inheritDoc}
	 */
    public SeanceDTO findSeanceById( Integer nIdOffer )
	{
		return SeanceDTO.convertEntity( _daoOffer.findById( nIdOffer ) );
	}

    /**
     * {@inheritDoc}
     */
    public List<OfferGenre> findAllGenre( )
    {
        return _daoOfferGenre.findAll( );
    }

    /**
     * {@inheritDoc}
     */
    public List<String> findSeanceByShow( Integer showId, OfferFilter filter )
    {
        List<SeanceDTO> offerList = SeanceDTO.convertEntityList( _daoOffer.findByProduct( showId, filter ) );
        final DateFormat sdfComboSeance = new SimpleDateFormat( TicketsConstants.FORMAT_COMBO_DATE_SEANCE );

        List<String> dateList = new ArrayList<String>( );
        Date today = new Date( );
        for ( SeanceDTO seance : offerList )
        {
            String sDateHour = sdfComboSeance.format( seance.getDateHour( ) );
            if ( seance.getStatut( ).equals( TicketsConstants.OFFER_STATUT_OPEN )
                    && seance.getDateHour( ).after( today ) )
            {
                if ( !dateList.contains( sDateHour ) )
                // Pas de type de séance non complète ajoutée pour cette date
                {
                    if ( seance.getQuantity( ) == 0 )
                    {
                        if ( !dateList.contains( sDateHour + " - COMPLET" ) )
                        {
                            dateList.add( sDateHour + " - COMPLET" );
                        }
                    }
                    else
                    {
                        dateList.remove( sDateHour + " - COMPLET" );
                        dateList.add( sDateHour );
                    }
                }
            }
        }
        return dateList;
    }

    /**
     * {@inheritDoc}
     */
    public List<SeanceDTO> findSeanceByDate( Integer showId, Date dateHour )
    {
        Timestamp dateHourTs = new Timestamp( dateHour.getTime( ) );

        List<SeanceDTO> offerList = SeanceDTO.convertEntityList( _daoOffer.findAvailableSeanceByDate( showId,
                dateHourTs ) );

        return offerList;
    }

    /**
     * Check availability.
     * 
     * @param offerId the offer id
     * @param userName the user name
     */
    public void checkAvailability( Integer offerId, String userName )
    {
        // TODO implémenter le controle des RG pour la réservation de place par
        // utilisateur et le blocage des produits pendant une session
    }

}
